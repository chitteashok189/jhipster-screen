import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyAttributeService } from '../service/party-attribute.service';
import { IPartyAttribute, PartyAttribute } from '../party-attribute.model';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

import { PartyAttributeUpdateComponent } from './party-attribute-update.component';

describe('PartyAttribute Management Update Component', () => {
  let comp: PartyAttributeUpdateComponent;
  let fixture: ComponentFixture<PartyAttributeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyAttributeService: PartyAttributeService;
  let partyService: PartyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyAttributeUpdateComponent],
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
      .overrideTemplate(PartyAttributeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyAttributeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyAttributeService = TestBed.inject(PartyAttributeService);
    partyService = TestBed.inject(PartyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Party query and add missing value', () => {
      const partyAttribute: IPartyAttribute = { id: 456 };
      const partyID: IParty = { id: 33508 };
      partyAttribute.partyID = partyID;

      const partyCollection: IParty[] = [{ id: 63667 }];
      jest.spyOn(partyService, 'query').mockReturnValue(of(new HttpResponse({ body: partyCollection })));
      const additionalParties = [partyID];
      const expectedCollection: IParty[] = [...additionalParties, ...partyCollection];
      jest.spyOn(partyService, 'addPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyAttribute });
      comp.ngOnInit();

      expect(partyService.query).toHaveBeenCalled();
      expect(partyService.addPartyToCollectionIfMissing).toHaveBeenCalledWith(partyCollection, ...additionalParties);
      expect(comp.partiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyAttribute: IPartyAttribute = { id: 456 };
      const partyID: IParty = { id: 91678 };
      partyAttribute.partyID = partyID;

      activatedRoute.data = of({ partyAttribute });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyAttribute));
      expect(comp.partiesSharedCollection).toContain(partyID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyAttribute>>();
      const partyAttribute = { id: 123 };
      jest.spyOn(partyAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyAttribute }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyAttributeService.update).toHaveBeenCalledWith(partyAttribute);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyAttribute>>();
      const partyAttribute = new PartyAttribute();
      jest.spyOn(partyAttributeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyAttribute }));
      saveSubject.complete();

      // THEN
      expect(partyAttributeService.create).toHaveBeenCalledWith(partyAttribute);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyAttribute>>();
      const partyAttribute = { id: 123 };
      jest.spyOn(partyAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyAttributeService.update).toHaveBeenCalledWith(partyAttribute);
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
  });
});
