import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';
import { IApplicationUserSecurityGroup, ApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';

import { ApplicationUserSecurityGroupUpdateComponent } from './application-user-security-group-update.component';

describe('ApplicationUserSecurityGroup Management Update Component', () => {
  let comp: ApplicationUserSecurityGroupUpdateComponent;
  let fixture: ComponentFixture<ApplicationUserSecurityGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let applicationUserSecurityGroupService: ApplicationUserSecurityGroupService;
  let securityGroupService: SecurityGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApplicationUserSecurityGroupUpdateComponent],
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
      .overrideTemplate(ApplicationUserSecurityGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationUserSecurityGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    applicationUserSecurityGroupService = TestBed.inject(ApplicationUserSecurityGroupService);
    securityGroupService = TestBed.inject(SecurityGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityGroup query and add missing value', () => {
      const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 456 };
      const securityGroupID: ISecurityGroup = { id: 39028 };
      applicationUserSecurityGroup.securityGroupID = securityGroupID;

      const securityGroupCollection: ISecurityGroup[] = [{ id: 19773 }];
      jest.spyOn(securityGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: securityGroupCollection })));
      const additionalSecurityGroups = [securityGroupID];
      const expectedCollection: ISecurityGroup[] = [...additionalSecurityGroups, ...securityGroupCollection];
      jest.spyOn(securityGroupService, 'addSecurityGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ applicationUserSecurityGroup });
      comp.ngOnInit();

      expect(securityGroupService.query).toHaveBeenCalled();
      expect(securityGroupService.addSecurityGroupToCollectionIfMissing).toHaveBeenCalledWith(
        securityGroupCollection,
        ...additionalSecurityGroups
      );
      expect(comp.securityGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 456 };
      const securityGroupID: ISecurityGroup = { id: 30719 };
      applicationUserSecurityGroup.securityGroupID = securityGroupID;

      activatedRoute.data = of({ applicationUserSecurityGroup });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(applicationUserSecurityGroup));
      expect(comp.securityGroupsSharedCollection).toContain(securityGroupID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApplicationUserSecurityGroup>>();
      const applicationUserSecurityGroup = { id: 123 };
      jest.spyOn(applicationUserSecurityGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationUserSecurityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationUserSecurityGroup }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(applicationUserSecurityGroupService.update).toHaveBeenCalledWith(applicationUserSecurityGroup);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApplicationUserSecurityGroup>>();
      const applicationUserSecurityGroup = new ApplicationUserSecurityGroup();
      jest.spyOn(applicationUserSecurityGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationUserSecurityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationUserSecurityGroup }));
      saveSubject.complete();

      // THEN
      expect(applicationUserSecurityGroupService.create).toHaveBeenCalledWith(applicationUserSecurityGroup);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ApplicationUserSecurityGroup>>();
      const applicationUserSecurityGroup = { id: 123 };
      jest.spyOn(applicationUserSecurityGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationUserSecurityGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(applicationUserSecurityGroupService.update).toHaveBeenCalledWith(applicationUserSecurityGroup);
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
  });
});
