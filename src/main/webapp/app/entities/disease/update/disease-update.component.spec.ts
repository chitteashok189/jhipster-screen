import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DiseaseService } from '../service/disease.service';
import { IDisease, Disease } from '../disease.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';

import { DiseaseUpdateComponent } from './disease-update.component';

describe('Disease Management Update Component', () => {
  let comp: DiseaseUpdateComponent;
  let fixture: ComponentFixture<DiseaseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let diseaseService: DiseaseService;
  let scoutingService: ScoutingService;
  let plantFactoryService: PlantFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DiseaseUpdateComponent],
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
      .overrideTemplate(DiseaseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiseaseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    diseaseService = TestBed.inject(DiseaseService);
    scoutingService = TestBed.inject(ScoutingService);
    plantFactoryService = TestBed.inject(PlantFactoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Scouting query and add missing value', () => {
      const disease: IDisease = { id: 456 };
      const scoutingID: IScouting = { id: 86404 };
      disease.scoutingID = scoutingID;

      const scoutingCollection: IScouting[] = [{ id: 20894 }];
      jest.spyOn(scoutingService, 'query').mockReturnValue(of(new HttpResponse({ body: scoutingCollection })));
      const additionalScoutings = [scoutingID];
      const expectedCollection: IScouting[] = [...additionalScoutings, ...scoutingCollection];
      jest.spyOn(scoutingService, 'addScoutingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      expect(scoutingService.query).toHaveBeenCalled();
      expect(scoutingService.addScoutingToCollectionIfMissing).toHaveBeenCalledWith(scoutingCollection, ...additionalScoutings);
      expect(comp.scoutingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlantFactory query and add missing value', () => {
      const disease: IDisease = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 41184 };
      disease.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 27677 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const disease: IDisease = { id: 456 };
      const scoutingID: IScouting = { id: 30758 };
      disease.scoutingID = scoutingID;
      const plantFactoryID: IPlantFactory = { id: 3748 };
      disease.plantFactoryID = plantFactoryID;

      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(disease));
      expect(comp.scoutingsSharedCollection).toContain(scoutingID);
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disease>>();
      const disease = { id: 123 };
      jest.spyOn(diseaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disease }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(diseaseService.update).toHaveBeenCalledWith(disease);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disease>>();
      const disease = new Disease();
      jest.spyOn(diseaseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disease }));
      saveSubject.complete();

      // THEN
      expect(diseaseService.create).toHaveBeenCalledWith(disease);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disease>>();
      const disease = { id: 123 };
      jest.spyOn(diseaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disease });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(diseaseService.update).toHaveBeenCalledWith(disease);
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

    describe('trackPlantFactoryById', () => {
      it('Should return tracked PlantFactory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlantFactoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
