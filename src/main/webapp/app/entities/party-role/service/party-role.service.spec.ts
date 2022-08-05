import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyRole, PartyRole } from '../party-role.model';

import { PartyRoleService } from './party-role.service';

describe('PartyRole Service', () => {
  let service: PartyRoleService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyRole;
  let expectedResult: IPartyRole | IPartyRole[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyRoleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      fromAgreement: 'AAAAAAA',
      toAgreement: 'AAAAAAA',
      fromCommunicationEvent: 'AAAAAAA',
      toCommunicationEvent: 'AAAAAAA',
      partyIdFrom: 0,
      partyIdTO: 0,
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

    it('should create a PartyRole', () => {
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

      service.create(new PartyRole()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyRole', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromAgreement: 'BBBBBB',
          toAgreement: 'BBBBBB',
          fromCommunicationEvent: 'BBBBBB',
          toCommunicationEvent: 'BBBBBB',
          partyIdFrom: 1,
          partyIdTO: 1,
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

    it('should partial update a PartyRole', () => {
      const patchObject = Object.assign(
        {
          fromAgreement: 'BBBBBB',
          toAgreement: 'BBBBBB',
          toCommunicationEvent: 'BBBBBB',
          partyIdFrom: 1,
          createdBy: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyRole()
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

    it('should return a list of PartyRole', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromAgreement: 'BBBBBB',
          toAgreement: 'BBBBBB',
          fromCommunicationEvent: 'BBBBBB',
          toCommunicationEvent: 'BBBBBB',
          partyIdFrom: 1,
          partyIdTO: 1,
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

    it('should delete a PartyRole', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyRoleToCollectionIfMissing', () => {
      it('should add a PartyRole to an empty array', () => {
        const partyRole: IPartyRole = { id: 123 };
        expectedResult = service.addPartyRoleToCollectionIfMissing([], partyRole);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRole);
      });

      it('should not add a PartyRole to an array that contains it', () => {
        const partyRole: IPartyRole = { id: 123 };
        const partyRoleCollection: IPartyRole[] = [
          {
            ...partyRole,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyRoleToCollectionIfMissing(partyRoleCollection, partyRole);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyRole to an array that doesn't contain it", () => {
        const partyRole: IPartyRole = { id: 123 };
        const partyRoleCollection: IPartyRole[] = [{ id: 456 }];
        expectedResult = service.addPartyRoleToCollectionIfMissing(partyRoleCollection, partyRole);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRole);
      });

      it('should add only unique PartyRole to an array', () => {
        const partyRoleArray: IPartyRole[] = [{ id: 123 }, { id: 456 }, { id: 80052 }];
        const partyRoleCollection: IPartyRole[] = [{ id: 123 }];
        expectedResult = service.addPartyRoleToCollectionIfMissing(partyRoleCollection, ...partyRoleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyRole: IPartyRole = { id: 123 };
        const partyRole2: IPartyRole = { id: 456 };
        expectedResult = service.addPartyRoleToCollectionIfMissing([], partyRole, partyRole2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRole);
        expect(expectedResult).toContain(partyRole2);
      });

      it('should accept null and undefined values', () => {
        const partyRole: IPartyRole = { id: 123 };
        expectedResult = service.addPartyRoleToCollectionIfMissing([], null, partyRole, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRole);
      });

      it('should return initial array if no PartyRole is added', () => {
        const partyRoleCollection: IPartyRole[] = [{ id: 123 }];
        expectedResult = service.addPartyRoleToCollectionIfMissing(partyRoleCollection, undefined, null);
        expect(expectedResult).toEqual(partyRoleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
