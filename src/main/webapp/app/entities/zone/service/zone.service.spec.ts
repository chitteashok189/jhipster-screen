import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ZoneType } from 'app/entities/enumerations/zone-type.model';
import { IZone, Zone } from '../zone.model';

import { ZoneService } from './zone.service';

describe('Zone Service', () => {
  let service: ZoneService;
  let httpMock: HttpTestingController;
  let elemDefault: IZone;
  let expectedResult: IZone | IZone[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ZoneService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      zoneType: ZoneType.Leafy,
      zoneName: 'AAAAAAA',
      createdBy: 0,
      createdOn: currentDate,
      updatedBy: 0,
      updatedOn: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Zone', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Zone()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Zone', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          zoneType: 'BBBBBB',
          zoneName: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Zone', () => {
      const patchObject = Object.assign(
        {
          updatedBy: 1,
        },
        new Zone()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Zone', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          zoneType: 'BBBBBB',
          zoneName: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Zone', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addZoneToCollectionIfMissing', () => {
      it('should add a Zone to an empty array', () => {
        const zone: IZone = { id: 123 };
        expectedResult = service.addZoneToCollectionIfMissing([], zone);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(zone);
      });

      it('should not add a Zone to an array that contains it', () => {
        const zone: IZone = { id: 123 };
        const zoneCollection: IZone[] = [
          {
            ...zone,
          },
          { id: 456 },
        ];
        expectedResult = service.addZoneToCollectionIfMissing(zoneCollection, zone);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Zone to an array that doesn't contain it", () => {
        const zone: IZone = { id: 123 };
        const zoneCollection: IZone[] = [{ id: 456 }];
        expectedResult = service.addZoneToCollectionIfMissing(zoneCollection, zone);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(zone);
      });

      it('should add only unique Zone to an array', () => {
        const zoneArray: IZone[] = [{ id: 123 }, { id: 456 }, { id: 46836 }];
        const zoneCollection: IZone[] = [{ id: 123 }];
        expectedResult = service.addZoneToCollectionIfMissing(zoneCollection, ...zoneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const zone: IZone = { id: 123 };
        const zone2: IZone = { id: 456 };
        expectedResult = service.addZoneToCollectionIfMissing([], zone, zone2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(zone);
        expect(expectedResult).toContain(zone2);
      });

      it('should accept null and undefined values', () => {
        const zone: IZone = { id: 123 };
        expectedResult = service.addZoneToCollectionIfMissing([], null, zone, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(zone);
      });

      it('should return initial array if no Zone is added', () => {
        const zoneCollection: IZone[] = [{ id: 123 }];
        expectedResult = service.addZoneToCollectionIfMissing(zoneCollection, undefined, null);
        expect(expectedResult).toEqual(zoneCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
