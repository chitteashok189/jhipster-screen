import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPlantFactory, PlantFactory } from '../plant-factory.model';
import { PlantFactoryService } from '../service/plant-factory.service';

import { PlantFactoryRoutingResolveService } from './plant-factory-routing-resolve.service';

describe('PlantFactory routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PlantFactoryRoutingResolveService;
  let service: PlantFactoryService;
  let resultPlantFactory: IPlantFactory | undefined;

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
    routingResolveService = TestBed.inject(PlantFactoryRoutingResolveService);
    service = TestBed.inject(PlantFactoryService);
    resultPlantFactory = undefined;
  });

  describe('resolve', () => {
    it('should return IPlantFactory returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlantFactory).toEqual({ id: 123 });
    });

    it('should return new IPlantFactory if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactory = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPlantFactory).toEqual(new PlantFactory());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PlantFactory })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactory = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlantFactory).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
