import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeviceService } from '../service/device.service';
import { IDevice, Device } from '../device.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IDeviceLevel } from 'app/entities/device-level/device-level.model';
import { DeviceLevelService } from 'app/entities/device-level/service/device-level.service';
import { IDeviceModel } from 'app/entities/device-model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/service/device-model.service';

import { DeviceUpdateComponent } from './device-update.component';

describe('Device Management Update Component', () => {
  let comp: DeviceUpdateComponent;
  let fixture: ComponentFixture<DeviceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deviceService: DeviceService;
  let plantFactoryService: PlantFactoryService;
  let deviceLevelService: DeviceLevelService;
  let deviceModelService: DeviceModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeviceUpdateComponent],
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
      .overrideTemplate(DeviceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deviceService = TestBed.inject(DeviceService);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    deviceLevelService = TestBed.inject(DeviceLevelService);
    deviceModelService = TestBed.inject(DeviceModelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlantFactory query and add missing value', () => {
      const device: IDevice = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 17911 };
      device.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 60495 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ device });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DeviceLevel query and add missing value', () => {
      const device: IDevice = { id: 456 };
      const deviceLevelID: IDeviceLevel = { id: 46592 };
      device.deviceLevelID = deviceLevelID;

      const deviceLevelCollection: IDeviceLevel[] = [{ id: 76688 }];
      jest.spyOn(deviceLevelService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceLevelCollection })));
      const additionalDeviceLevels = [deviceLevelID];
      const expectedCollection: IDeviceLevel[] = [...additionalDeviceLevels, ...deviceLevelCollection];
      jest.spyOn(deviceLevelService, 'addDeviceLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ device });
      comp.ngOnInit();

      expect(deviceLevelService.query).toHaveBeenCalled();
      expect(deviceLevelService.addDeviceLevelToCollectionIfMissing).toHaveBeenCalledWith(deviceLevelCollection, ...additionalDeviceLevels);
      expect(comp.deviceLevelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DeviceModel query and add missing value', () => {
      const device: IDevice = { id: 456 };
      const deviceModelID: IDeviceModel = { id: 84116 };
      device.deviceModelID = deviceModelID;

      const deviceModelCollection: IDeviceModel[] = [{ id: 81611 }];
      jest.spyOn(deviceModelService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceModelCollection })));
      const additionalDeviceModels = [deviceModelID];
      const expectedCollection: IDeviceModel[] = [...additionalDeviceModels, ...deviceModelCollection];
      jest.spyOn(deviceModelService, 'addDeviceModelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ device });
      comp.ngOnInit();

      expect(deviceModelService.query).toHaveBeenCalled();
      expect(deviceModelService.addDeviceModelToCollectionIfMissing).toHaveBeenCalledWith(deviceModelCollection, ...additionalDeviceModels);
      expect(comp.deviceModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const device: IDevice = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 18016 };
      device.plantFactoryID = plantFactoryID;
      const deviceLevelID: IDeviceLevel = { id: 34336 };
      device.deviceLevelID = deviceLevelID;
      const deviceModelID: IDeviceModel = { id: 69603 };
      device.deviceModelID = deviceModelID;

      activatedRoute.data = of({ device });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(device));
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
      expect(comp.deviceLevelsSharedCollection).toContain(deviceLevelID);
      expect(comp.deviceModelsSharedCollection).toContain(deviceModelID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Device>>();
      const device = { id: 123 };
      jest.spyOn(deviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ device });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: device }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(deviceService.update).toHaveBeenCalledWith(device);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Device>>();
      const device = new Device();
      jest.spyOn(deviceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ device });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: device }));
      saveSubject.complete();

      // THEN
      expect(deviceService.create).toHaveBeenCalledWith(device);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Device>>();
      const device = { id: 123 };
      jest.spyOn(deviceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ device });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deviceService.update).toHaveBeenCalledWith(device);
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

    describe('trackDeviceLevelById', () => {
      it('Should return tracked DeviceLevel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviceLevelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDeviceModelById', () => {
      it('Should return tracked DeviceModel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviceModelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
