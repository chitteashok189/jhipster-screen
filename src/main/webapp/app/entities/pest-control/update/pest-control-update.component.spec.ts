import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PestControlService } from '../service/pest-control.service';
import { IPestControl, PestControl } from '../pest-control.model';
import { IPest } from 'app/entities/pest/pest.model';
import { PestService } from 'app/entities/pest/service/pest.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';

import { PestControlUpdateComponent } from './pest-control-update.component';

describe('PestControl Management Update Component', () => {
  let comp: PestControlUpdateComponent;
  let fixture: ComponentFixture<PestControlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pestControlService: PestControlService;
  let pestService: PestService;
  let cropService: CropService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PestControlUpdateComponent],
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
      .overrideTemplate(PestControlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PestControlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pestControlService = TestBed.inject(PestControlService);
    pestService = TestBed.inject(PestService);
    cropService = TestBed.inject(CropService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pest query and add missing value', () => {
      const pestControl: IPestControl = { id: 456 };
      const pestID: IPest = { id: 76573 };
      pestControl.pestID = pestID;

      const pestCollection: IPest[] = [{ id: 3537 }];
      jest.spyOn(pestService, 'query').mockReturnValue(of(new HttpResponse({ body: pestCollection })));
      const additionalPests = [pestID];
      const expectedCollection: IPest[] = [...additionalPests, ...pestCollection];
      jest.spyOn(pestService, 'addPestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      expect(pestService.query).toHaveBeenCalled();
      expect(pestService.addPestToCollectionIfMissing).toHaveBeenCalledWith(pestCollection, ...additionalPests);
      expect(comp.pestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Crop query and add missing value', () => {
      const pestControl: IPestControl = { id: 456 };
      const cropID: ICrop = { id: 28041 };
      pestControl.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 27473 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pestControl: IPestControl = { id: 456 };
      const pestID: IPest = { id: 28178 };
      pestControl.pestID = pestID;
      const cropID: ICrop = { id: 76739 };
      pestControl.cropID = cropID;

      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pestControl));
      expect(comp.pestsSharedCollection).toContain(pestID);
      expect(comp.cropsSharedCollection).toContain(cropID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PestControl>>();
      const pestControl = { id: 123 };
      jest.spyOn(pestControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pestControl }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pestControlService.update).toHaveBeenCalledWith(pestControl);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PestControl>>();
      const pestControl = new PestControl();
      jest.spyOn(pestControlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pestControl }));
      saveSubject.complete();

      // THEN
      expect(pestControlService.create).toHaveBeenCalledWith(pestControl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PestControl>>();
      const pestControl = { id: 123 };
      jest.spyOn(pestControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pestControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pestControlService.update).toHaveBeenCalledWith(pestControl);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPestById', () => {
      it('Should return tracked Pest primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPestById(0, entity);
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
