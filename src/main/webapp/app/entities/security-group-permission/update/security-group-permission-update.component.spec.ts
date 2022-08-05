import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityGroupPermissionService } from '../service/security-group-permission.service';
import { ISecurityGroupPermission, SecurityGroupPermission } from '../security-group-permission.model';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';

import { SecurityGroupPermissionUpdateComponent } from './security-group-permission-update.component';

describe('SecurityGroupPermission Management Update Component', () => {
  let comp: SecurityGroupPermissionUpdateComponent;
  let fixture: ComponentFixture<SecurityGroupPermissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityGroupPermissionService: SecurityGroupPermissionService;
  let securityGroupService: SecurityGroupService;
  let securityPermissionService: SecurityPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityGroupPermissionUpdateComponent],
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
      .overrideTemplate(SecurityGroupPermissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityGroupPermissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityGroupPermissionService = TestBed.inject(SecurityGroupPermissionService);
    securityGroupService = TestBed.inject(SecurityGroupService);
    securityPermissionService = TestBed.inject(SecurityPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityGroup query and add missing value', () => {
      const securityGroupPermission: ISecurityGroupPermission = { id: 456 };
      const securityGroupID: ISecurityGroup = { id: 31758 };
      securityGroupPermission.securityGroupID = securityGroupID;

      const securityGroupCollection: ISecurityGroup[] = [{ id: 82939 }];
      jest.spyOn(securityGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: securityGroupCollection })));
      const additionalSecurityGroups = [securityGroupID];
      const expectedCollection: ISecurityGroup[] = [...additionalSecurityGroups, ...securityGroupCollection];
      jest.spyOn(securityGroupService, 'addSecurityGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      expect(securityGroupService.query).toHaveBeenCalled();
      expect(securityGroupService.addSecurityGroupToCollectionIfMissing).toHaveBeenCalledWith(
        securityGroupCollection,
        ...additionalSecurityGroups
      );
      expect(comp.securityGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityPermission query and add missing value', () => {
      const securityGroupPermission: ISecurityGroupPermission = { id: 456 };
      const securityPermissionID: ISecurityPermission = { id: 9082 };
      securityGroupPermission.securityPermissionID = securityPermissionID;

      const securityPermissionCollection: ISecurityPermission[] = [{ id: 49334 }];
      jest.spyOn(securityPermissionService, 'query').mockReturnValue(of(new HttpResponse({ body: securityPermissionCollection })));
      const additionalSecurityPermissions = [securityPermissionID];
      const expectedCollection: ISecurityPermission[] = [...additionalSecurityPermissions, ...securityPermissionCollection];
      jest.spyOn(securityPermissionService, 'addSecurityPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      expect(securityPermissionService.query).toHaveBeenCalled();
      expect(securityPermissionService.addSecurityPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityPermissionCollection,
        ...additionalSecurityPermissions
      );
      expect(comp.securityPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityGroupPermission: ISecurityGroupPermission = { id: 456 };
      const securityGroupID: ISecurityGroup = { id: 59434 };
      securityGroupPermission.securityGroupID = securityGroupID;
      const securityPermissionID: ISecurityPermission = { id: 20861 };
      securityGroupPermission.securityPermissionID = securityPermissionID;

      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityGroupPermission));
      expect(comp.securityGroupsSharedCollection).toContain(securityGroupID);
      expect(comp.securityPermissionsSharedCollection).toContain(securityPermissionID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroupPermission>>();
      const securityGroupPermission = { id: 123 };
      jest.spyOn(securityGroupPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityGroupPermission }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityGroupPermissionService.update).toHaveBeenCalledWith(securityGroupPermission);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroupPermission>>();
      const securityGroupPermission = new SecurityGroupPermission();
      jest.spyOn(securityGroupPermissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityGroupPermission }));
      saveSubject.complete();

      // THEN
      expect(securityGroupPermissionService.create).toHaveBeenCalledWith(securityGroupPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroupPermission>>();
      const securityGroupPermission = { id: 123 };
      jest.spyOn(securityGroupPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroupPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityGroupPermissionService.update).toHaveBeenCalledWith(securityGroupPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityGroupById', () => {
      it('Should return tracked SecurityGroup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityGroupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityPermissionById', () => {
      it('Should return tracked SecurityPermission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityPermissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
