import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInspectionRecord, InspectionRecord } from '../inspection-record.model';
import { InspectionRecordService } from '../service/inspection-record.service';

import { InspectionRecordRoutingResolveService } from './inspection-record-routing-resolve.service';

describe('InspectionRecord routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InspectionRecordRoutingResolveService;
  let service: InspectionRecordService;
  let resultInspectionRecord: IInspectionRecord | undefined;

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
    routingResolveService = TestBed.inject(InspectionRecordRoutingResolveService);
    service = TestBed.inject(InspectionRecordService);
    resultInspectionRecord = undefined;
  });

  describe('resolve', () => {
    it('should return IInspectionRecord returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInspectionRecord = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInspectionRecord).toEqual({ id: 123 });
    });

    it('should return new IInspectionRecord if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInspectionRecord = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInspectionRecord).toEqual(new InspectionRecord());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InspectionRecord })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInspectionRecord = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInspectionRecord).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
