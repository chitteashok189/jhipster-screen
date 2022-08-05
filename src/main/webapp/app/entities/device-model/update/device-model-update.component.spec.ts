import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeviceModelService } from '../service/device-model.service';
import { IDeviceModel, DeviceModel } from '../device-model.model';

import { DeviceModelUpdateComponent } from './device-model-update.component';

describe('DeviceModel Management Update Component', () => {
  let comp: DeviceModelUpdateComponent;
  let fixture: ComponentFixture<DeviceModelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deviceModelService: DeviceModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeviceModelUpdateComponent],
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
      .overrideTemplate(DeviceModelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviceModelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deviceModelService = TestBed.inject(DeviceModelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const deviceModel: IDeviceModel = { id: 456 };

      activatedRoute.data = of({ deviceModel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(deviceModel));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceModel>>();
      const deviceModel = { id: 123 };
      jest.spyOn(deviceModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deviceModel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(deviceModelService.update).toHaveBeenCalledWith(deviceModel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceModel>>();
      const deviceModel = new DeviceModel();
      jest.spyOn(deviceModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deviceModel }));
      saveSubject.complete();

      // THEN
      expect(deviceModelService.create).toHaveBeenCalledWith(deviceModel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceModel>>();
      const deviceModel = { id: 123 };
      jest.spyOn(deviceModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deviceModelService.update).toHaveBeenCalledWith(deviceModel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
