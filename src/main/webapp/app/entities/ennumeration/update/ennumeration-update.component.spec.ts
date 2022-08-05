import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnnumerationService } from '../service/ennumeration.service';
import { IEnnumeration, Ennumeration } from '../ennumeration.model';

import { EnnumerationUpdateComponent } from './ennumeration-update.component';

describe('Ennumeration Management Update Component', () => {
  let comp: EnnumerationUpdateComponent;
  let fixture: ComponentFixture<EnnumerationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ennumerationService: EnnumerationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnnumerationUpdateComponent],
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
      .overrideTemplate(EnnumerationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnnumerationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ennumerationService = TestBed.inject(EnnumerationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ennumeration: IEnnumeration = { id: 456 };

      activatedRoute.data = of({ ennumeration });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ennumeration));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ennumeration>>();
      const ennumeration = { id: 123 };
      jest.spyOn(ennumerationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumeration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ennumeration }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ennumerationService.update).toHaveBeenCalledWith(ennumeration);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ennumeration>>();
      const ennumeration = new Ennumeration();
      jest.spyOn(ennumerationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumeration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ennumeration }));
      saveSubject.complete();

      // THEN
      expect(ennumerationService.create).toHaveBeenCalledWith(ennumeration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Ennumeration>>();
      const ennumeration = { id: 123 };
      jest.spyOn(ennumerationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumeration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ennumerationService.update).toHaveBeenCalledWith(ennumeration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
