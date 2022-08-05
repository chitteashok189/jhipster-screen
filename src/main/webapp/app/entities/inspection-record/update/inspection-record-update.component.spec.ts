import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InspectionRecordService } from '../service/inspection-record.service';
import { IInspectionRecord, InspectionRecord } from '../inspection-record.model';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { InspectionService } from 'app/entities/inspection/service/inspection.service';
import { ILot } from 'app/entities/lot/lot.model';
import { LotService } from 'app/entities/lot/service/lot.service';

import { InspectionRecordUpdateComponent } from './inspection-record-update.component';

describe('InspectionRecord Management Update Component', () => {
  let comp: InspectionRecordUpdateComponent;
  let fixture: ComponentFixture<InspectionRecordUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inspectionRecordService: InspectionRecordService;
  let inspectionService: InspectionService;
  let lotService: LotService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InspectionRecordUpdateComponent],
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
      .overrideTemplate(InspectionRecordUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InspectionRecordUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inspectionRecordService = TestBed.inject(InspectionRecordService);
    inspectionService = TestBed.inject(InspectionService);
    lotService = TestBed.inject(LotService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Inspection query and add missing value', () => {
      const inspectionRecord: IInspectionRecord = { id: 456 };
      const inspectionID: IInspection = { id: 214 };
      inspectionRecord.inspectionID = inspectionID;

      const inspectionCollection: IInspection[] = [{ id: 7916 }];
      jest.spyOn(inspectionService, 'query').mockReturnValue(of(new HttpResponse({ body: inspectionCollection })));
      const additionalInspections = [inspectionID];
      const expectedCollection: IInspection[] = [...additionalInspections, ...inspectionCollection];
      jest.spyOn(inspectionService, 'addInspectionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      expect(inspectionService.query).toHaveBeenCalled();
      expect(inspectionService.addInspectionToCollectionIfMissing).toHaveBeenCalledWith(inspectionCollection, ...additionalInspections);
      expect(comp.inspectionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Lot query and add missing value', () => {
      const inspectionRecord: IInspectionRecord = { id: 456 };
      const lotID: ILot = { id: 10230 };
      inspectionRecord.lotID = lotID;

      const lotCollection: ILot[] = [{ id: 25476 }];
      jest.spyOn(lotService, 'query').mockReturnValue(of(new HttpResponse({ body: lotCollection })));
      const additionalLots = [lotID];
      const expectedCollection: ILot[] = [...additionalLots, ...lotCollection];
      jest.spyOn(lotService, 'addLotToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      expect(lotService.query).toHaveBeenCalled();
      expect(lotService.addLotToCollectionIfMissing).toHaveBeenCalledWith(lotCollection, ...additionalLots);
      expect(comp.lotsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inspectionRecord: IInspectionRecord = { id: 456 };
      const inspectionID: IInspection = { id: 88845 };
      inspectionRecord.inspectionID = inspectionID;
      const lotID: ILot = { id: 56889 };
      inspectionRecord.lotID = lotID;

      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(inspectionRecord));
      expect(comp.inspectionsSharedCollection).toContain(inspectionID);
      expect(comp.lotsSharedCollection).toContain(lotID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InspectionRecord>>();
      const inspectionRecord = { id: 123 };
      jest.spyOn(inspectionRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspectionRecord }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(inspectionRecordService.update).toHaveBeenCalledWith(inspectionRecord);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InspectionRecord>>();
      const inspectionRecord = new InspectionRecord();
      jest.spyOn(inspectionRecordService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inspectionRecord }));
      saveSubject.complete();

      // THEN
      expect(inspectionRecordService.create).toHaveBeenCalledWith(inspectionRecord);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InspectionRecord>>();
      const inspectionRecord = { id: 123 };
      jest.spyOn(inspectionRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inspectionRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inspectionRecordService.update).toHaveBeenCalledWith(inspectionRecord);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInspectionById', () => {
      it('Should return tracked Inspection primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInspectionById(0, entity);
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
