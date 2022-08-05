import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';
import { IPlantFactoryController, PlantFactoryController } from '../plant-factory-controller.model';

import { PlantFactoryControllerUpdateComponent } from './plant-factory-controller-update.component';

describe('PlantFactoryController Management Update Component', () => {
  let comp: PlantFactoryControllerUpdateComponent;
  let fixture: ComponentFixture<PlantFactoryControllerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plantFactoryControllerService: PlantFactoryControllerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlantFactoryControllerUpdateComponent],
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
      .overrideTemplate(PlantFactoryControllerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantFactoryControllerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plantFactoryControllerService = TestBed.inject(PlantFactoryControllerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const plantFactoryController: IPlantFactoryController = { id: 456 };

      activatedRoute.data = of({ plantFactoryController });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(plantFactoryController));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactoryController>>();
      const plantFactoryController = { id: 123 };
      jest.spyOn(plantFactoryControllerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactoryController });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantFactoryController }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(plantFactoryControllerService.update).toHaveBeenCalledWith(plantFactoryController);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactoryController>>();
      const plantFactoryController = new PlantFactoryController();
      jest.spyOn(plantFactoryControllerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactoryController });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantFactoryController }));
      saveSubject.complete();

      // THEN
      expect(plantFactoryControllerService.create).toHaveBeenCalledWith(plantFactoryController);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PlantFactoryController>>();
      const plantFactoryController = { id: 123 };
      jest.spyOn(plantFactoryControllerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantFactoryController });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plantFactoryControllerService.update).toHaveBeenCalledWith(plantFactoryController);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
