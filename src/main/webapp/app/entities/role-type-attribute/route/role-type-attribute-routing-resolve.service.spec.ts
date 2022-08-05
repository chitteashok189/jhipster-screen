import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRoleTypeAttribute, RoleTypeAttribute } from '../role-type-attribute.model';
import { RoleTypeAttributeService } from '../service/role-type-attribute.service';

import { RoleTypeAttributeRoutingResolveService } from './role-type-attribute-routing-resolve.service';

describe('RoleTypeAttribute routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RoleTypeAttributeRoutingResolveService;
  let service: RoleTypeAttributeService;
  let resultRoleTypeAttribute: IRoleTypeAttribute | undefined;

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
    routingResolveService = TestBed.inject(RoleTypeAttributeRoutingResolveService);
    service = TestBed.inject(RoleTypeAttributeService);
    resultRoleTypeAttribute = undefined;
  });

  describe('resolve', () => {
    it('should return IRoleTypeAttribute returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRoleTypeAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRoleTypeAttribute).toEqual({ id: 123 });
    });

    it('should return new IRoleTypeAttribute if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRoleTypeAttribute = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRoleTypeAttribute).toEqual(new RoleTypeAttribute());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RoleTypeAttribute })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRoleTypeAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRoleTypeAttribute).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
