import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';
import { IPartyRelationshipType, PartyRelationshipType } from '../party-relationship-type.model';
import { IPartyRelationship } from 'app/entities/party-relationship/party-relationship.model';
import { PartyRelationshipService } from 'app/entities/party-relationship/service/party-relationship.service';

import { PartyRelationshipTypeUpdateComponent } from './party-relationship-type-update.component';

describe('PartyRelationshipType Management Update Component', () => {
  let comp: PartyRelationshipTypeUpdateComponent;
  let fixture: ComponentFixture<PartyRelationshipTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyRelationshipTypeService: PartyRelationshipTypeService;
  let partyRelationshipService: PartyRelationshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyRelationshipTypeUpdateComponent],
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
      .overrideTemplate(PartyRelationshipTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyRelationshipTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyRelationshipTypeService = TestBed.inject(PartyRelationshipTypeService);
    partyRelationshipService = TestBed.inject(PartyRelationshipService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PartyRelationship query and add missing value', () => {
      const partyRelationshipType: IPartyRelationshipType = { id: 456 };
      const partyRelationshipID: IPartyRelationship = { id: 2368 };
      partyRelationshipType.partyRelationshipID = partyRelationshipID;

      const partyRelationshipCollection: IPartyRelationship[] = [{ id: 16187 }];
      jest.spyOn(partyRelationshipService, 'query').mockReturnValue(of(new HttpResponse({ body: partyRelationshipCollection })));
      const additionalPartyRelationships = [partyRelationshipID];
      const expectedCollection: IPartyRelationship[] = [...additionalPartyRelationships, ...partyRelationshipCollection];
      jest.spyOn(partyRelationshipService, 'addPartyRelationshipToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyRelationshipType });
      comp.ngOnInit();

      expect(partyRelationshipService.query).toHaveBeenCalled();
      expect(partyRelationshipService.addPartyRelationshipToCollectionIfMissing).toHaveBeenCalledWith(
        partyRelationshipCollection,
        ...additionalPartyRelationships
      );
      expect(comp.partyRelationshipsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyRelationshipType: IPartyRelationshipType = { id: 456 };
      const partyRelationshipID: IPartyRelationship = { id: 44593 };
      partyRelationshipType.partyRelationshipID = partyRelationshipID;

      activatedRoute.data = of({ partyRelationshipType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyRelationshipType));
      expect(comp.partyRelationshipsSharedCollection).toContain(partyRelationshipID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationshipType>>();
      const partyRelationshipType = { id: 123 };
      jest.spyOn(partyRelationshipTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationshipType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRelationshipType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyRelationshipTypeService.update).toHaveBeenCalledWith(partyRelationshipType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationshipType>>();
      const partyRelationshipType = new PartyRelationshipType();
      jest.spyOn(partyRelationshipTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationshipType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyRelationshipType }));
      saveSubject.complete();

      // THEN
      expect(partyRelationshipTypeService.create).toHaveBeenCalledWith(partyRelationshipType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyRelationshipType>>();
      const partyRelationshipType = { id: 123 };
      jest.spyOn(partyRelationshipTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyRelationshipType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyRelationshipTypeService.update).toHaveBeenCalledWith(partyRelationshipType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyRelationshipById', () => {
      it('Should return tracked PartyRelationship primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyRelationshipById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
