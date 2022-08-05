import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyClassificationService } from '../service/party-classification.service';
import { IPartyClassification, PartyClassification } from '../party-classification.model';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

import { PartyClassificationUpdateComponent } from './party-classification-update.component';

describe('PartyClassification Management Update Component', () => {
  let comp: PartyClassificationUpdateComponent;
  let fixture: ComponentFixture<PartyClassificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyClassificationService: PartyClassificationService;
  let partyService: PartyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyClassificationUpdateComponent],
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
      .overrideTemplate(PartyClassificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyClassificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyClassificationService = TestBed.inject(PartyClassificationService);
    partyService = TestBed.inject(PartyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Party query and add missing value', () => {
      const partyClassification: IPartyClassification = { id: 456 };
      const partyID: IParty = { id: 12562 };
      partyClassification.partyID = partyID;

      const partyCollection: IParty[] = [{ id: 13312 }];
      jest.spyOn(partyService, 'query').mockReturnValue(of(new HttpResponse({ body: partyCollection })));
      const additionalParties = [partyID];
      const expectedCollection: IParty[] = [...additionalParties, ...partyCollection];
      jest.spyOn(partyService, 'addPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ partyClassification });
      comp.ngOnInit();

      expect(partyService.query).toHaveBeenCalled();
      expect(partyService.addPartyToCollectionIfMissing).toHaveBeenCalledWith(partyCollection, ...additionalParties);
      expect(comp.partiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const partyClassification: IPartyClassification = { id: 456 };
      const partyID: IParty = { id: 90292 };
      partyClassification.partyID = partyID;

      activatedRoute.data = of({ partyClassification });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyClassification));
      expect(comp.partiesSharedCollection).toContain(partyID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyClassification>>();
      const partyClassification = { id: 123 };
      jest.spyOn(partyClassificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyClassification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyClassification }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyClassificationService.update).toHaveBeenCalledWith(partyClassification);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyClassification>>();
      const partyClassification = new PartyClassification();
      jest.spyOn(partyClassificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyClassification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyClassification }));
      saveSubject.complete();

      // THEN
      expect(partyClassificationService.create).toHaveBeenCalledWith(partyClassification);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyClassification>>();
      const partyClassification = { id: 123 };
      jest.spyOn(partyClassificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyClassification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyClassificationService.update).toHaveBeenCalledWith(partyClassification);
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
