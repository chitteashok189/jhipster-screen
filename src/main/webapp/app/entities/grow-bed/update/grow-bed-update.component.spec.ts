import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GrowBedService } from '../service/grow-bed.service';
import { IGrowBed, GrowBed } from '../grow-bed.model';

import { GrowBedUpdateComponent } from './grow-bed-update.component';

describe('GrowBed Management Update Component', () => {
  let comp: GrowBedUpdateComponent;
  let fixture: ComponentFixture<GrowBedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let growBedService: GrowBedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GrowBedUpdateComponent],
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
      .overrideTemplate(GrowBedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrowBedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    growBedService = TestBed.inject(GrowBedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const growBed: IGrowBed = { id: 456 };

      activatedRoute.data = of({ growBed });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(growBed));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrowBed>>();
      const growBed = { id: 123 };
      jest.spyOn(growBedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ growBed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: growBed }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(growBedService.update).toHaveBeenCalledWith(growBed);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrowBed>>();
      const growBed = new GrowBed();
      jest.spyOn(growBedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ growBed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: growBed }));
      saveSubject.complete();

      // THEN
      expect(growBedService.create).toHaveBeenCalledWith(growBed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<GrowBed>>();
      const growBed = { id: 123 };
      jest.spyOn(growBedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ growBed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(growBedService.update).toHaveBeenCalledWith(growBed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
