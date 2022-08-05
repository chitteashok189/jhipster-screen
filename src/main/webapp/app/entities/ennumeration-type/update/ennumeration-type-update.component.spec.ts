import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnnumerationTypeService } from '../service/ennumeration-type.service';
import { IEnnumerationType, EnnumerationType } from '../ennumeration-type.model';

import { EnnumerationTypeUpdateComponent } from './ennumeration-type-update.component';

describe('EnnumerationType Management Update Component', () => {
  let comp: EnnumerationTypeUpdateComponent;
  let fixture: ComponentFixture<EnnumerationTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ennumerationTypeService: EnnumerationTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnnumerationTypeUpdateComponent],
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
      .overrideTemplate(EnnumerationTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnnumerationTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ennumerationTypeService = TestBed.inject(EnnumerationTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ennumerationType: IEnnumerationType = { id: 456 };

      activatedRoute.data = of({ ennumerationType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ennumerationType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnnumerationType>>();
      const ennumerationType = { id: 123 };
      jest.spyOn(ennumerationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumerationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ennumerationType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ennumerationTypeService.update).toHaveBeenCalledWith(ennumerationType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnnumerationType>>();
      const ennumerationType = new EnnumerationType();
      jest.spyOn(ennumerationTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumerationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ennumerationType }));
      saveSubject.complete();

      // THEN
      expect(ennumerationTypeService.create).toHaveBeenCalledWith(ennumerationType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EnnumerationType>>();
      const ennumerationType = { id: 123 };
      jest.spyOn(ennumerationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ennumerationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ennumerationTypeService.update).toHaveBeenCalledWith(ennumerationType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
