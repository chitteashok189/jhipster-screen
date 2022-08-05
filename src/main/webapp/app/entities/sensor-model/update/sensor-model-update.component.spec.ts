import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SensorModelService } from '../service/sensor-model.service';
import { ISensorModel, SensorModel } from '../sensor-model.model';

import { SensorModelUpdateComponent } from './sensor-model-update.component';

describe('SensorModel Management Update Component', () => {
  let comp: SensorModelUpdateComponent;
  let fixture: ComponentFixture<SensorModelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sensorModelService: SensorModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SensorModelUpdateComponent],
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
      .overrideTemplate(SensorModelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SensorModelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sensorModelService = TestBed.inject(SensorModelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sensorModel: ISensorModel = { id: 456 };

      activatedRoute.data = of({ sensorModel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sensorModel));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SensorModel>>();
      const sensorModel = { id: 123 };
      jest.spyOn(sensorModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensorModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sensorModel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sensorModelService.update).toHaveBeenCalledWith(sensorModel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SensorModel>>();
      const sensorModel = new SensorModel();
      jest.spyOn(sensorModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensorModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sensorModel }));
      saveSubject.complete();

      // THEN
      expect(sensorModelService.create).toHaveBeenCalledWith(sensorModel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SensorModel>>();
      const sensorModel = { id: 123 };
      jest.spyOn(sensorModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sensorModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sensorModelService.update).toHaveBeenCalledWith(sensorModel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
