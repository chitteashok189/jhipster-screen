import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyStatusType, PartyStatusType } from '../party-status-type.model';

import { PartyStatusTypeService } from './party-status-type.service';

describe('PartyStatusType Service', () => {
  let service: PartyStatusTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyStatusType;
  let expectedResult: IPartyStatusType | IPartyStatusType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyStatusTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      parentTypeID: 0,
      hasTable: false,
      description: 'AAAAAAA',
      childStatusType: 0,
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

    it('should create a PartyStatusType', () => {
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

      service.create(new PartyStatusType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyStatusType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          parentTypeID: 1,
          hasTable: true,
          description: 'BBBBBB',
          childStatusType: 1,
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

    it('should partial update a PartyStatusType', () => {
      const patchObject = Object.assign(
        {
          parentTypeID: 1,
          description: 'BBBBBB',
          childStatusType: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyStatusType()
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

    it('should return a list of PartyStatusType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          parentTypeID: 1,
          hasTable: true,
          description: 'BBBBBB',
          childStatusType: 1,
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

    it('should delete a PartyStatusType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyStatusTypeToCollectionIfMissing', () => {
      it('should add a PartyStatusType to an empty array', () => {
        const partyStatusType: IPartyStatusType = { id: 123 };
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing([], partyStatusType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatusType);
      });

      it('should not add a PartyStatusType to an array that contains it', () => {
        const partyStatusType: IPartyStatusType = { id: 123 };
        const partyStatusTypeCollection: IPartyStatusType[] = [
          {
            ...partyStatusType,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing(partyStatusTypeCollection, partyStatusType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyStatusType to an array that doesn't contain it", () => {
        const partyStatusType: IPartyStatusType = { id: 123 };
        const partyStatusTypeCollection: IPartyStatusType[] = [{ id: 456 }];
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing(partyStatusTypeCollection, partyStatusType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatusType);
      });

      it('should add only unique PartyStatusType to an array', () => {
        const partyStatusTypeArray: IPartyStatusType[] = [{ id: 123 }, { id: 456 }, { id: 99876 }];
        const partyStatusTypeCollection: IPartyStatusType[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing(partyStatusTypeCollection, ...partyStatusTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyStatusType: IPartyStatusType = { id: 123 };
        const partyStatusType2: IPartyStatusType = { id: 456 };
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing([], partyStatusType, partyStatusType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatusType);
        expect(expectedResult).toContain(partyStatusType2);
      });

      it('should accept null and undefined values', () => {
        const partyStatusType: IPartyStatusType = { id: 123 };
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing([], null, partyStatusType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatusType);
      });

      it('should return initial array if no PartyStatusType is added', () => {
        const partyStatusTypeCollection: IPartyStatusType[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusTypeToCollectionIfMissing(partyStatusTypeCollection, undefined, null);
        expect(expectedResult).toEqual(partyStatusTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
