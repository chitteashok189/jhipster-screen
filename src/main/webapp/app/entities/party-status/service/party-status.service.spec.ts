import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyStatus, PartyStatus } from '../party-status.model';

import { PartyStatusService } from './party-status.service';

describe('PartyStatus Service', () => {
  let service: PartyStatusService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyStatus;
  let expectedResult: IPartyStatus | IPartyStatus[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyStatusService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      statusDate: currentDate,
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
          statusDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a PartyStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          statusDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          statusDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new PartyStatus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          statusDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          statusDate: currentDate,
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

    it('should partial update a PartyStatus', () => {
      const patchObject = Object.assign(
        {
          statusDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyStatus()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          statusDate: currentDate,
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

    it('should return a list of PartyStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          statusDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          statusDate: currentDate,
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

    it('should delete a PartyStatus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyStatusToCollectionIfMissing', () => {
      it('should add a PartyStatus to an empty array', () => {
        const partyStatus: IPartyStatus = { id: 123 };
        expectedResult = service.addPartyStatusToCollectionIfMissing([], partyStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatus);
      });

      it('should not add a PartyStatus to an array that contains it', () => {
        const partyStatus: IPartyStatus = { id: 123 };
        const partyStatusCollection: IPartyStatus[] = [
          {
            ...partyStatus,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyStatusToCollectionIfMissing(partyStatusCollection, partyStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyStatus to an array that doesn't contain it", () => {
        const partyStatus: IPartyStatus = { id: 123 };
        const partyStatusCollection: IPartyStatus[] = [{ id: 456 }];
        expectedResult = service.addPartyStatusToCollectionIfMissing(partyStatusCollection, partyStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatus);
      });

      it('should add only unique PartyStatus to an array', () => {
        const partyStatusArray: IPartyStatus[] = [{ id: 123 }, { id: 456 }, { id: 14626 }];
        const partyStatusCollection: IPartyStatus[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusToCollectionIfMissing(partyStatusCollection, ...partyStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyStatus: IPartyStatus = { id: 123 };
        const partyStatus2: IPartyStatus = { id: 456 };
        expectedResult = service.addPartyStatusToCollectionIfMissing([], partyStatus, partyStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatus);
        expect(expectedResult).toContain(partyStatus2);
      });

      it('should accept null and undefined values', () => {
        const partyStatus: IPartyStatus = { id: 123 };
        expectedResult = service.addPartyStatusToCollectionIfMissing([], null, partyStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatus);
      });

      it('should return initial array if no PartyStatus is added', () => {
        const partyStatusCollection: IPartyStatus[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusToCollectionIfMissing(partyStatusCollection, undefined, null);
        expect(expectedResult).toEqual(partyStatusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
