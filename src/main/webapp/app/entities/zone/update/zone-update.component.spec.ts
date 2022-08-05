import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ZoneService } from '../service/zone.service';
import { IZone, Zone } from '../zone.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IGrowBed } from 'app/entities/grow-bed/grow-bed.model';
import { GrowBedService } from 'app/entities/grow-bed/service/grow-bed.service';

import { ZoneUpdateComponent } from './zone-update.component';

describe('Zone Management Update Component', () => {
  let comp: ZoneUpdateComponent;
  let fixture: ComponentFixture<ZoneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let zoneService: ZoneService;
  let plantFactoryService: PlantFactoryService;
  let growBedService: GrowBedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ZoneUpdateComponent],
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
      .overrideTemplate(ZoneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ZoneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    zoneService = TestBed.inject(ZoneService);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    growBedService = TestBed.inject(GrowBedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlantFactory query and add missing value', () => {
      const zone: IZone = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 92604 };
      zone.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 38839 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GrowBed query and add missing value', () => {
      const zone: IZone = { id: 456 };
      const growBedID: IGrowBed = { id: 16907 };
      zone.growBedID = growBedID;

      const growBedCollection: IGrowBed[] = [{ id: 62787 }];
      jest.spyOn(growBedService, 'query').mockReturnValue(of(new HttpResponse({ body: growBedCollection })));
      const additionalGrowBeds = [growBedID];
      const expectedCollection: IGrowBed[] = [...additionalGrowBeds, ...growBedCollection];
      jest.spyOn(growBedService, 'addGrowBedToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      expect(growBedService.query).toHaveBeenCalled();
      expect(growBedService.addGrowBedToCollectionIfMissing).toHaveBeenCalledWith(growBedCollection, ...additionalGrowBeds);
      expect(comp.growBedsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const zone: IZone = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 40521 };
      zone.plantFactoryID = plantFactoryID;
      const growBedID: IGrowBed = { id: 70685 };
      zone.growBedID = growBedID;

      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(zone));
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
      expect(comp.growBedsSharedCollection).toContain(growBedID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Zone>>();
      const zone = { id: 123 };
      jest.spyOn(zoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: zone }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(zoneService.update).toHaveBeenCalledWith(zone);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Zone>>();
      const zone = new Zone();
      jest.spyOn(zoneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: zone }));
      saveSubject.complete();

      // THEN
      expect(zoneService.create).toHaveBeenCalledWith(zone);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Zone>>();
      const zone = { id: 123 };
      jest.spyOn(zoneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ zone });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(zoneService.update).toHaveBeenCalledWith(zone);
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

    describe('trackGrowBedById', () => {
      it('Should return tracked GrowBed primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGrowBedById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
