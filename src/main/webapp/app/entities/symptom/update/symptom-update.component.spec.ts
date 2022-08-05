import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SymptomService } from '../service/symptom.service';
import { ISymptom, Symptom } from '../symptom.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IDisease } from 'app/entities/disease/disease.model';
import { DiseaseService } from 'app/entities/disease/service/disease.service';
import { IPest } from 'app/entities/pest/pest.model';
import { PestService } from 'app/entities/pest/service/pest.service';

import { SymptomUpdateComponent } from './symptom-update.component';

describe('Symptom Management Update Component', () => {
  let comp: SymptomUpdateComponent;
  let fixture: ComponentFixture<SymptomUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let symptomService: SymptomService;
  let scoutingService: ScoutingService;
  let diseaseService: DiseaseService;
  let pestService: PestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SymptomUpdateComponent],
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
      .overrideTemplate(SymptomUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SymptomUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    symptomService = TestBed.inject(SymptomService);
    scoutingService = TestBed.inject(ScoutingService);
    diseaseService = TestBed.inject(DiseaseService);
    pestService = TestBed.inject(PestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Scouting query and add missing value', () => {
      const symptom: ISymptom = { id: 456 };
      const scoutingID: IScouting = { id: 77611 };
      symptom.scoutingID = scoutingID;

      const scoutingCollection: IScouting[] = [{ id: 92365 }];
      jest.spyOn(scoutingService, 'query').mockReturnValue(of(new HttpResponse({ body: scoutingCollection })));
      const additionalScoutings = [scoutingID];
      const expectedCollection: IScouting[] = [...additionalScoutings, ...scoutingCollection];
      jest.spyOn(scoutingService, 'addScoutingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      expect(scoutingService.query).toHaveBeenCalled();
      expect(scoutingService.addScoutingToCollectionIfMissing).toHaveBeenCalledWith(scoutingCollection, ...additionalScoutings);
      expect(comp.scoutingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Disease query and add missing value', () => {
      const symptom: ISymptom = { id: 456 };
      const diseaseID: IDisease = { id: 7393 };
      symptom.diseaseID = diseaseID;

      const diseaseCollection: IDisease[] = [{ id: 71086 }];
      jest.spyOn(diseaseService, 'query').mockReturnValue(of(new HttpResponse({ body: diseaseCollection })));
      const additionalDiseases = [diseaseID];
      const expectedCollection: IDisease[] = [...additionalDiseases, ...diseaseCollection];
      jest.spyOn(diseaseService, 'addDiseaseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      expect(diseaseService.query).toHaveBeenCalled();
      expect(diseaseService.addDiseaseToCollectionIfMissing).toHaveBeenCalledWith(diseaseCollection, ...additionalDiseases);
      expect(comp.diseasesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pest query and add missing value', () => {
      const symptom: ISymptom = { id: 456 };
      const pestID: IPest = { id: 3840 };
      symptom.pestID = pestID;

      const pestCollection: IPest[] = [{ id: 81888 }];
      jest.spyOn(pestService, 'query').mockReturnValue(of(new HttpResponse({ body: pestCollection })));
      const additionalPests = [pestID];
      const expectedCollection: IPest[] = [...additionalPests, ...pestCollection];
      jest.spyOn(pestService, 'addPestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      expect(pestService.query).toHaveBeenCalled();
      expect(pestService.addPestToCollectionIfMissing).toHaveBeenCalledWith(pestCollection, ...additionalPests);
      expect(comp.pestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const symptom: ISymptom = { id: 456 };
      const scoutingID: IScouting = { id: 25303 };
      symptom.scoutingID = scoutingID;
      const diseaseID: IDisease = { id: 83172 };
      symptom.diseaseID = diseaseID;
      const pestID: IPest = { id: 4432 };
      symptom.pestID = pestID;

      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(symptom));
      expect(comp.scoutingsSharedCollection).toContain(scoutingID);
      expect(comp.diseasesSharedCollection).toContain(diseaseID);
      expect(comp.pestsSharedCollection).toContain(pestID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Symptom>>();
      const symptom = { id: 123 };
      jest.spyOn(symptomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: symptom }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(symptomService.update).toHaveBeenCalledWith(symptom);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Symptom>>();
      const symptom = new Symptom();
      jest.spyOn(symptomService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: symptom }));
      saveSubject.complete();

      // THEN
      expect(symptomService.create).toHaveBeenCalledWith(symptom);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Symptom>>();
      const symptom = { id: 123 };
      jest.spyOn(symptomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ symptom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(symptomService.update).toHaveBeenCalledWith(symptom);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackScoutingById', () => {
      it('Should return tracked Scouting primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackScoutingById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDiseaseById', () => {
      it('Should return tracked Disease primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDiseaseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPestById', () => {
      it('Should return tracked Pest primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPestById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
