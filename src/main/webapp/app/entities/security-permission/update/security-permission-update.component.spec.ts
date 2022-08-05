import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityPermissionService } from '../service/security-permission.service';
import { ISecurityPermission, SecurityPermission } from '../security-permission.model';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';
import { SecurityGroupPermissionService } from 'app/entities/security-group-permission/service/security-group-permission.service';

import { SecurityPermissionUpdateComponent } from './security-permission-update.component';

describe('SecurityPermission Management Update Component', () => {
  let comp: SecurityPermissionUpdateComponent;
  let fixture: ComponentFixture<SecurityPermissionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityPermissionService: SecurityPermissionService;
  let securityGroupPermissionService: SecurityGroupPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityPermissionUpdateComponent],
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
      .overrideTemplate(SecurityPermissionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityPermissionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityPermissionService = TestBed.inject(SecurityPermissionService);
    securityGroupPermissionService = TestBed.inject(SecurityGroupPermissionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityGroupPermission query and add missing value', () => {
      const securityPermission: ISecurityPermission = { id: 456 };
      const securityGroupPermissionID: ISecurityGroupPermission = { id: 95848 };
      securityPermission.securityGroupPermissionID = securityGroupPermissionID;

      const securityGroupPermissionCollection: ISecurityGroupPermission[] = [{ id: 28204 }];
      jest
        .spyOn(securityGroupPermissionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: securityGroupPermissionCollection })));
      const additionalSecurityGroupPermissions = [securityGroupPermissionID];
      const expectedCollection: ISecurityGroupPermission[] = [...additionalSecurityGroupPermissions, ...securityGroupPermissionCollection];
      jest.spyOn(securityGroupPermissionService, 'addSecurityGroupPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      expect(securityGroupPermissionService.query).toHaveBeenCalled();
      expect(securityGroupPermissionService.addSecurityGroupPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityGroupPermissionCollection,
        ...additionalSecurityGroupPermissions
      );
      expect(comp.securityGroupPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityPermission: ISecurityPermission = { id: 456 };
      const securityGroupPermissionID: ISecurityGroupPermission = { id: 62016 };
      securityPermission.securityGroupPermissionID = securityGroupPermissionID;

      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(securityPermission));
      expect(comp.securityGroupPermissionsSharedCollection).toContain(securityGroupPermissionID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = { id: 123 };
      jest.spyOn(securityPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityPermission }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityPermissionService.update).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = new SecurityPermission();
      jest.spyOn(securityPermissionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityPermission }));
      saveSubject.complete();

      // THEN
      expect(securityPermissionService.create).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SecurityPermission>>();
      const securityPermission = { id: 123 };
      jest.spyOn(securityPermissionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityPermission });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityPermissionService.update).toHaveBeenCalledWith(securityPermission);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSecurityGroupPermissionById', () => {
      it('Should return tracked SecurityGroupPermission primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityGroupPermissionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
