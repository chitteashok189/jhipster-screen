import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyRelationship, PartyRelationship } from '../party-relationship.model';

import { PartyRelationshipService } from './party-relationship.service';

describe('PartyRelationship Service', () => {
  let service: PartyRelationshipService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyRelationship;
  let expectedResult: IPartyRelationship | IPartyRelationship[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyRelationshipService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      partyIdTo: 0,
      partyIdFrom: 0,
      roleTypeIdFrom: 0,
      roleTypeIdTo: 0,
      fromDate: currentDate,
      thruDate: currentDate,
      relationshipName: 'AAAAAAA',
      positionTitle: 'AAAAAAA',
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

    it('should create a PartyRelationship', () => {
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

      service.create(new PartyRelationship()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyRelationship', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          partyIdTo: 1,
          partyIdFrom: 1,
          roleTypeIdFrom: 1,
          roleTypeIdTo: 1,
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          relationshipName: 'BBBBBB',
          positionTitle: 'BBBBBB',
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

    it('should partial update a PartyRelationship', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          partyIdFrom: 1,
          roleTypeIdFrom: 1,
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          relationshipName: 'BBBBBB',
          positionTitle: 'BBBBBB',
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyRelationship()
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

    it('should return a list of PartyRelationship', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          partyIdTo: 1,
          partyIdFrom: 1,
          roleTypeIdFrom: 1,
          roleTypeIdTo: 1,
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          relationshipName: 'BBBBBB',
          positionTitle: 'BBBBBB',
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

    it('should delete a PartyRelationship', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyRelationshipToCollectionIfMissing', () => {
      it('should add a PartyRelationship to an empty array', () => {
        const partyRelationship: IPartyRelationship = { id: 123 };
        expectedResult = service.addPartyRelationshipToCollectionIfMissing([], partyRelationship);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRelationship);
      });

      it('should not add a PartyRelationship to an array that contains it', () => {
        const partyRelationship: IPartyRelationship = { id: 123 };
        const partyRelationshipCollection: IPartyRelationship[] = [
          {
            ...partyRelationship,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyRelationshipToCollectionIfMissing(partyRelationshipCollection, partyRelationship);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyRelationship to an array that doesn't contain it", () => {
        const partyRelationship: IPartyRelationship = { id: 123 };
        const partyRelationshipCollection: IPartyRelationship[] = [{ id: 456 }];
        expectedResult = service.addPartyRelationshipToCollectionIfMissing(partyRelationshipCollection, partyRelationship);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRelationship);
      });

      it('should add only unique PartyRelationship to an array', () => {
        const partyRelationshipArray: IPartyRelationship[] = [{ id: 123 }, { id: 456 }, { id: 93741 }];
        const partyRelationshipCollection: IPartyRelationship[] = [{ id: 123 }];
        expectedResult = service.addPartyRelationshipToCollectionIfMissing(partyRelationshipCollection, ...partyRelationshipArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyRelationship: IPartyRelationship = { id: 123 };
        const partyRelationship2: IPartyRelationship = { id: 456 };
        expectedResult = service.addPartyRelationshipToCollectionIfMissing([], partyRelationship, partyRelationship2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRelationship);
        expect(expectedResult).toContain(partyRelationship2);
      });

      it('should accept null and undefined values', () => {
        const partyRelationship: IPartyRelationship = { id: 123 };
        expectedResult = service.addPartyRelationshipToCollectionIfMissing([], null, partyRelationship, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRelationship);
      });

      it('should return initial array if no PartyRelationship is added', () => {
        const partyRelationshipCollection: IPartyRelationship[] = [{ id: 123 }];
        expectedResult = service.addPartyRelationshipToCollectionIfMissing(partyRelationshipCollection, undefined, null);
        expect(expectedResult).toEqual(partyRelationshipCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
