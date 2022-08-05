import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ScoutingType } from 'app/entities/enumerations/scouting-type.model';
import { CropState } from 'app/entities/enumerations/crop-state.model';
import { IScouting, Scouting } from '../scouting.model';

import { ScoutingService } from './scouting.service';

describe('Scouting Service', () => {
  let service: ScoutingService;
  let httpMock: HttpTestingController;
  let elemDefault: IScouting;
  let expectedResult: IScouting | IScouting[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ScoutingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      scoutingDate: currentDate,
      scout: 'AAAAAAA',
      scoutingType: ScoutingType.Growth,
      scoutingCoordinates: 0,
      scoutingArea: 0,
      cropState: CropState.Bad,
      comments: 'AAAAAAA',
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
          scoutingDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a Scouting', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          scoutingDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          scoutingDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Scouting()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Scouting', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          scoutingDate: currentDate.format(DATE_TIME_FORMAT),
          scout: 'BBBBBB',
          scoutingType: 'BBBBBB',
          scoutingCoordinates: 1,
          scoutingArea: 1,
          cropState: 'BBBBBB',
          comments: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          scoutingDate: currentDate,
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

    it('should partial update a Scouting', () => {
      const patchObject = Object.assign(
        {
          scout: 'BBBBBB',
          scoutingType: 'BBBBBB',
          scoutingCoordinates: 1,
          scoutingArea: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Scouting()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          scoutingDate: currentDate,
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

    it('should return a list of Scouting', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          scoutingDate: currentDate.format(DATE_TIME_FORMAT),
          scout: 'BBBBBB',
          scoutingType: 'BBBBBB',
          scoutingCoordinates: 1,
          scoutingArea: 1,
          cropState: 'BBBBBB',
          comments: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          scoutingDate: currentDate,
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

    it('should delete a Scouting', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addScoutingToCollectionIfMissing', () => {
      it('should add a Scouting to an empty array', () => {
        const scouting: IScouting = { id: 123 };
        expectedResult = service.addScoutingToCollectionIfMissing([], scouting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scouting);
      });

      it('should not add a Scouting to an array that contains it', () => {
        const scouting: IScouting = { id: 123 };
        const scoutingCollection: IScouting[] = [
          {
            ...scouting,
          },
          { id: 456 },
        ];
        expectedResult = service.addScoutingToCollectionIfMissing(scoutingCollection, scouting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Scouting to an array that doesn't contain it", () => {
        const scouting: IScouting = { id: 123 };
        const scoutingCollection: IScouting[] = [{ id: 456 }];
        expectedResult = service.addScoutingToCollectionIfMissing(scoutingCollection, scouting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scouting);
      });

      it('should add only unique Scouting to an array', () => {
        const scoutingArray: IScouting[] = [{ id: 123 }, { id: 456 }, { id: 2805 }];
        const scoutingCollection: IScouting[] = [{ id: 123 }];
        expectedResult = service.addScoutingToCollectionIfMissing(scoutingCollection, ...scoutingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const scouting: IScouting = { id: 123 };
        const scouting2: IScouting = { id: 456 };
        expectedResult = service.addScoutingToCollectionIfMissing([], scouting, scouting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scouting);
        expect(expectedResult).toContain(scouting2);
      });

      it('should accept null and undefined values', () => {
        const scouting: IScouting = { id: 123 };
        expectedResult = service.addScoutingToCollectionIfMissing([], null, scouting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scouting);
      });

      it('should return initial array if no Scouting is added', () => {
        const scoutingCollection: IScouting[] = [{ id: 123 }];
        expectedResult = service.addScoutingToCollectionIfMissing(scoutingCollection, undefined, null);
        expect(expectedResult).toEqual(scoutingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
