import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NutrientService } from '../service/nutrient.service';
import { INutrient, Nutrient } from '../nutrient.model';

import { NutrientUpdateComponent } from './nutrient-update.component';

describe('Nutrient Management Update Component', () => {
  let comp: NutrientUpdateComponent;
  let fixture: ComponentFixture<NutrientUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nutrientService: NutrientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NutrientUpdateComponent],
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
      .overrideTemplate(NutrientUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NutrientUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nutrientService = TestBed.inject(NutrientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nutrient: INutrient = { id: 456 };

      activatedRoute.data = of({ nutrient });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nutrient));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nutrient>>();
      const nutrient = { id: 123 };
      jest.spyOn(nutrientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nutrient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nutrient }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nutrientService.update).toHaveBeenCalledWith(nutrient);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nutrient>>();
      const nutrient = new Nutrient();
      jest.spyOn(nutrientService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nutrient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nutrient }));
      saveSubject.complete();

      // THEN
      expect(nutrientService.create).toHaveBeenCalledWith(nutrient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nutrient>>();
      const nutrient = { id: 123 };
      jest.spyOn(nutrientService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nutrient });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nutrientService.update).toHaveBeenCalledWith(nutrient);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
