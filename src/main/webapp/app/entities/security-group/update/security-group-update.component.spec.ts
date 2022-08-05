import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityGroupService } from '../service/security-group.service';
import { ISecurityGroup, SecurityGroup } from '../security-group.model';
import { IApplicationUserSecurityGroup } from 'app/entities/application-user-security-group/application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from 'app/entities/application-user-security-group/service/application-user-security-group.service';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';
import { SecurityGroupPermissionService } from 'app/entities/security-group-permission/service/security-group-permission.service';

import { SecurityGroupUpdateComponent } from './security-group-update.component';

describe('SecurityGroup Management Update Component', () => {
  let comp: SecurityGroupUpdateComponent;
  let fixture: ComponentFixture<SecurityGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityGroupService: SecurityGroupService;
  let applicationUserSecurityGroupService: ApplicationUserSecurityGroupService;
  let securityGroupPermissionService: SecurityGroupPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityGroupUpdateComponent],
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
      .overrideTemplate(SecurityGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityGroupService = TestBed.inject(SecurityGroupService);
    applicationUserSecurityGroupService = TestBed.inject(ApplicationUserSecurityGroupService);
    securityGroupPermissionService = TestBed.inject(SecurityGroupPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ApplicationUserSecurityGroup query and add missing value', () => {
      const securityGroup: ISecurityGroup = { id: 456 };
      const applicationUserSecurityGroupID: IApplicationUserSecurityGroup = { id: 15384 };
      securityGroup.applicationUserSecurityGroupID = applicationUserSecurityGroupID;

      const applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[] = [{ id: 83216 }];
      jest
        .spyOn(applicationUserSecurityGroupService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: applicationUserSecurityGroupCollection })));
      const additionalApplicationUserSecurityGroups = [applicationUserSecurityGroupID];
      const expectedCollection: IApplicationUserSecurityGroup[] = [
        ...additionalApplicationUserSecurityGroups,
        ...applicationUserSecurityGroupCollection,
      ];
      jest
        .spyOn(applicationUserSecurityGroupService, 'addApplicationUserSecurityGroupToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      expect(applicationUserSecurityGroupService.query).toHaveBeenCalled();
      expect(applicationUserSecurityGroupService.addApplicationUserSecurityGroupToCollectionIfMissing).toHaveBeenCalledWith(
        applicationUserSecurityGroupCollection,
        ...additionalApplicationUserSecurityGroups
      );
      expect(comp.applicationUserSecurityGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityGroupPermission query and add missing value', () => {
      const securityGroup: ISecurityGroup = { id: 456 };
      const securityGroupPermissionID: ISecurityGroupPermission = { id: 93899 };
      securityGroup.securityGroupPermissionID = securityGroupPermissionID;

      const securityGroupPermissionCollection: ISecurityGroupPermission[] = [{ id: 77922 }];
      jest
        .spyOn(securityGroupPermissionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: securityGroupPermissionCollection })));
      const additionalSecurityGroupPermissions = [securityGroupPermissionID];
      const expectedCollection: ISecurityGroupPermission[] = [...additionalSecurityGroupPermissions, ...securityGroupPermissionCollection];
      jest.spyOn(securityGroupPermissionService, 'addSecurityGroupPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      expect(securityGroupPermissionService.query).toHaveBeenCalled();
      expect(securityGroupPermissionService.addSecurityGroupPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityGroupPermissionCollection,
        ...additionalSecurityGroupPermissions
      );
      expect(comp.securityGroupPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityGroup: ISecurityGroup = { id: 456 };
      const applicationUserSecurityGroupID: IApplicationUserSecurityGroup = { id: 90026 };
      securityGroup.applicationUserSecurityGroupID = applicationUserSecurityGroupID;
      const securityGroupPermissionID: ISecurityGroupPermission = { id: 71982 };
      securityGroup.securityGroupPermissionID = securityGroupPermissionID;

      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityGroup));
      expect(comp.applicationUserSecurityGroupsSharedCollection).toContain(applicationUserSecurityGroupID);
      expect(comp.securityGroupPermissionsSharedCollection).toContain(securityGroupPermissionID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroup>>();
      const securityGroup = { id: 123 };
      jest.spyOn(securityGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityGroup }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityGroupService.update).toHaveBeenCalledWith(securityGroup);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroup>>();
      const securityGroup = new SecurityGroup();
      jest.spyOn(securityGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityGroup }));
      saveSubject.complete();

      // THEN
      expect(securityGroupService.create).toHaveBeenCalledWith(securityGroup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityGroup>>();
      const securityGroup = { id: 123 };
      jest.spyOn(securityGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityGroupService.update).toHaveBeenCalledWith(securityGroup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackApplicationUserSecurityGroupById', () => {
      it('Should return tracked ApplicationUserSecurityGroup primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackApplicationUserSecurityGroupById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityGroupPermissionById', () => {
      it('Should return tracked SecurityGroupPermission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityGroupPermissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
