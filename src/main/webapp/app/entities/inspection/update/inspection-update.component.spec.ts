import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InspectionService } from '../service/inspection.service';
import { IInspection, Inspection } from '../inspection.model';
import { IHarvest } from 'app/entities/harvest/harvest.model';
import { HarvestService } from 'app/entities/harvest/service/harvest.service';

import { InspectionUpdateComponent } from './inspection-update.component';

describe('Inspection Management Update Component', () => {
  let comp: InspectionUpdateComponent;
  let fixture: ComponentFixture<InspectionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inspectionService: InspectionService;
  let harvestService: HarvestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InspectionUpdateComponent],
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
      .overrideTemplate(InspectionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InspectionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inspectionService = TestBed.inject(InspectionService);
    harvestService = TestBed.inject(HarvestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Harvest query and add missing value', () => {
      const inspection: IInspection = { id: 456 };
      const harvestID: IHarvest = { id: 57643 };
      inspection.harvestID = harvestID;

      const harvestCollection: IHarvest[] = [{ id: 8937 }];
      jest.spyOn(harvestService, 'query').mockReturnValue(of(new HttpResponse({ body: harvestCollection })));
      const additionalHarvests = [harvestID];
      const expectedCollection: IHarvest[] = [...additionalHarvests, ...harvestCollection];
      jest.spyOn(harvestService, 'addHarvestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inspection });
      comp.ngOnInit();

      expect(harvestService.query).toHaveBeenCalled();
      expect(harvestService.addHarvestToCollectionIfMissing).toHaveBeenCalledWith(harvestCollection, ...additionalHarvests);
      expect(comp.harvestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inspection: IInspection = { id: 456 };
      const harvestID: IHarvest = { id: 30596 };
      inspection.harvestID = harvestID;

      activatedRoute.data = of({ inspection });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inspection));
      expect(comp.harvestsSharedCollection).toContain(harvestID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inspection>>();
      const inspection = { id: 123 };
      jest.spyOn(inspectionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspection });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspection }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inspectionService.update).toHaveBeenCalledWith(inspection);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inspection>>();
      const inspection = new Inspection();
      jest.spyOn(inspectionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspection });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspection }));
      saveSubject.complete();

      // THEN
      expect(inspectionService.create).toHaveBeenCalledWith(inspection);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Inspection>>();
      const inspection = { id: 123 };
      jest.spyOn(inspectionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspection });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inspectionService.update).toHaveBeenCalledWith(inspection);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackHarvestById', () => {
      it('Should return tracked Harvest primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHarvestById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
