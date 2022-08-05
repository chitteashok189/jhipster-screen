import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IrrigationService } from '../service/irrigation.service';
import { IIrrigation, Irrigation } from '../irrigation.model';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';

import { IrrigationUpdateComponent } from './irrigation-update.component';

describe('Irrigation Management Update Component', () => {
  let comp: IrrigationUpdateComponent;
  let fixture: ComponentFixture<IrrigationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let irrigationService: IrrigationService;
  let deviceService: DeviceService;
  let plantFactoryService: PlantFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IrrigationUpdateComponent],
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
      .overrideTemplate(IrrigationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IrrigationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    irrigationService = TestBed.inject(IrrigationService);
    deviceService = TestBed.inject(DeviceService);
    plantFactoryService = TestBed.inject(PlantFactoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Device query and add missing value', () => {
      const irrigation: IIrrigation = { id: 456 };
      const deviceID: IDevice = { id: 15022 };
      irrigation.deviceID = deviceID;

      const deviceCollection: IDevice[] = [{ id: 71008 }];
      jest.spyOn(deviceService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceCollection })));
      const additionalDevices = [deviceID];
      const expectedCollection: IDevice[] = [...additionalDevices, ...deviceCollection];
      jest.spyOn(deviceService, 'addDeviceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      expect(deviceService.query).toHaveBeenCalled();
      expect(deviceService.addDeviceToCollectionIfMissing).toHaveBeenCalledWith(deviceCollection, ...additionalDevices);
      expect(comp.devicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlantFactory query and add missing value', () => {
      const irrigation: IIrrigation = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 45163 };
      irrigation.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 51325 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const irrigation: IIrrigation = { id: 456 };
      const deviceID: IDevice = { id: 38028 };
      irrigation.deviceID = deviceID;
      const plantFactoryID: IPlantFactory = { id: 22064 };
      irrigation.plantFactoryID = plantFactoryID;

      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(irrigation));
      expect(comp.devicesSharedCollection).toContain(deviceID);
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Irrigation>>();
      const irrigation = { id: 123 };
      jest.spyOn(irrigationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: irrigation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(irrigationService.update).toHaveBeenCalledWith(irrigation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Irrigation>>();
      const irrigation = new Irrigation();
      jest.spyOn(irrigationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: irrigation }));
      saveSubject.complete();

      // THEN
      expect(irrigationService.create).toHaveBeenCalledWith(irrigation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Irrigation>>();
      const irrigation = { id: 123 };
      jest.spyOn(irrigationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ irrigation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(irrigationService.update).toHaveBeenCalledWith(irrigation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDeviceById', () => {
      it('Should return tracked Device primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviceById(0, entity);
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
