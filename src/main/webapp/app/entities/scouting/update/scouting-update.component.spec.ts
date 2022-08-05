import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ScoutingService } from '../service/scouting.service';
import { IScouting, Scouting } from '../scouting.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';

import { ScoutingUpdateComponent } from './scouting-update.component';

describe('Scouting Management Update Component', () => {
  let comp: ScoutingUpdateComponent;
  let fixture: ComponentFixture<ScoutingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let scoutingService: ScoutingService;
  let plantFactoryService: PlantFactoryService;
  let cropService: CropService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ScoutingUpdateComponent],
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
      .overrideTemplate(ScoutingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ScoutingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    scoutingService = TestBed.inject(ScoutingService);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    cropService = TestBed.inject(CropService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlantFactory query and add missing value', () => {
      const scouting: IScouting = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 93344 };
      scouting.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 18177 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Crop query and add missing value', () => {
      const scouting: IScouting = { id: 456 };
      const cropID: ICrop = { id: 71617 };
      scouting.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 4028 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const scouting: IScouting = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 32776 };
      scouting.plantFactoryID = plantFactoryID;
      const cropID: ICrop = { id: 66684 };
      scouting.cropID = cropID;

      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(scouting));
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
      expect(comp.cropsSharedCollection).toContain(cropID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Scouting>>();
      const scouting = { id: 123 };
      jest.spyOn(scoutingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scouting }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(scoutingService.update).toHaveBeenCalledWith(scouting);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Scouting>>();
      const scouting = new Scouting();
      jest.spyOn(scoutingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scouting }));
      saveSubject.complete();

      // THEN
      expect(scoutingService.create).toHaveBeenCalledWith(scouting);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Scouting>>();
      const scouting = { id: 123 };
      jest.spyOn(scoutingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scouting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(scoutingService.update).toHaveBeenCalledWith(scouting);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPlantFactoryById', () => {
      it('Should return tracked PlantFactory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlantFactoryById(0, entity);
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
  });
});
