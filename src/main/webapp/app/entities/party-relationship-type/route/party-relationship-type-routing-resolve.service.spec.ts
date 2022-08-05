import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPartyRelationshipType, PartyRelationshipType } from '../party-relationship-type.model';
import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';

import { PartyRelationshipTypeRoutingResolveService } from './party-relationship-type-routing-resolve.service';

describe('PartyRelationshipType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PartyRelationshipTypeRoutingResolveService;
  let service: PartyRelationshipTypeService;
  let resultPartyRelationshipType: IPartyRelationshipType | undefined;

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
    routingResolveService = TestBed.inject(PartyRelationshipTypeRoutingResolveService);
    service = TestBed.inject(PartyRelationshipTypeService);
    resultPartyRelationshipType = undefined;
  });

  describe('resolve', () => {
    it('should return IPartyRelationshipType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyRelationshipType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartyRelationshipType).toEqual({ id: 123 });
    });

    it('should return new IPartyRelationshipType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyRelationshipType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPartyRelationshipType).toEqual(new PartyRelationshipType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PartyRelationshipType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyRelationshipType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartyRelationshipType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
