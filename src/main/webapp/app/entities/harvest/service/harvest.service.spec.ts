import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHarvest, Harvest } from '../harvest.model';

import { HarvestService } from './harvest.service';

describe('Harvest Service', () => {
  let service: HarvestService;
  let httpMock: HttpTestingController;
  let elemDefault: IHarvest;
  let expectedResult: IHarvest | IHarvest[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HarvestService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      harvestingDate: currentDate,
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
          harvestingDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a Harvest', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          harvestingDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          harvestingDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Harvest()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Harvest', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          harvestingDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          harvestingDate: currentDate,
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

    it('should partial update a Harvest', () => {
      const patchObject = Object.assign(
        {
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Harvest()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          harvestingDate: currentDate,
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

    it('should return a list of Harvest', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          harvestingDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          harvestingDate: currentDate,
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

    it('should delete a Harvest', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHarvestToCollectionIfMissing', () => {
      it('should add a Harvest to an empty array', () => {
        const harvest: IHarvest = { id: 123 };
        expectedResult = service.addHarvestToCollectionIfMissing([], harvest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(harvest);
      });

      it('should not add a Harvest to an array that contains it', () => {
        const harvest: IHarvest = { id: 123 };
        const harvestCollection: IHarvest[] = [
          {
            ...harvest,
          },
          { id: 456 },
        ];
        expectedResult = service.addHarvestToCollectionIfMissing(harvestCollection, harvest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Harvest to an array that doesn't contain it", () => {
        const harvest: IHarvest = { id: 123 };
        const harvestCollection: IHarvest[] = [{ id: 456 }];
        expectedResult = service.addHarvestToCollectionIfMissing(harvestCollection, harvest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(harvest);
      });

      it('should add only unique Harvest to an array', () => {
        const harvestArray: IHarvest[] = [{ id: 123 }, { id: 456 }, { id: 2263 }];
        const harvestCollection: IHarvest[] = [{ id: 123 }];
        expectedResult = service.addHarvestToCollectionIfMissing(harvestCollection, ...harvestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const harvest: IHarvest = { id: 123 };
        const harvest2: IHarvest = { id: 456 };
        expectedResult = service.addHarvestToCollectionIfMissing([], harvest, harvest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(harvest);
        expect(expectedResult).toContain(harvest2);
      });

      it('should accept null and undefined values', () => {
        const harvest: IHarvest = { id: 123 };
        expectedResult = service.addHarvestToCollectionIfMissing([], null, harvest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(harvest);
      });

      it('should return initial array if no Harvest is added', () => {
        const harvestCollection: IHarvest[] = [{ id: 123 }];
        expectedResult = service.addHarvestToCollectionIfMissing(harvestCollection, undefined, null);
        expect(expectedResult).toEqual(harvestCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
