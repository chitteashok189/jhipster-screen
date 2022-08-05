import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlantService } from '../service/plant.service';
import { IPlant, Plant } from '../plant.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';

import { PlantUpdateComponent } from './plant-update.component';

describe('Plant Management Update Component', () => {
  let comp: PlantUpdateComponent;
  let fixture: ComponentFixture<PlantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plantService: PlantService;
  let cropService: CropService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlantUpdateComponent],
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
      .overrideTemplate(PlantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plantService = TestBed.inject(PlantService);
    cropService = TestBed.inject(CropService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Crop query and add missing value', () => {
      const plant: IPlant = { id: 456 };
      const cropID: ICrop = { id: 75194 };
      plant.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 36309 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plant });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plant: IPlant = { id: 456 };
      const cropID: ICrop = { id: 2250 };
      plant.cropID = cropID;

      activatedRoute.data = of({ plant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(plant));
      expect(comp.cropsSharedCollection).toContain(cropID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plant>>();
      const plant = { id: 123 };
      jest.spyOn(plantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(plantService.update).toHaveBeenCalledWith(plant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plant>>();
      const plant = new Plant();
      jest.spyOn(plantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plant }));
      saveSubject.complete();

      // THEN
      expect(plantService.create).toHaveBeenCalledWith(plant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Plant>>();
      const plant = { id: 123 };
      jest.spyOn(plantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plantService.update).toHaveBeenCalledWith(plant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCropById', () => {
      it('Should return tracked Crop primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCropById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
