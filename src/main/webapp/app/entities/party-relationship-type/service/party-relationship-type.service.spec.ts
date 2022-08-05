import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyRelationshipType, PartyRelationshipType } from '../party-relationship-type.model';

import { PartyRelationshipTypeService } from './party-relationship-type.service';

describe('PartyRelationshipType Service', () => {
  let service: PartyRelationshipTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyRelationshipType;
  let expectedResult: IPartyRelationshipType | IPartyRelationshipType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyRelationshipTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 0,
      hasTable: false,
      partyRelationshipName: 'AAAAAAA',
      description: 'AAAAAAA',
      roleTypeIdValidFrom: 0,
      roleTypeIdValidTo: 0,
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

    it('should create a PartyRelationshipType', () => {
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

      service.create(new PartyRelationshipType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyRelationshipType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          hasTable: true,
          partyRelationshipName: 'BBBBBB',
          description: 'BBBBBB',
          roleTypeIdValidFrom: 1,
          roleTypeIdValidTo: 1,
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

    it('should partial update a PartyRelationshipType', () => {
      const patchObject = Object.assign(
        {
          createdBy: 1,
        },
        new PartyRelationshipType()
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

    it('should return a list of PartyRelationshipType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          hasTable: true,
          partyRelationshipName: 'BBBBBB',
          description: 'BBBBBB',
          roleTypeIdValidFrom: 1,
          roleTypeIdValidTo: 1,
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

    it('should delete a PartyRelationshipType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyRelationshipTypeToCollectionIfMissing', () => {
      it('should add a PartyRelationshipType to an empty array', () => {
        const partyRelationshipType: IPartyRelationshipType = { id: 123 };
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing([], partyRelationshipType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRelationshipType);
      });

      it('should not add a PartyRelationshipType to an array that contains it', () => {
        const partyRelationshipType: IPartyRelationshipType = { id: 123 };
        const partyRelationshipTypeCollection: IPartyRelationshipType[] = [
          {
            ...partyRelationshipType,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing(partyRelationshipTypeCollection, partyRelationshipType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyRelationshipType to an array that doesn't contain it", () => {
        const partyRelationshipType: IPartyRelationshipType = { id: 123 };
        const partyRelationshipTypeCollection: IPartyRelationshipType[] = [{ id: 456 }];
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing(partyRelationshipTypeCollection, partyRelationshipType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRelationshipType);
      });

      it('should add only unique PartyRelationshipType to an array', () => {
        const partyRelationshipTypeArray: IPartyRelationshipType[] = [{ id: 123 }, { id: 456 }, { id: 74075 }];
        const partyRelationshipTypeCollection: IPartyRelationshipType[] = [{ id: 123 }];
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing(
          partyRelationshipTypeCollection,
          ...partyRelationshipTypeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyRelationshipType: IPartyRelationshipType = { id: 123 };
        const partyRelationshipType2: IPartyRelationshipType = { id: 456 };
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing([], partyRelationshipType, partyRelationshipType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyRelationshipType);
        expect(expectedResult).toContain(partyRelationshipType2);
      });

      it('should accept null and undefined values', () => {
        const partyRelationshipType: IPartyRelationshipType = { id: 123 };
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing([], null, partyRelationshipType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyRelationshipType);
      });

      it('should return initial array if no PartyRelationshipType is added', () => {
        const partyRelationshipTypeCollection: IPartyRelationshipType[] = [{ id: 123 }];
        expectedResult = service.addPartyRelationshipTypeToCollectionIfMissing(partyRelationshipTypeCollection, undefined, null);
        expect(expectedResult).toEqual(partyRelationshipTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
