import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlantFactoryService } from '../service/plant-factory.service';
import { IPlantFactory, PlantFactory } from '../plant-factory.model';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IFarm } from 'app/entities/farm/farm.model';
import { FarmService } from 'app/entities/farm/service/farm.service';

import { PlantFactoryUpdateComponent } from './plant-factory-update.component';

describe('PlantFactory Management Update Component', () => {
  let comp: PlantFactoryUpdateComponent;
  let fixture: ComponentFixture<PlantFactoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plantFactoryService: PlantFactoryService;
  let deviceService: DeviceService;
  let farmService: FarmService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlantFactoryUpdateComponent],
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
      .overrideTemplate(PlantFactoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantFactoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    deviceService = TestBed.inject(DeviceService);
    farmService = TestBed.inject(FarmService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Device query and add missing value', () => {
      const plantFactory: IPlantFactory = { id: 456 };
      const deviceID: IDevice = { id: 25981 };
      plantFactory.deviceID = deviceID;

      const deviceCollection: IDevice[] = [{ id: 47304 }];
      jest.spyOn(deviceService, 'query').mockReturnValue(of(new HttpResponse({ body: deviceCollection })));
      const additionalDevices = [deviceID];
      const expectedCollection: IDevice[] = [...additionalDevices, ...deviceCollection];
      jest.spyOn(deviceService, 'addDeviceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      expect(deviceService.query).toHaveBeenCalled();
      expect(deviceService.addDeviceToCollectionIfMissing).toHaveBeenCalledWith(deviceCollection, ...additionalDevices);
      expect(comp.devicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Farm query and add missing value', () => {
      const plantFactory: IPlantFactory = { id: 456 };
      const farmID: IFarm = { id: 66220 };
      plantFactory.farmID = farmID;

      const farmCollection: IFarm[] = [{ id: 23207 }];
      jest.spyOn(farmService, 'query').mockReturnValue(of(new HttpResponse({ body: farmCollection })));
      const additionalFarms = [farmID];
      const expectedCollection: IFarm[] = [...additionalFarms, ...farmCollection];
      jest.spyOn(farmService, 'addFarmToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      expect(farmService.query).toHaveBeenCalled();
      expect(farmService.addFarmToCollectionIfMissing).toHaveBeenCalledWith(farmCollection, ...additionalFarms);
      expect(comp.farmsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plantFactory: IPlantFactory = { id: 456 };
      const deviceID: IDevice = { id: 59585 };
      plantFactory.deviceID = deviceID;
      const farmID: IFarm = { id: 3733 };
      plantFactory.farmID = farmID;

      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(plantFactory));
      expect(comp.devicesSharedCollection).toContain(deviceID);
      expect(comp.farmsSharedCollection).toContain(farmID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactory>>();
      const plantFactory = { id: 123 };
      jest.spyOn(plantFactoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantFactory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(plantFactoryService.update).toHaveBeenCalledWith(plantFactory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactory>>();
      const plantFactory = new PlantFactory();
      jest.spyOn(plantFactoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantFactory }));
      saveSubject.complete();

      // THEN
      expect(plantFactoryService.create).toHaveBeenCalledWith(plantFactory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactory>>();
      const plantFactory = { id: 123 };
      jest.spyOn(plantFactoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plantFactoryService.update).toHaveBeenCalledWith(plantFactory);
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

    describe('trackFarmById', () => {
      it('Should return tracked Farm primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFarmById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
