import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleTypeAttributeService } from '../service/role-type-attribute.service';
import { IRoleTypeAttribute, RoleTypeAttribute } from '../role-type-attribute.model';
import { IRoleType } from 'app/entities/role-type/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/service/role-type.service';

import { RoleTypeAttributeUpdateComponent } from './role-type-attribute-update.component';

describe('RoleTypeAttribute Management Update Component', () => {
  let comp: RoleTypeAttributeUpdateComponent;
  let fixture: ComponentFixture<RoleTypeAttributeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleTypeAttributeService: RoleTypeAttributeService;
  let roleTypeService: RoleTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RoleTypeAttributeUpdateComponent],
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
      .overrideTemplate(RoleTypeAttributeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleTypeAttributeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleTypeAttributeService = TestBed.inject(RoleTypeAttributeService);
    roleTypeService = TestBed.inject(RoleTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RoleType query and add missing value', () => {
      const roleTypeAttribute: IRoleTypeAttribute = { id: 456 };
      const roleTypeID: IRoleType = { id: 82498 };
      roleTypeAttribute.roleTypeID = roleTypeID;

      const roleTypeCollection: IRoleType[] = [{ id: 70155 }];
      jest.spyOn(roleTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: roleTypeCollection })));
      const additionalRoleTypes = [roleTypeID];
      const expectedCollection: IRoleType[] = [...additionalRoleTypes, ...roleTypeCollection];
      jest.spyOn(roleTypeService, 'addRoleTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ roleTypeAttribute });
      comp.ngOnInit();

      expect(roleTypeService.query).toHaveBeenCalled();
      expect(roleTypeService.addRoleTypeToCollectionIfMissing).toHaveBeenCalledWith(roleTypeCollection, ...additionalRoleTypes);
      expect(comp.roleTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const roleTypeAttribute: IRoleTypeAttribute = { id: 456 };
      const roleTypeID: IRoleType = { id: 63689 };
      roleTypeAttribute.roleTypeID = roleTypeID;

      activatedRoute.data = of({ roleTypeAttribute });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(roleTypeAttribute));
      expect(comp.roleTypesSharedCollection).toContain(roleTypeID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleTypeAttribute>>();
      const roleTypeAttribute = { id: 123 };
      jest.spyOn(roleTypeAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleTypeAttribute }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleTypeAttributeService.update).toHaveBeenCalledWith(roleTypeAttribute);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleTypeAttribute>>();
      const roleTypeAttribute = new RoleTypeAttribute();
      jest.spyOn(roleTypeAttributeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleTypeAttribute }));
      saveSubject.complete();

      // THEN
      expect(roleTypeAttributeService.create).toHaveBeenCalledWith(roleTypeAttribute);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleTypeAttribute>>();
      const roleTypeAttribute = { id: 123 };
      jest.spyOn(roleTypeAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleTypeAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleTypeAttributeService.update).toHaveBeenCalledWith(roleTypeAttribute);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRoleTypeById', () => {
      it('Should return tracked RoleType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRoleTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
