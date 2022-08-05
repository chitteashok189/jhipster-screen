import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoleTypeService } from '../service/role-type.service';
import { IRoleType, RoleType } from '../role-type.model';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { PartyRoleService } from 'app/entities/party-role/service/party-role.service';
import { IRoleTypeAttribute } from 'app/entities/role-type-attribute/role-type-attribute.model';
import { RoleTypeAttributeService } from 'app/entities/role-type-attribute/service/role-type-attribute.service';

import { RoleTypeUpdateComponent } from './role-type-update.component';

describe('RoleType Management Update Component', () => {
  let comp: RoleTypeUpdateComponent;
  let fixture: ComponentFixture<RoleTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roleTypeService: RoleTypeService;
  let partyRoleService: PartyRoleService;
  let roleTypeAttributeService: RoleTypeAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RoleTypeUpdateComponent],
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
      .overrideTemplate(RoleTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoleTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roleTypeService = TestBed.inject(RoleTypeService);
    partyRoleService = TestBed.inject(PartyRoleService);
    roleTypeAttributeService = TestBed.inject(RoleTypeAttributeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PartyRole query and add missing value', () => {
      const roleType: IRoleType = { id: 456 };
      const partyRoleID: IPartyRole = { id: 33531 };
      roleType.partyRoleID = partyRoleID;

      const partyRoleCollection: IPartyRole[] = [{ id: 45285 }];
      jest.spyOn(partyRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: partyRoleCollection })));
      const additionalPartyRoles = [partyRoleID];
      const expectedCollection: IPartyRole[] = [...additionalPartyRoles, ...partyRoleCollection];
      jest.spyOn(partyRoleService, 'addPartyRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      expect(partyRoleService.query).toHaveBeenCalled();
      expect(partyRoleService.addPartyRoleToCollectionIfMissing).toHaveBeenCalledWith(partyRoleCollection, ...additionalPartyRoles);
      expect(comp.partyRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RoleTypeAttribute query and add missing value', () => {
      const roleType: IRoleType = { id: 456 };
      const roleTypeAttributeID: IRoleTypeAttribute = { id: 68015 };
      roleType.roleTypeAttributeID = roleTypeAttributeID;

      const roleTypeAttributeCollection: IRoleTypeAttribute[] = [{ id: 41895 }];
      jest.spyOn(roleTypeAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: roleTypeAttributeCollection })));
      const additionalRoleTypeAttributes = [roleTypeAttributeID];
      const expectedCollection: IRoleTypeAttribute[] = [...additionalRoleTypeAttributes, ...roleTypeAttributeCollection];
      jest.spyOn(roleTypeAttributeService, 'addRoleTypeAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      expect(roleTypeAttributeService.query).toHaveBeenCalled();
      expect(roleTypeAttributeService.addRoleTypeAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        roleTypeAttributeCollection,
        ...additionalRoleTypeAttributes
      );
      expect(comp.roleTypeAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const roleType: IRoleType = { id: 456 };
      const partyRoleID: IPartyRole = { id: 23111 };
      roleType.partyRoleID = partyRoleID;
      const roleTypeAttributeID: IRoleTypeAttribute = { id: 90247 };
      roleType.roleTypeAttributeID = roleTypeAttributeID;

      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(roleType));
      expect(comp.partyRolesSharedCollection).toContain(partyRoleID);
      expect(comp.roleTypeAttributesSharedCollection).toContain(roleTypeAttributeID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleType>>();
      const roleType = { id: 123 };
      jest.spyOn(roleTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(roleTypeService.update).toHaveBeenCalledWith(roleType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleType>>();
      const roleType = new RoleType();
      jest.spyOn(roleTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: roleType }));
      saveSubject.complete();

      // THEN
      expect(roleTypeService.create).toHaveBeenCalledWith(roleType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RoleType>>();
      const roleType = { id: 123 };
      jest.spyOn(roleTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ roleType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roleTypeService.update).toHaveBeenCalledWith(roleType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyRoleById', () => {
      it('Should return tracked PartyRole primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyRoleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackRoleTypeAttributeById', () => {
      it('Should return tracked RoleTypeAttribute primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRoleTypeAttributeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
