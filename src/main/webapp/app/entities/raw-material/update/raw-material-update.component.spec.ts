import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RawMaterialService } from '../service/raw-material.service';
import { IRawMaterial, RawMaterial } from '../raw-material.model';

import { RawMaterialUpdateComponent } from './raw-material-update.component';

describe('RawMaterial Management Update Component', () => {
  let comp: RawMaterialUpdateComponent;
  let fixture: ComponentFixture<RawMaterialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rawMaterialService: RawMaterialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RawMaterialUpdateComponent],
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
      .overrideTemplate(RawMaterialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RawMaterialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rawMaterialService = TestBed.inject(RawMaterialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const rawMaterial: IRawMaterial = { id: 456 };

      activatedRoute.data = of({ rawMaterial });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rawMaterial));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterial>>();
      const rawMaterial = { id: 123 };
      jest.spyOn(rawMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterial }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rawMaterialService.update).toHaveBeenCalledWith(rawMaterial);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterial>>();
      const rawMaterial = new RawMaterial();
      jest.spyOn(rawMaterialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterial }));
      saveSubject.complete();

      // THEN
      expect(rawMaterialService.create).toHaveBeenCalledWith(rawMaterial);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterial>>();
      const rawMaterial = { id: 123 };
      jest.spyOn(rawMaterialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rawMaterialService.update).toHaveBeenCalledWith(rawMaterial);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
