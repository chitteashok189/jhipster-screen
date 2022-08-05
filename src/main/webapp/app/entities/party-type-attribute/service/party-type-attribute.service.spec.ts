import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyTypeAttribute, PartyTypeAttribute } from '../party-type-attribute.model';

import { PartyTypeAttributeService } from './party-type-attribute.service';

describe('PartyTypeAttribute Service', () => {
  let service: PartyTypeAttributeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyTypeAttribute;
  let expectedResult: IPartyTypeAttribute | IPartyTypeAttribute[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyTypeAttributeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 0,
      attributeName: 'AAAAAAA',
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

    it('should create a PartyTypeAttribute', () => {
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

      service.create(new PartyTypeAttribute()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyTypeAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          attributeName: 'BBBBBB',
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

    it('should partial update a PartyTypeAttribute', () => {
      const patchObject = Object.assign(
        {
          gUID: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyTypeAttribute()
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

    it('should return a list of PartyTypeAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          attributeName: 'BBBBBB',
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

    it('should delete a PartyTypeAttribute', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyTypeAttributeToCollectionIfMissing', () => {
      it('should add a PartyTypeAttribute to an empty array', () => {
        const partyTypeAttribute: IPartyTypeAttribute = { id: 123 };
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing([], partyTypeAttribute);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyTypeAttribute);
      });

      it('should not add a PartyTypeAttribute to an array that contains it', () => {
        const partyTypeAttribute: IPartyTypeAttribute = { id: 123 };
        const partyTypeAttributeCollection: IPartyTypeAttribute[] = [
          {
            ...partyTypeAttribute,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing(partyTypeAttributeCollection, partyTypeAttribute);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyTypeAttribute to an array that doesn't contain it", () => {
        const partyTypeAttribute: IPartyTypeAttribute = { id: 123 };
        const partyTypeAttributeCollection: IPartyTypeAttribute[] = [{ id: 456 }];
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing(partyTypeAttributeCollection, partyTypeAttribute);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyTypeAttribute);
      });

      it('should add only unique PartyTypeAttribute to an array', () => {
        const partyTypeAttributeArray: IPartyTypeAttribute[] = [{ id: 123 }, { id: 456 }, { id: 6230 }];
        const partyTypeAttributeCollection: IPartyTypeAttribute[] = [{ id: 123 }];
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing(partyTypeAttributeCollection, ...partyTypeAttributeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyTypeAttribute: IPartyTypeAttribute = { id: 123 };
        const partyTypeAttribute2: IPartyTypeAttribute = { id: 456 };
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing([], partyTypeAttribute, partyTypeAttribute2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyTypeAttribute);
        expect(expectedResult).toContain(partyTypeAttribute2);
      });

      it('should accept null and undefined values', () => {
        const partyTypeAttribute: IPartyTypeAttribute = { id: 123 };
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing([], null, partyTypeAttribute, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyTypeAttribute);
      });

      it('should return initial array if no PartyTypeAttribute is added', () => {
        const partyTypeAttributeCollection: IPartyTypeAttribute[] = [{ id: 123 }];
        expectedResult = service.addPartyTypeAttributeToCollectionIfMissing(partyTypeAttributeCollection, undefined, null);
        expect(expectedResult).toEqual(partyTypeAttributeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
