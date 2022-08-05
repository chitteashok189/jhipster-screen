import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { GrowBedType } from 'app/entities/enumerations/grow-bed-type.model';
import { IGrowBed, GrowBed } from '../grow-bed.model';

import { GrowBedService } from './grow-bed.service';

describe('GrowBed Service', () => {
  let service: GrowBedService;
  let httpMock: HttpTestingController;
  let elemDefault: IGrowBed;
  let expectedResult: IGrowBed | IGrowBed[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GrowBedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      growBedType: GrowBedType.Vertical,
      growBedName: 'AAAAAAA',
      manufacturer: 'AAAAAAA',
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

    it('should create a GrowBed', () => {
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

      service.create(new GrowBed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrowBed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          growBedType: 'BBBBBB',
          growBedName: 'BBBBBB',
          manufacturer: 'BBBBBB',
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

    it('should partial update a GrowBed', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new GrowBed()
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

    it('should return a list of GrowBed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          growBedType: 'BBBBBB',
          growBedName: 'BBBBBB',
          manufacturer: 'BBBBBB',
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

    it('should delete a GrowBed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGrowBedToCollectionIfMissing', () => {
      it('should add a GrowBed to an empty array', () => {
        const growBed: IGrowBed = { id: 123 };
        expectedResult = service.addGrowBedToCollectionIfMissing([], growBed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(growBed);
      });

      it('should not add a GrowBed to an array that contains it', () => {
        const growBed: IGrowBed = { id: 123 };
        const growBedCollection: IGrowBed[] = [
          {
            ...growBed,
          },
          { id: 456 },
        ];
        expectedResult = service.addGrowBedToCollectionIfMissing(growBedCollection, growBed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrowBed to an array that doesn't contain it", () => {
        const growBed: IGrowBed = { id: 123 };
        const growBedCollection: IGrowBed[] = [{ id: 456 }];
        expectedResult = service.addGrowBedToCollectionIfMissing(growBedCollection, growBed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(growBed);
      });

      it('should add only unique GrowBed to an array', () => {
        const growBedArray: IGrowBed[] = [{ id: 123 }, { id: 456 }, { id: 32449 }];
        const growBedCollection: IGrowBed[] = [{ id: 123 }];
        expectedResult = service.addGrowBedToCollectionIfMissing(growBedCollection, ...growBedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const growBed: IGrowBed = { id: 123 };
        const growBed2: IGrowBed = { id: 456 };
        expectedResult = service.addGrowBedToCollectionIfMissing([], growBed, growBed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(growBed);
        expect(expectedResult).toContain(growBed2);
      });

      it('should accept null and undefined values', () => {
        const growBed: IGrowBed = { id: 123 };
        expectedResult = service.addGrowBedToCollectionIfMissing([], null, growBed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(growBed);
      });

      it('should return initial array if no GrowBed is added', () => {
        const growBedCollection: IGrowBed[] = [{ id: 123 }];
        expectedResult = service.addGrowBedToCollectionIfMissing(growBedCollection, undefined, null);
        expect(expectedResult).toEqual(growBedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
