import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BreederService } from '../service/breeder.service';
import { IBreeder, Breeder } from '../breeder.model';

import { BreederUpdateComponent } from './breeder-update.component';

describe('Breeder Management Update Component', () => {
  let comp: BreederUpdateComponent;
  let fixture: ComponentFixture<BreederUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let breederService: BreederService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BreederUpdateComponent],
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
      .overrideTemplate(BreederUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BreederUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    breederService = TestBed.inject(BreederService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const breeder: IBreeder = { id: 456 };

      activatedRoute.data = of({ breeder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(breeder));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Breeder>>();
      const breeder = { id: 123 };
      jest.spyOn(breederService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ breeder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: breeder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(breederService.update).toHaveBeenCalledWith(breeder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Breeder>>();
      const breeder = new Breeder();
      jest.spyOn(breederService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ breeder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: breeder }));
      saveSubject.complete();

      // THEN
      expect(breederService.create).toHaveBeenCalledWith(breeder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Breeder>>();
      const breeder = { id: 123 };
      jest.spyOn(breederService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ breeder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(breederService.update).toHaveBeenCalledWith(breeder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
