import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeviceLevelService } from '../service/device-level.service';
import { IDeviceLevel, DeviceLevel } from '../device-level.model';

import { DeviceLevelUpdateComponent } from './device-level-update.component';

describe('DeviceLevel Management Update Component', () => {
  let comp: DeviceLevelUpdateComponent;
  let fixture: ComponentFixture<DeviceLevelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deviceLevelService: DeviceLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeviceLevelUpdateComponent],
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
      .overrideTemplate(DeviceLevelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviceLevelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deviceLevelService = TestBed.inject(DeviceLevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const deviceLevel: IDeviceLevel = { id: 456 };

      activatedRoute.data = of({ deviceLevel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(deviceLevel));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceLevel>>();
      const deviceLevel = { id: 123 };
      jest.spyOn(deviceLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deviceLevel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(deviceLevelService.update).toHaveBeenCalledWith(deviceLevel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceLevel>>();
      const deviceLevel = new DeviceLevel();
      jest.spyOn(deviceLevelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deviceLevel }));
      saveSubject.complete();

      // THEN
      expect(deviceLevelService.create).toHaveBeenCalledWith(deviceLevel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DeviceLevel>>();
      const deviceLevel = { id: 123 };
      jest.spyOn(deviceLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deviceLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deviceLevelService.update).toHaveBeenCalledWith(deviceLevel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
