import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SensorService } from '../service/sensor.service';
import { ISensor, Sensor } from '../sensor.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { ISensorModel } from 'app/entities/sensor-model/sensor-model.model';
import { SensorModelService } from 'app/entities/sensor-model/service/sensor-model.service';

import { SensorUpdateComponent } from './sensor-update.component';

describe('Sensor Management Update Component', () => {
  let comp: SensorUpdateComponent;
  let fixture: ComponentFixture<SensorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sensorService: SensorService;
  let locationService: LocationService;
  let deviceService: DeviceService;
  let sensorModelService: SensorModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SensorUpdateComponent],
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
      .overrideTemplate(SensorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SensorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sensorService = TestBed.inject(SensorService);
    locationService = TestBed.inject(LocationService);
    deviceService = TestBed.inject(DeviceService);
    sensorModelService = TestBed.inject(SensorModelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Location query and add missing value', () => {
      const sensor: ISensor = { id: 456 };
      const locationID: ILocation = { id: 45187 };
      sensor.locationID = locationID;

      const locationCollection: ILocation[] = [{ id: 25004 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const additionalLocations = [locationID];
      const expectedCollection: ILocation[] = [...additionalLocations, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, ...additionalLocations);
      expect(comp.locationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Device query and add missing value', () => {
      const sensor: ISensor = { id: 456 };
      const deviceID: IDevice = { id: 78549 };
      sensor.deviceID = deviceID;

      const deviceCollection: IDevice[] = [{ id: 23823 }];
      jest.spyOn(deviceService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceCollection })));
      const additionalDevices = [deviceID];
      const expectedCollection: IDevice[] = [...additionalDevices, ...deviceCollection];
      jest.spyOn(deviceService, 'addDeviceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      expect(deviceService.query).toHaveBeenCalled();
      expect(deviceService.addDeviceToCollectionIfMissing).toHaveBeenCalledWith(deviceCollection, ...additionalDevices);
      expect(comp.devicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SensorModel query and add missing value', () => {
      const sensor: ISensor = { id: 456 };
      const sensorModelID: ISensorModel = { id: 59502 };
      sensor.sensorModelID = sensorModelID;

      const sensorModelCollection: ISensorModel[] = [{ id: 33244 }];
      jest.spyOn(sensorModelService, 'query').mockReturnValue(of(new HttpResponse({ body: sensorModelCollection })));
      const additionalSensorModels = [sensorModelID];
      const expectedCollection: ISensorModel[] = [...additionalSensorModels, ...sensorModelCollection];
      jest.spyOn(sensorModelService, 'addSensorModelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      expect(sensorModelService.query).toHaveBeenCalled();
      expect(sensorModelService.addSensorModelToCollectionIfMissing).toHaveBeenCalledWith(sensorModelCollection, ...additionalSensorModels);
      expect(comp.sensorModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sensor: ISensor = { id: 456 };
      const locationID: ILocation = { id: 52794 };
      sensor.locationID = locationID;
      const deviceID: IDevice = { id: 73268 };
      sensor.deviceID = deviceID;
      const sensorModelID: ISensorModel = { id: 8480 };
      sensor.sensorModelID = sensorModelID;

      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sensor));
      expect(comp.locationsSharedCollection).toContain(locationID);
      expect(comp.devicesSharedCollection).toContain(deviceID);
      expect(comp.sensorModelsSharedCollection).toContain(sensorModelID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sensor>>();
      const sensor = { id: 123 };
      jest.spyOn(sensorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sensor }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sensorService.update).toHaveBeenCalledWith(sensor);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sensor>>();
      const sensor = new Sensor();
      jest.spyOn(sensorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sensor }));
      saveSubject.complete();

      // THEN
      expect(sensorService.create).toHaveBeenCalledWith(sensor);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Sensor>>();
      const sensor = { id: 123 };
      jest.spyOn(sensorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sensorService.update).toHaveBeenCalledWith(sensor);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLocationById', () => {
      it('Should return tracked Location primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDeviceById', () => {
      it('Should return tracked Device primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDeviceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSensorModelById', () => {
      it('Should return tracked SensorModel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSensorModelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
