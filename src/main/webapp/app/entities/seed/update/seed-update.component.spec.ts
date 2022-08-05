import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SeedService } from '../service/seed.service';
import { ISeed, Seed } from '../seed.model';

import { SeedUpdateComponent } from './seed-update.component';

describe('Seed Management Update Component', () => {
  let comp: SeedUpdateComponent;
  let fixture: ComponentFixture<SeedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let seedService: SeedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SeedUpdateComponent],
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
      .overrideTemplate(SeedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SeedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    seedService = TestBed.inject(SeedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const seed: ISeed = { id: 456 };

      activatedRoute.data = of({ seed });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(seed));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Seed>>();
      const seed = { id: 123 };
      jest.spyOn(seedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: seed }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(seedService.update).toHaveBeenCalledWith(seed);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Seed>>();
      const seed = new Seed();
      jest.spyOn(seedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: seed }));
      saveSubject.complete();

      // THEN
      expect(seedService.create).toHaveBeenCalledWith(seed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Seed>>();
      const seed = { id: 123 };
      jest.spyOn(seedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seed });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(seedService.update).toHaveBeenCalledWith(seed);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
