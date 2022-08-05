import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ThreatLevel } from 'app/entities/enumerations/threat-level.model';
import { IPest, Pest } from '../pest.model';

import { PestService } from './pest.service';

describe('Pest Service', () => {
  let service: PestService;
  let httpMock: HttpTestingController;
  let elemDefault: IPest;
  let expectedResult: IPest | IPest[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PestService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      threatLevel: ThreatLevel.High,
      name: 'AAAAAAA',
      scientificName: 'AAAAAAA',
      attachements: 0,
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

    it('should create a Pest', () => {
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

      service.create(new Pest()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pest', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          threatLevel: 'BBBBBB',
          name: 'BBBBBB',
          scientificName: 'BBBBBB',
          attachements: 1,
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

    it('should partial update a Pest', () => {
      const patchObject = Object.assign(
        {
          threatLevel: 'BBBBBB',
          name: 'BBBBBB',
          attachements: 1,
          createdBy: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Pest()
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

    it('should return a list of Pest', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          threatLevel: 'BBBBBB',
          name: 'BBBBBB',
          scientificName: 'BBBBBB',
          attachements: 1,
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

    it('should delete a Pest', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPestToCollectionIfMissing', () => {
      it('should add a Pest to an empty array', () => {
        const pest: IPest = { id: 123 };
        expectedResult = service.addPestToCollectionIfMissing([], pest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pest);
      });

      it('should not add a Pest to an array that contains it', () => {
        const pest: IPest = { id: 123 };
        const pestCollection: IPest[] = [
          {
            ...pest,
          },
          { id: 456 },
        ];
        expectedResult = service.addPestToCollectionIfMissing(pestCollection, pest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pest to an array that doesn't contain it", () => {
        const pest: IPest = { id: 123 };
        const pestCollection: IPest[] = [{ id: 456 }];
        expectedResult = service.addPestToCollectionIfMissing(pestCollection, pest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pest);
      });

      it('should add only unique Pest to an array', () => {
        const pestArray: IPest[] = [{ id: 123 }, { id: 456 }, { id: 12687 }];
        const pestCollection: IPest[] = [{ id: 123 }];
        expectedResult = service.addPestToCollectionIfMissing(pestCollection, ...pestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pest: IPest = { id: 123 };
        const pest2: IPest = { id: 456 };
        expectedResult = service.addPestToCollectionIfMissing([], pest, pest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pest);
        expect(expectedResult).toContain(pest2);
      });

      it('should accept null and undefined values', () => {
        const pest: IPest = { id: 123 };
        expectedResult = service.addPestToCollectionIfMissing([], null, pest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pest);
      });

      it('should return initial array if no Pest is added', () => {
        const pestCollection: IPest[] = [{ id: 123 }];
        expectedResult = service.addPestToCollectionIfMissing(pestCollection, undefined, null);
        expect(expectedResult).toEqual(pestCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
