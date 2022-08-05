import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyStatusItem, PartyStatusItem } from '../party-status-item.model';

import { PartyStatusItemService } from './party-status-item.service';

describe('PartyStatusItem Service', () => {
  let service: PartyStatusItemService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyStatusItem;
  let expectedResult: IPartyStatusItem | IPartyStatusItem[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyStatusItemService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      statusCode: 0,
      sequenceID: 0,
      description: 'AAAAAAA',
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

    it('should create a PartyStatusItem', () => {
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

      service.create(new PartyStatusItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyStatusItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          statusCode: 1,
          sequenceID: 1,
          description: 'BBBBBB',
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

    it('should partial update a PartyStatusItem', () => {
      const patchObject = Object.assign(
        {
          statusCode: 1,
          sequenceID: 1,
          description: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyStatusItem()
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

    it('should return a list of PartyStatusItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          statusCode: 1,
          sequenceID: 1,
          description: 'BBBBBB',
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

    it('should delete a PartyStatusItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyStatusItemToCollectionIfMissing', () => {
      it('should add a PartyStatusItem to an empty array', () => {
        const partyStatusItem: IPartyStatusItem = { id: 123 };
        expectedResult = service.addPartyStatusItemToCollectionIfMissing([], partyStatusItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatusItem);
      });

      it('should not add a PartyStatusItem to an array that contains it', () => {
        const partyStatusItem: IPartyStatusItem = { id: 123 };
        const partyStatusItemCollection: IPartyStatusItem[] = [
          {
            ...partyStatusItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyStatusItemToCollectionIfMissing(partyStatusItemCollection, partyStatusItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyStatusItem to an array that doesn't contain it", () => {
        const partyStatusItem: IPartyStatusItem = { id: 123 };
        const partyStatusItemCollection: IPartyStatusItem[] = [{ id: 456 }];
        expectedResult = service.addPartyStatusItemToCollectionIfMissing(partyStatusItemCollection, partyStatusItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatusItem);
      });

      it('should add only unique PartyStatusItem to an array', () => {
        const partyStatusItemArray: IPartyStatusItem[] = [{ id: 123 }, { id: 456 }, { id: 7460 }];
        const partyStatusItemCollection: IPartyStatusItem[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusItemToCollectionIfMissing(partyStatusItemCollection, ...partyStatusItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyStatusItem: IPartyStatusItem = { id: 123 };
        const partyStatusItem2: IPartyStatusItem = { id: 456 };
        expectedResult = service.addPartyStatusItemToCollectionIfMissing([], partyStatusItem, partyStatusItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyStatusItem);
        expect(expectedResult).toContain(partyStatusItem2);
      });

      it('should accept null and undefined values', () => {
        const partyStatusItem: IPartyStatusItem = { id: 123 };
        expectedResult = service.addPartyStatusItemToCollectionIfMissing([], null, partyStatusItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyStatusItem);
      });

      it('should return initial array if no PartyStatusItem is added', () => {
        const partyStatusItemCollection: IPartyStatusItem[] = [{ id: 123 }];
        expectedResult = service.addPartyStatusItemToCollectionIfMissing(partyStatusItemCollection, undefined, null);
        expect(expectedResult).toEqual(partyStatusItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
