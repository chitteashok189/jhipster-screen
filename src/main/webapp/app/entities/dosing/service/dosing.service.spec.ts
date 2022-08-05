import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DosingSource } from 'app/entities/enumerations/dosing-source.model';
import { IDosing, Dosing } from '../dosing.model';

import { DosingService } from './dosing.service';

describe('Dosing Service', () => {
  let service: DosingService;
  let httpMock: HttpTestingController;
  let elemDefault: IDosing;
  let expectedResult: IDosing | IDosing[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DosingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      source: DosingSource.Automatic,
      pH: 0,
      eC: 0,
      dO: 0,
      nutrientTemperature: 0,
      solarRadiation: 0,
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

    it('should create a Dosing', () => {
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

      service.create(new Dosing()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dosing', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          pH: 1,
          eC: 1,
          dO: 1,
          nutrientTemperature: 1,
          solarRadiation: 1,
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

    it('should partial update a Dosing', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          pH: 1,
          eC: 1,
          nutrientTemperature: 1,
          solarRadiation: 1,
        },
        new Dosing()
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

    it('should return a list of Dosing', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          pH: 1,
          eC: 1,
          dO: 1,
          nutrientTemperature: 1,
          solarRadiation: 1,
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

    it('should delete a Dosing', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDosingToCollectionIfMissing', () => {
      it('should add a Dosing to an empty array', () => {
        const dosing: IDosing = { id: 123 };
        expectedResult = service.addDosingToCollectionIfMissing([], dosing);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dosing);
      });

      it('should not add a Dosing to an array that contains it', () => {
        const dosing: IDosing = { id: 123 };
        const dosingCollection: IDosing[] = [
          {
            ...dosing,
          },
          { id: 456 },
        ];
        expectedResult = service.addDosingToCollectionIfMissing(dosingCollection, dosing);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dosing to an array that doesn't contain it", () => {
        const dosing: IDosing = { id: 123 };
        const dosingCollection: IDosing[] = [{ id: 456 }];
        expectedResult = service.addDosingToCollectionIfMissing(dosingCollection, dosing);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dosing);
      });

      it('should add only unique Dosing to an array', () => {
        const dosingArray: IDosing[] = [{ id: 123 }, { id: 456 }, { id: 67677 }];
        const dosingCollection: IDosing[] = [{ id: 123 }];
        expectedResult = service.addDosingToCollectionIfMissing(dosingCollection, ...dosingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dosing: IDosing = { id: 123 };
        const dosing2: IDosing = { id: 456 };
        expectedResult = service.addDosingToCollectionIfMissing([], dosing, dosing2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dosing);
        expect(expectedResult).toContain(dosing2);
      });

      it('should accept null and undefined values', () => {
        const dosing: IDosing = { id: 123 };
        expectedResult = service.addDosingToCollectionIfMissing([], null, dosing, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dosing);
      });

      it('should return initial array if no Dosing is added', () => {
        const dosingCollection: IDosing[] = [{ id: 123 }];
        expectedResult = service.addDosingToCollectionIfMissing(dosingCollection, undefined, null);
        expect(expectedResult).toEqual(dosingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
