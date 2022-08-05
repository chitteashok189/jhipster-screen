import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiseaseControlService } from '../service/disease-control.service';
import { IDiseaseControl, DiseaseControl } from '../disease-control.model';
import { IDisease } from 'app/entities/disease/disease.model';
import { DiseaseService } from 'app/entities/disease/service/disease.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { SymptomService } from 'app/entities/symptom/service/symptom.service';

import { DiseaseControlUpdateComponent } from './disease-control-update.component';

describe('DiseaseControl Management Update Component', () => {
  let comp: DiseaseControlUpdateComponent;
  let fixture: ComponentFixture<DiseaseControlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diseaseControlService: DiseaseControlService;
  let diseaseService: DiseaseService;
  let cropService: CropService;
  let symptomService: SymptomService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiseaseControlUpdateComponent],
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
      .overrideTemplate(DiseaseControlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiseaseControlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diseaseControlService = TestBed.inject(DiseaseControlService);
    diseaseService = TestBed.inject(DiseaseService);
    cropService = TestBed.inject(CropService);
    symptomService = TestBed.inject(SymptomService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Disease query and add missing value', () => {
      const diseaseControl: IDiseaseControl = { id: 456 };
      const diseaseID: IDisease = { id: 81097 };
      diseaseControl.diseaseID = diseaseID;

      const diseaseCollection: IDisease[] = [{ id: 397 }];
      jest.spyOn(diseaseService, 'query').mockReturnValue(of(new HttpResponse({ body: diseaseCollection })));
      const additionalDiseases = [diseaseID];
      const expectedCollection: IDisease[] = [...additionalDiseases, ...diseaseCollection];
      jest.spyOn(diseaseService, 'addDiseaseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      expect(diseaseService.query).toHaveBeenCalled();
      expect(diseaseService.addDiseaseToCollectionIfMissing).toHaveBeenCalledWith(diseaseCollection, ...additionalDiseases);
      expect(comp.diseasesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Crop query and add missing value', () => {
      const diseaseControl: IDiseaseControl = { id: 456 };
      const cropID: ICrop = { id: 84489 };
      diseaseControl.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 6440 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Symptom query and add missing value', () => {
      const diseaseControl: IDiseaseControl = { id: 456 };
      const symptomID: ISymptom = { id: 26430 };
      diseaseControl.symptomID = symptomID;

      const symptomCollection: ISymptom[] = [{ id: 91949 }];
      jest.spyOn(symptomService, 'query').mockReturnValue(of(new HttpResponse({ body: symptomCollection })));
      const additionalSymptoms = [symptomID];
      const expectedCollection: ISymptom[] = [...additionalSymptoms, ...symptomCollection];
      jest.spyOn(symptomService, 'addSymptomToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      expect(symptomService.query).toHaveBeenCalled();
      expect(symptomService.addSymptomToCollectionIfMissing).toHaveBeenCalledWith(symptomCollection, ...additionalSymptoms);
      expect(comp.symptomsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const diseaseControl: IDiseaseControl = { id: 456 };
      const diseaseID: IDisease = { id: 77195 };
      diseaseControl.diseaseID = diseaseID;
      const cropID: ICrop = { id: 20006 };
      diseaseControl.cropID = cropID;
      const symptomID: ISymptom = { id: 92232 };
      diseaseControl.symptomID = symptomID;

      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(diseaseControl));
      expect(comp.diseasesSharedCollection).toContain(diseaseID);
      expect(comp.cropsSharedCollection).toContain(cropID);
      expect(comp.symptomsSharedCollection).toContain(symptomID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiseaseControl>>();
      const diseaseControl = { id: 123 };
      jest.spyOn(diseaseControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diseaseControl }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(diseaseControlService.update).toHaveBeenCalledWith(diseaseControl);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiseaseControl>>();
      const diseaseControl = new DiseaseControl();
      jest.spyOn(diseaseControlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: diseaseControl }));
      saveSubject.complete();

      // THEN
      expect(diseaseControlService.create).toHaveBeenCalledWith(diseaseControl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DiseaseControl>>();
      const diseaseControl = { id: 123 };
      jest.spyOn(diseaseControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ diseaseControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diseaseControlService.update).toHaveBeenCalledWith(diseaseControl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDiseaseById', () => {
      it('Should return tracked Disease primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDiseaseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCropById', () => {
      it('Should return tracked Crop primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCropById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSymptomById', () => {
      it('Should return tracked Symptom primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSymptomById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
