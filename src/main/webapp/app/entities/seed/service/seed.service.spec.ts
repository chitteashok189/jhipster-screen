import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SeedClass } from 'app/entities/enumerations/seed-class.model';
import { SeedRate } from 'app/entities/enumerations/seed-rate.model';
import { Treatment } from 'app/entities/enumerations/treatment.model';
import { ISeed, Seed } from '../seed.model';

import { SeedService } from './seed.service';

describe('Seed Service', () => {
  let service: SeedService;
  let httpMock: HttpTestingController;
  let elemDefault: ISeed;
  let expectedResult: ISeed | ISeed[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SeedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      breederID: 'AAAAAAA',
      seedClass: SeedClass.Certified,
      variety: 'AAAAAAA',
      seedRate: SeedRate.Seeds_Ha,
      germinationPercentage: 0,
      treatment: Treatment.Dry,
      origin: 'AAAAAAA',
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

    it('should create a Seed', () => {
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

      service.create(new Seed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Seed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          breederID: 'BBBBBB',
          seedClass: 'BBBBBB',
          variety: 'BBBBBB',
          seedRate: 'BBBBBB',
          germinationPercentage: 1,
          treatment: 'BBBBBB',
          origin: 'BBBBBB',
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

    it('should partial update a Seed', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          breederID: 'BBBBBB',
          germinationPercentage: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Seed()
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

    it('should return a list of Seed', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          breederID: 'BBBBBB',
          seedClass: 'BBBBBB',
          variety: 'BBBBBB',
          seedRate: 'BBBBBB',
          germinationPercentage: 1,
          treatment: 'BBBBBB',
          origin: 'BBBBBB',
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

    it('should delete a Seed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSeedToCollectionIfMissing', () => {
      it('should add a Seed to an empty array', () => {
        const seed: ISeed = { id: 123 };
        expectedResult = service.addSeedToCollectionIfMissing([], seed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seed);
      });

      it('should not add a Seed to an array that contains it', () => {
        const seed: ISeed = { id: 123 };
        const seedCollection: ISeed[] = [
          {
            ...seed,
          },
          { id: 456 },
        ];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, seed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Seed to an array that doesn't contain it", () => {
        const seed: ISeed = { id: 123 };
        const seedCollection: ISeed[] = [{ id: 456 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, seed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seed);
      });

      it('should add only unique Seed to an array', () => {
        const seedArray: ISeed[] = [{ id: 123 }, { id: 456 }, { id: 41921 }];
        const seedCollection: ISeed[] = [{ id: 123 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, ...seedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const seed: ISeed = { id: 123 };
        const seed2: ISeed = { id: 456 };
        expectedResult = service.addSeedToCollectionIfMissing([], seed, seed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seed);
        expect(expectedResult).toContain(seed2);
      });

      it('should accept null and undefined values', () => {
        const seed: ISeed = { id: 123 };
        expectedResult = service.addSeedToCollectionIfMissing([], null, seed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seed);
      });

      it('should return initial array if no Seed is added', () => {
        const seedCollection: ISeed[] = [{ id: 123 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, undefined, null);
        expect(expectedResult).toEqual(seedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
