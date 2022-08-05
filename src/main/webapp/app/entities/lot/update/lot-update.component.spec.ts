import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LotService } from '../service/lot.service';
import { ILot, Lot } from '../lot.model';
import { ISeed } from 'app/entities/seed/seed.model';
import { SeedService } from 'app/entities/seed/service/seed.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';

import { LotUpdateComponent } from './lot-update.component';

describe('Lot Management Update Component', () => {
  let comp: LotUpdateComponent;
  let fixture: ComponentFixture<LotUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lotService: LotService;
  let seedService: SeedService;
  let cropService: CropService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LotUpdateComponent],
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
      .overrideTemplate(LotUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LotUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lotService = TestBed.inject(LotService);
    seedService = TestBed.inject(SeedService);
    cropService = TestBed.inject(CropService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Seed query and add missing value', () => {
      const lot: ILot = { id: 456 };
      const seedID: ISeed = { id: 99823 };
      lot.seedID = seedID;

      const seedCollection: ISeed[] = [{ id: 52690 }];
      jest.spyOn(seedService, 'query').mockReturnValue(of(new HttpResponse({ body: seedCollection })));
      const additionalSeeds = [seedID];
      const expectedCollection: ISeed[] = [...additionalSeeds, ...seedCollection];
      jest.spyOn(seedService, 'addSeedToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      expect(seedService.query).toHaveBeenCalled();
      expect(seedService.addSeedToCollectionIfMissing).toHaveBeenCalledWith(seedCollection, ...additionalSeeds);
      expect(comp.seedsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Crop query and add missing value', () => {
      const lot: ILot = { id: 456 };
      const cropID: ICrop = { id: 25445 };
      lot.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 68738 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const lot: ILot = { id: 456 };
      const seedID: ISeed = { id: 78759 };
      lot.seedID = seedID;
      const cropID: ICrop = { id: 79208 };
      lot.cropID = cropID;

      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(lot));
      expect(comp.seedsSharedCollection).toContain(seedID);
      expect(comp.cropsSharedCollection).toContain(cropID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lot>>();
      const lot = { id: 123 };
      jest.spyOn(lotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lot }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(lotService.update).toHaveBeenCalledWith(lot);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lot>>();
      const lot = new Lot();
      jest.spyOn(lotService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lot }));
      saveSubject.complete();

      // THEN
      expect(lotService.create).toHaveBeenCalledWith(lot);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Lot>>();
      const lot = { id: 123 };
      jest.spyOn(lotService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lotService.update).toHaveBeenCalledWith(lot);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSeedById', () => {
      it('Should return tracked Seed primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSeedById(0, entity);
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
