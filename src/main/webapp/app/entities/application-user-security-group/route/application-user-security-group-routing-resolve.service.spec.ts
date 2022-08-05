import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IApplicationUserSecurityGroup, ApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';

import { ApplicationUserSecurityGroupRoutingResolveService } from './application-user-security-group-routing-resolve.service';

describe('ApplicationUserSecurityGroup routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ApplicationUserSecurityGroupRoutingResolveService;
  let service: ApplicationUserSecurityGroupService;
  let resultApplicationUserSecurityGroup: IApplicationUserSecurityGroup | undefined;

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
    routingResolveService = TestBed.inject(ApplicationUserSecurityGroupRoutingResolveService);
    service = TestBed.inject(ApplicationUserSecurityGroupService);
    resultApplicationUserSecurityGroup = undefined;
  });

  describe('resolve', () => {
    it('should return IApplicationUserSecurityGroup returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApplicationUserSecurityGroup = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultApplicationUserSecurityGroup).toEqual({ id: 123 });
    });

    it('should return new IApplicationUserSecurityGroup if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApplicationUserSecurityGroup = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultApplicationUserSecurityGroup).toEqual(new ApplicationUserSecurityGroup());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ApplicationUserSecurityGroup })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApplicationUserSecurityGroup = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultApplicationUserSecurityGroup).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
