import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPartyTypeAttribute, PartyTypeAttribute } from '../party-type-attribute.model';
import { PartyTypeAttributeService } from '../service/party-type-attribute.service';

import { PartyTypeAttributeRoutingResolveService } from './party-type-attribute-routing-resolve.service';

describe('PartyTypeAttribute routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PartyTypeAttributeRoutingResolveService;
  let service: PartyTypeAttributeService;
  let resultPartyTypeAttribute: IPartyTypeAttribute | undefined;

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
    routingResolveService = TestBed.inject(PartyTypeAttributeRoutingResolveService);
    service = TestBed.inject(PartyTypeAttributeService);
    resultPartyTypeAttribute = undefined;
  });

  describe('resolve', () => {
    it('should return IPartyTypeAttribute returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyTypeAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartyTypeAttribute).toEqual({ id: 123 });
    });

    it('should return new IPartyTypeAttribute if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyTypeAttribute = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPartyTypeAttribute).toEqual(new PartyTypeAttribute());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PartyTypeAttribute })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPartyTypeAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPartyTypeAttribute).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
