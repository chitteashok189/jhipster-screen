import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDeviceLevel, DeviceLevel } from '../device-level.model';
import { DeviceLevelService } from '../service/device-level.service';

import { DeviceLevelRoutingResolveService } from './device-level-routing-resolve.service';

describe('DeviceLevel routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DeviceLevelRoutingResolveService;
  let service: DeviceLevelService;
  let resultDeviceLevel: IDeviceLevel | undefined;

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
    routingResolveService = TestBed.inject(DeviceLevelRoutingResolveService);
    service = TestBed.inject(DeviceLevelService);
    resultDeviceLevel = undefined;
  });

  describe('resolve', () => {
    it('should return IDeviceLevel returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDeviceLevel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDeviceLevel).toEqual({ id: 123 });
    });

    it('should return new IDeviceLevel if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDeviceLevel = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDeviceLevel).toEqual(new DeviceLevel());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DeviceLevel })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDeviceLevel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDeviceLevel).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
