import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HarvestService } from '../service/harvest.service';
import { IHarvest, Harvest } from '../harvest.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ILot } from 'app/entities/lot/lot.model';
import { LotService } from 'app/entities/lot/service/lot.service';

import { HarvestUpdateComponent } from './harvest-update.component';

describe('Harvest Management Update Component', () => {
  let comp: HarvestUpdateComponent;
  let fixture: ComponentFixture<HarvestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let harvestService: HarvestService;
  let cropService: CropService;
  let lotService: LotService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HarvestUpdateComponent],
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
      .overrideTemplate(HarvestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HarvestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    harvestService = TestBed.inject(HarvestService);
    cropService = TestBed.inject(CropService);
    lotService = TestBed.inject(LotService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Crop query and add missing value', () => {
      const harvest: IHarvest = { id: 456 };
      const cropID: ICrop = { id: 60604 };
      harvest.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 60340 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Lot query and add missing value', () => {
      const harvest: IHarvest = { id: 456 };
      const lotID: ILot = { id: 49765 };
      harvest.lotID = lotID;

      const lotCollection: ILot[] = [{ id: 39773 }];
      jest.spyOn(lotService, 'query').mockReturnValue(of(new HttpResponse({ body: lotCollection })));
      const additionalLots = [lotID];
      const expectedCollection: ILot[] = [...additionalLots, ...lotCollection];
      jest.spyOn(lotService, 'addLotToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      expect(lotService.query).toHaveBeenCalled();
      expect(lotService.addLotToCollectionIfMissing).toHaveBeenCalledWith(lotCollection, ...additionalLots);
      expect(comp.lotsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const harvest: IHarvest = { id: 456 };
      const cropID: ICrop = { id: 4518 };
      harvest.cropID = cropID;
      const lotID: ILot = { id: 41761 };
      harvest.lotID = lotID;

      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(harvest));
      expect(comp.cropsSharedCollection).toContain(cropID);
      expect(comp.lotsSharedCollection).toContain(lotID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Harvest>>();
      const harvest = { id: 123 };
      jest.spyOn(harvestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: harvest }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(harvestService.update).toHaveBeenCalledWith(harvest);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Harvest>>();
      const harvest = new Harvest();
      jest.spyOn(harvestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: harvest }));
      saveSubject.complete();

      // THEN
      expect(harvestService.create).toHaveBeenCalledWith(harvest);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Harvest>>();
      const harvest = { id: 123 };
      jest.spyOn(harvestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ harvest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(harvestService.update).toHaveBeenCalledWith(harvest);
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

    describe('trackLotById', () => {
      it('Should return tracked Lot primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLotById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
