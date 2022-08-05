import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISecurityGroupPermission, SecurityGroupPermission } from '../security-group-permission.model';
import { SecurityGroupPermissionService } from '../service/security-group-permission.service';

import { SecurityGroupPermissionRoutingResolveService } from './security-group-permission-routing-resolve.service';

describe('SecurityGroupPermission routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SecurityGroupPermissionRoutingResolveService;
  let service: SecurityGroupPermissionService;
  let resultSecurityGroupPermission: ISecurityGroupPermission | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SecurityGroupPermissionRoutingResolveService);
    service = TestBed.inject(SecurityGroupPermissionService);
    resultSecurityGroupPermission = undefined;
  });

  describe('resolve', () => {
    it('should return ISecurityGroupPermission returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityGroupPermission = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSecurityGroupPermission).toEqual({ id: 123 });
    });

    it('should return new ISecurityGroupPermission if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityGroupPermission = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSecurityGroupPermission).toEqual(new SecurityGroupPermission());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SecurityGroupPermission })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSecurityGroupPermission = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSecurityGroupPermission).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
