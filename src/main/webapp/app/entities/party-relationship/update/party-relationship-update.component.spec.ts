import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyRelationshipService } from '../service/party-relationship.service';
import { IPartyRelationship, PartyRelationship } from '../party-relationship.model';
import { IPartyRelationshipType } from 'app/entities/party-relationship-type/party-relationship-type.model';
import { PartyRelationshipTypeService } from 'app/entities/party-relationship-type/service/party-relationship-type.service';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';

import { PartyRelationshipUpdateComponent } from './party-relationship-update.component';

describe('PartyRelationship Management Update Component', () => {
  let comp: PartyRelationshipUpdateComponent;
  let fixture: ComponentFixture<PartyRelationshipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyRelationshipService: PartyRelationshipService;
  let partyRelationshipTypeService: PartyRelationshipTypeService;
  let securityGroupService: SecurityGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyRelationshipUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PartyRelationshipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRelationshipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyRelationshipService = TestBed.inject(PartyRelationshipService);
    partyRelationshipTypeService = TestBed.inject(PartyRelationshipTypeService);
    securityGroupService = TestBed.inject(SecurityGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PartyRelationshipType query and add missing value', () => {
      const partyRelationship: IPartyRelationship = { id: 456 };
      const partyRelationshipTypeID: IPartyRelationshipType = { id: 74129 };
      partyRelationship.partyRelationshipTypeID = partyRelationshipTypeID;

      const partyRelationshipTypeCollection: IPartyRelationshipType[] = [{ id: 63394 }];
      jest.spyOn(partyRelationshipTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: partyRelationshipTypeCollection })));
      const additionalPartyRelationshipTypes = [partyRelationshipTypeID];
      const expectedCollection: IPartyRelationshipType[] = [...additionalPartyRelationshipTypes, ...partyRelationshipTypeCollection];
      jest.spyOn(partyRelationshipTypeService, 'addPartyRelationshipTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      expect(partyRelationshipTypeService.query).toHaveBeenCalled();
      expect(partyRelationshipTypeService.addPartyRelationshipTypeToCollectionIfMissing).toHaveBeenCalledWith(
        partyRelationshipTypeCollection,
        ...additionalPartyRelationshipTypes
      );
      expect(comp.partyRelationshipTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityGroup query and add missing value', () => {
      const partyRelationship: IPartyRelationship = { id: 456 };
      const securityGroupID: ISecurityGroup = { id: 59056 };
      partyRelationship.securityGroupID = securityGroupID;

      const securityGroupCollection: ISecurityGroup[] = [{ id: 75630 }];
      jest.spyOn(securityGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: securityGroupCollection })));
      const additionalSecurityGroups = [securityGroupID];
      const expectedCollection: ISecurityGroup[] = [...additionalSecurityGroups, ...securityGroupCollection];
      jest.spyOn(securityGroupService, 'addSecurityGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      expect(securityGroupService.query).toHaveBeenCalled();
      expect(securityGroupService.addSecurityGroupToCollectionIfMissing).toHaveBeenCalledWith(
        securityGroupCollection,
        ...additionalSecurityGroups
      );
      expect(comp.securityGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyRelationship: IPartyRelationship = { id: 456 };
      const partyRelationshipTypeID: IPartyRelationshipType = { id: 62528 };
      partyRelationship.partyRelationshipTypeID = partyRelationshipTypeID;
      const securityGroupID: ISecurityGroup = { id: 33287 };
      partyRelationship.securityGroupID = securityGroupID;

      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyRelationship));
      expect(comp.partyRelationshipTypesSharedCollection).toContain(partyRelationshipTypeID);
      expect(comp.securityGroupsSharedCollection).toContain(securityGroupID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationship>>();
      const partyRelationship = { id: 123 };
      jest.spyOn(partyRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRelationship }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyRelationshipService.update).toHaveBeenCalledWith(partyRelationship);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationship>>();
      const partyRelationship = new PartyRelationship();
      jest.spyOn(partyRelationshipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRelationship }));
      saveSubject.complete();

      // THEN
      expect(partyRelationshipService.create).toHaveBeenCalledWith(partyRelationship);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationship>>();
      const partyRelationship = { id: 123 };
      jest.spyOn(partyRelationshipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationship });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyRelationshipService.update).toHaveBeenCalledWith(partyRelationship);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyRelationshipTypeById', () => {
      it('Should return tracked PartyRelationshipType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyRelationshipTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityGroupById', () => {
      it('Should return tracked SecurityGroup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityGroupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
