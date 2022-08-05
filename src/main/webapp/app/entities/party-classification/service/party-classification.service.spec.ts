import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyClassification, PartyClassification } from '../party-classification.model';

import { PartyClassificationService } from './party-classification.service';

describe('PartyClassification Service', () => {
  let service: PartyClassificationService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyClassification;
  let expectedResult: IPartyClassification | IPartyClassification[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyClassificationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      fromDate: currentDate,
      thruDate: currentDate,
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
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a PartyClassification', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new PartyClassification()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyClassification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should partial update a PartyClassification', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyClassification()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should return a list of PartyClassification', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should delete a PartyClassification', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyClassificationToCollectionIfMissing', () => {
      it('should add a PartyClassification to an empty array', () => {
        const partyClassification: IPartyClassification = { id: 123 };
        expectedResult = service.addPartyClassificationToCollectionIfMissing([], partyClassification);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyClassification);
      });

      it('should not add a PartyClassification to an array that contains it', () => {
        const partyClassification: IPartyClassification = { id: 123 };
        const partyClassificationCollection: IPartyClassification[] = [
          {
            ...partyClassification,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyClassificationToCollectionIfMissing(partyClassificationCollection, partyClassification);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyClassification to an array that doesn't contain it", () => {
        const partyClassification: IPartyClassification = { id: 123 };
        const partyClassificationCollection: IPartyClassification[] = [{ id: 456 }];
        expectedResult = service.addPartyClassificationToCollectionIfMissing(partyClassificationCollection, partyClassification);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyClassification);
      });

      it('should add only unique PartyClassification to an array', () => {
        const partyClassificationArray: IPartyClassification[] = [{ id: 123 }, { id: 456 }, { id: 41440 }];
        const partyClassificationCollection: IPartyClassification[] = [{ id: 123 }];
        expectedResult = service.addPartyClassificationToCollectionIfMissing(partyClassificationCollection, ...partyClassificationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyClassification: IPartyClassification = { id: 123 };
        const partyClassification2: IPartyClassification = { id: 456 };
        expectedResult = service.addPartyClassificationToCollectionIfMissing([], partyClassification, partyClassification2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyClassification);
        expect(expectedResult).toContain(partyClassification2);
      });

      it('should accept null and undefined values', () => {
        const partyClassification: IPartyClassification = { id: 123 };
        expectedResult = service.addPartyClassificationToCollectionIfMissing([], null, partyClassification, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyClassification);
      });

      it('should return initial array if no PartyClassification is added', () => {
        const partyClassificationCollection: IPartyClassification[] = [{ id: 123 }];
        expectedResult = service.addPartyClassificationToCollectionIfMissing(partyClassificationCollection, undefined, null);
        expect(expectedResult).toEqual(partyClassificationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
