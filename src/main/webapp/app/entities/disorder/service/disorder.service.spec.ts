import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDisorder, Disorder } from '../disorder.model';

import { DisorderService } from './disorder.service';

describe('Disorder Service', () => {
  let service: DisorderService;
  let httpMock: HttpTestingController;
  let elemDefault: IDisorder;
  let expectedResult: IDisorder | IDisorder[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DisorderService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      physiologicalDisorder: 'AAAAAAA',
      cause: 'AAAAAAA',
      controlMeasure: 'AAAAAAA',
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

    it('should create a Disorder', () => {
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

      service.create(new Disorder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Disorder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          physiologicalDisorder: 'BBBBBB',
          cause: 'BBBBBB',
          controlMeasure: 'BBBBBB',
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

    it('should partial update a Disorder', () => {
      const patchObject = Object.assign(
        {
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Disorder()
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

    it('should return a list of Disorder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          physiologicalDisorder: 'BBBBBB',
          cause: 'BBBBBB',
          controlMeasure: 'BBBBBB',
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

    it('should delete a Disorder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDisorderToCollectionIfMissing', () => {
      it('should add a Disorder to an empty array', () => {
        const disorder: IDisorder = { id: 123 };
        expectedResult = service.addDisorderToCollectionIfMissing([], disorder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disorder);
      });

      it('should not add a Disorder to an array that contains it', () => {
        const disorder: IDisorder = { id: 123 };
        const disorderCollection: IDisorder[] = [
          {
            ...disorder,
          },
          { id: 456 },
        ];
        expectedResult = service.addDisorderToCollectionIfMissing(disorderCollection, disorder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Disorder to an array that doesn't contain it", () => {
        const disorder: IDisorder = { id: 123 };
        const disorderCollection: IDisorder[] = [{ id: 456 }];
        expectedResult = service.addDisorderToCollectionIfMissing(disorderCollection, disorder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disorder);
      });

      it('should add only unique Disorder to an array', () => {
        const disorderArray: IDisorder[] = [{ id: 123 }, { id: 456 }, { id: 99153 }];
        const disorderCollection: IDisorder[] = [{ id: 123 }];
        expectedResult = service.addDisorderToCollectionIfMissing(disorderCollection, ...disorderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const disorder: IDisorder = { id: 123 };
        const disorder2: IDisorder = { id: 456 };
        expectedResult = service.addDisorderToCollectionIfMissing([], disorder, disorder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disorder);
        expect(expectedResult).toContain(disorder2);
      });

      it('should accept null and undefined values', () => {
        const disorder: IDisorder = { id: 123 };
        expectedResult = service.addDisorderToCollectionIfMissing([], null, disorder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disorder);
      });

      it('should return initial array if no Disorder is added', () => {
        const disorderCollection: IDisorder[] = [{ id: 123 }];
        expectedResult = service.addDisorderToCollectionIfMissing(disorderCollection, undefined, null);
        expect(expectedResult).toEqual(disorderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
