import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FarmService } from '../service/farm.service';
import { IFarm, Farm } from '../farm.model';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

import { FarmUpdateComponent } from './farm-update.component';

describe('Farm Management Update Component', () => {
  let comp: FarmUpdateComponent;
  let fixture: ComponentFixture<FarmUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let farmService: FarmService;
  let partyService: PartyService;
  let locationService: LocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FarmUpdateComponent],
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
      .overrideTemplate(FarmUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FarmUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    farmService = TestBed.inject(FarmService);
    partyService = TestBed.inject(PartyService);
    locationService = TestBed.inject(LocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Party query and add missing value', () => {
      const farm: IFarm = { id: 456 };
      const partyID: IParty = { id: 50546 };
      farm.partyID = partyID;

      const partyCollection: IParty[] = [{ id: 74274 }];
      jest.spyOn(partyService, 'query').mockReturnValue(of(new HttpResponse({ body: partyCollection })));
      const additionalParties = [partyID];
      const expectedCollection: IParty[] = [...additionalParties, ...partyCollection];
      jest.spyOn(partyService, 'addPartyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      expect(partyService.query).toHaveBeenCalled();
      expect(partyService.addPartyToCollectionIfMissing).toHaveBeenCalledWith(partyCollection, ...additionalParties);
      expect(comp.partiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Location query and add missing value', () => {
      const farm: IFarm = { id: 456 };
      const locationID: ILocation = { id: 95238 };
      farm.locationID = locationID;

      const locationCollection: ILocation[] = [{ id: 30774 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const additionalLocations = [locationID];
      const expectedCollection: ILocation[] = [...additionalLocations, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, ...additionalLocations);
      expect(comp.locationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const farm: IFarm = { id: 456 };
      const partyID: IParty = { id: 73923 };
      farm.partyID = partyID;
      const locationID: ILocation = { id: 23915 };
      farm.locationID = locationID;

      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(farm));
      expect(comp.partiesSharedCollection).toContain(partyID);
      expect(comp.locationsSharedCollection).toContain(locationID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Farm>>();
      const farm = { id: 123 };
      jest.spyOn(farmService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farm }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(farmService.update).toHaveBeenCalledWith(farm);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Farm>>();
      const farm = new Farm();
      jest.spyOn(farmService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farm }));
      saveSubject.complete();

      // THEN
      expect(farmService.create).toHaveBeenCalledWith(farm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Farm>>();
      const farm = { id: 123 };
      jest.spyOn(farmService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(farmService.update).toHaveBeenCalledWith(farm);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPartyById', () => {
      it('Should return tracked Party primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPartyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLocationById', () => {
      it('Should return tracked Location primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLocationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
