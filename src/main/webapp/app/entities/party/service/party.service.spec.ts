import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IParty, Party } from '../party.model';

import { PartyService } from './party.service';

describe('Party Service', () => {
  let service: PartyService;
  let httpMock: HttpTestingController;
  let elemDefault: IParty;
  let expectedResult: IParty | IParty[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      partyName: 'AAAAAAA',
      statusID: false,
      description: 'AAAAAAA',
      externalID: 0,
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

    it('should create a Party', () => {
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

      service.create(new Party()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Party', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          partyName: 'BBBBBB',
          statusID: true,
          description: 'BBBBBB',
          externalID: 1,
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

    it('should partial update a Party', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Party()
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

    it('should return a list of Party', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          partyName: 'BBBBBB',
          statusID: true,
          description: 'BBBBBB',
          externalID: 1,
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

    it('should delete a Party', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyToCollectionIfMissing', () => {
      it('should add a Party to an empty array', () => {
        const party: IParty = { id: 123 };
        expectedResult = service.addPartyToCollectionIfMissing([], party);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(party);
      });

      it('should not add a Party to an array that contains it', () => {
        const party: IParty = { id: 123 };
        const partyCollection: IParty[] = [
          {
            ...party,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyToCollectionIfMissing(partyCollection, party);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Party to an array that doesn't contain it", () => {
        const party: IParty = { id: 123 };
        const partyCollection: IParty[] = [{ id: 456 }];
        expectedResult = service.addPartyToCollectionIfMissing(partyCollection, party);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(party);
      });

      it('should add only unique Party to an array', () => {
        const partyArray: IParty[] = [{ id: 123 }, { id: 456 }, { id: 69593 }];
        const partyCollection: IParty[] = [{ id: 123 }];
        expectedResult = service.addPartyToCollectionIfMissing(partyCollection, ...partyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const party: IParty = { id: 123 };
        const party2: IParty = { id: 456 };
        expectedResult = service.addPartyToCollectionIfMissing([], party, party2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(party);
        expect(expectedResult).toContain(party2);
      });

      it('should accept null and undefined values', () => {
        const party: IParty = { id: 123 };
        expectedResult = service.addPartyToCollectionIfMissing([], null, party, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(party);
      });

      it('should return initial array if no Party is added', () => {
        const partyCollection: IParty[] = [{ id: 123 }];
        expectedResult = service.addPartyToCollectionIfMissing(partyCollection, undefined, null);
        expect(expectedResult).toEqual(partyCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
