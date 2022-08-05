import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBreeder, Breeder } from '../breeder.model';

import { BreederService } from './breeder.service';

describe('Breeder Service', () => {
  let service: BreederService;
  let httpMock: HttpTestingController;
  let elemDefault: IBreeder;
  let expectedResult: IBreeder | IBreeder[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BreederService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      breederName: 'AAAAAAA',
      breederType: 0,
      breederStatus: 0,
      breederDescription: 'AAAAAAA',
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

    it('should create a Breeder', () => {
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

      service.create(new Breeder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Breeder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          breederName: 'BBBBBB',
          breederType: 1,
          breederStatus: 1,
          breederDescription: 'BBBBBB',
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

    it('should partial update a Breeder', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          breederType: 1,
          createdBy: 1,
        },
        new Breeder()
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

    it('should return a list of Breeder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          breederName: 'BBBBBB',
          breederType: 1,
          breederStatus: 1,
          breederDescription: 'BBBBBB',
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

    it('should delete a Breeder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBreederToCollectionIfMissing', () => {
      it('should add a Breeder to an empty array', () => {
        const breeder: IBreeder = { id: 123 };
        expectedResult = service.addBreederToCollectionIfMissing([], breeder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(breeder);
      });

      it('should not add a Breeder to an array that contains it', () => {
        const breeder: IBreeder = { id: 123 };
        const breederCollection: IBreeder[] = [
          {
            ...breeder,
          },
          { id: 456 },
        ];
        expectedResult = service.addBreederToCollectionIfMissing(breederCollection, breeder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Breeder to an array that doesn't contain it", () => {
        const breeder: IBreeder = { id: 123 };
        const breederCollection: IBreeder[] = [{ id: 456 }];
        expectedResult = service.addBreederToCollectionIfMissing(breederCollection, breeder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(breeder);
      });

      it('should add only unique Breeder to an array', () => {
        const breederArray: IBreeder[] = [{ id: 123 }, { id: 456 }, { id: 64659 }];
        const breederCollection: IBreeder[] = [{ id: 123 }];
        expectedResult = service.addBreederToCollectionIfMissing(breederCollection, ...breederArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const breeder: IBreeder = { id: 123 };
        const breeder2: IBreeder = { id: 456 };
        expectedResult = service.addBreederToCollectionIfMissing([], breeder, breeder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(breeder);
        expect(expectedResult).toContain(breeder2);
      });

      it('should accept null and undefined values', () => {
        const breeder: IBreeder = { id: 123 };
        expectedResult = service.addBreederToCollectionIfMissing([], null, breeder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(breeder);
      });

      it('should return initial array if no Breeder is added', () => {
        const breederCollection: IBreeder[] = [{ id: 123 }];
        expectedResult = service.addBreederToCollectionIfMissing(breederCollection, undefined, null);
        expect(expectedResult).toEqual(breederCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
