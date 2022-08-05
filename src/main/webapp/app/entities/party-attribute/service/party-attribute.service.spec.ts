import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyAttribute, PartyAttribute } from '../party-attribute.model';

import { PartyAttributeService } from './party-attribute.service';

describe('PartyAttribute Service', () => {
  let service: PartyAttributeService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyAttribute;
  let expectedResult: IPartyAttribute | IPartyAttribute[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyAttributeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      attributeName: 'AAAAAAA',
      attributeValue: 0,
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

    it('should create a PartyAttribute', () => {
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

      service.create(new PartyAttribute()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          attributeName: 'BBBBBB',
          attributeValue: 1,
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

    it('should partial update a PartyAttribute', () => {
      const patchObject = Object.assign(
        {
          attributeValue: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new PartyAttribute()
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

    it('should return a list of PartyAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          attributeName: 'BBBBBB',
          attributeValue: 1,
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

    it('should delete a PartyAttribute', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyAttributeToCollectionIfMissing', () => {
      it('should add a PartyAttribute to an empty array', () => {
        const partyAttribute: IPartyAttribute = { id: 123 };
        expectedResult = service.addPartyAttributeToCollectionIfMissing([], partyAttribute);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyAttribute);
      });

      it('should not add a PartyAttribute to an array that contains it', () => {
        const partyAttribute: IPartyAttribute = { id: 123 };
        const partyAttributeCollection: IPartyAttribute[] = [
          {
            ...partyAttribute,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyAttributeToCollectionIfMissing(partyAttributeCollection, partyAttribute);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyAttribute to an array that doesn't contain it", () => {
        const partyAttribute: IPartyAttribute = { id: 123 };
        const partyAttributeCollection: IPartyAttribute[] = [{ id: 456 }];
        expectedResult = service.addPartyAttributeToCollectionIfMissing(partyAttributeCollection, partyAttribute);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyAttribute);
      });

      it('should add only unique PartyAttribute to an array', () => {
        const partyAttributeArray: IPartyAttribute[] = [{ id: 123 }, { id: 456 }, { id: 5790 }];
        const partyAttributeCollection: IPartyAttribute[] = [{ id: 123 }];
        expectedResult = service.addPartyAttributeToCollectionIfMissing(partyAttributeCollection, ...partyAttributeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyAttribute: IPartyAttribute = { id: 123 };
        const partyAttribute2: IPartyAttribute = { id: 456 };
        expectedResult = service.addPartyAttributeToCollectionIfMissing([], partyAttribute, partyAttribute2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyAttribute);
        expect(expectedResult).toContain(partyAttribute2);
      });

      it('should accept null and undefined values', () => {
        const partyAttribute: IPartyAttribute = { id: 123 };
        expectedResult = service.addPartyAttributeToCollectionIfMissing([], null, partyAttribute, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyAttribute);
      });

      it('should return initial array if no PartyAttribute is added', () => {
        const partyAttributeCollection: IPartyAttribute[] = [{ id: 123 }];
        expectedResult = service.addPartyAttributeToCollectionIfMissing(partyAttributeCollection, undefined, null);
        expect(expectedResult).toEqual(partyAttributeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
