import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyRoleService } from '../service/party-role.service';
import { IPartyRole, PartyRole } from '../party-role.model';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';
import { IRoleType } from 'app/entities/role-type/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/service/role-type.service';

import { PartyRoleUpdateComponent } from './party-role-update.component';

describe('PartyRole Management Update Component', () => {
  let comp: PartyRoleUpdateComponent;
  let fixture: ComponentFixture<PartyRoleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyRoleService: PartyRoleService;
  let partyService: PartyService;
  let roleTypeService: RoleTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyRoleUpdateComponent],
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
      .overrideTemplate(PartyRoleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRoleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyRoleService = TestBed.inject(PartyRoleService);
    partyService = TestBed.inject(PartyService);
    roleTypeService = TestBed.inject(RoleTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Party query and add missing value', () => {
      const partyRole: IPartyRole = { id: 456 };
      const partyID: IParty = { id: 35477 };
      partyRole.partyID = partyID;

      const partyCollection: IParty[] = [{ id: 93295 }];
      jest.spyOn(partyService, 'query').mockReturnValue(of(new HttpResponse({ body: partyCollection })));
      const additionalParties = [partyID];
      const expectedCollection: IParty[] = [...additionalParties, ...partyCollection];
      jest.spyOn(partyService, 'addPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      expect(partyService.query).toHaveBeenCalled();
      expect(partyService.addPartyToCollectionIfMissing).toHaveBeenCalledWith(partyCollection, ...additionalParties);
      expect(comp.partiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RoleType query and add missing value', () => {
      const partyRole: IPartyRole = { id: 456 };
      const roleTypeID: IRoleType = { id: 25495 };
      partyRole.roleTypeID = roleTypeID;

      const roleTypeCollection: IRoleType[] = [{ id: 33657 }];
      jest.spyOn(roleTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: roleTypeCollection })));
      const additionalRoleTypes = [roleTypeID];
      const expectedCollection: IRoleType[] = [...additionalRoleTypes, ...roleTypeCollection];
      jest.spyOn(roleTypeService, 'addRoleTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      expect(roleTypeService.query).toHaveBeenCalled();
      expect(roleTypeService.addRoleTypeToCollectionIfMissing).toHaveBeenCalledWith(roleTypeCollection, ...additionalRoleTypes);
      expect(comp.roleTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyRole: IPartyRole = { id: 456 };
      const partyID: IParty = { id: 60235 };
      partyRole.partyID = partyID;
      const roleTypeID: IRoleType = { id: 43527 };
      partyRole.roleTypeID = roleTypeID;

      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyRole));
      expect(comp.partiesSharedCollection).toContain(partyID);
      expect(comp.roleTypesSharedCollection).toContain(roleTypeID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRole>>();
      const partyRole = { id: 123 };
      jest.spyOn(partyRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRole }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyRoleService.update).toHaveBeenCalledWith(partyRole);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRole>>();
      const partyRole = new PartyRole();
      jest.spyOn(partyRoleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRole }));
      saveSubject.complete();

      // THEN
      expect(partyRoleService.create).toHaveBeenCalledWith(partyRole);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRole>>();
      const partyRole = { id: 123 };
      jest.spyOn(partyRoleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRole });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyRoleService.update).toHaveBeenCalledWith(partyRole);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyById', () => {
      it('Should return tracked Party primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRoleTypeById', () => {
      it('Should return tracked RoleType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRoleTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
