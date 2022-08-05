import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPlantFactoryController, PlantFactoryController } from '../plant-factory-controller.model';
import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';

import { PlantFactoryControllerRoutingResolveService } from './plant-factory-controller-routing-resolve.service';

describe('PlantFactoryController routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PlantFactoryControllerRoutingResolveService;
  let service: PlantFactoryControllerService;
  let resultPlantFactoryController: IPlantFactoryController | undefined;

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
    routingResolveService = TestBed.inject(PlantFactoryControllerRoutingResolveService);
    service = TestBed.inject(PlantFactoryControllerService);
    resultPlantFactoryController = undefined;
  });

  describe('resolve', () => {
    it('should return IPlantFactoryController returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactoryController = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlantFactoryController).toEqual({ id: 123 });
    });

    it('should return new IPlantFactoryController if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactoryController = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPlantFactoryController).toEqual(new PlantFactoryController());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PlantFactoryController })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPlantFactoryController = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPlantFactoryController).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
