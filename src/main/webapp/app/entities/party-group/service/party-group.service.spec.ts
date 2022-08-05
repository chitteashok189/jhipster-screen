import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyGroup, PartyGroup } from '../party-group.model';

import { PartyGroupService } from './party-group.service';

describe('PartyGroup Service', () => {
  let service: PartyGroupService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyGroup;
  let expectedResult: IPartyGroup | IPartyGroup[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyGroupService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 0,
      groupName: 'AAAAAAA',
      localGroupName: 'AAAAAAA',
      officeSiteName: 'AAAAAAA',
      comments: 'AAAAAAA',
      logoImageUrl: 'AAAAAAA',
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

    it('should create a PartyGroup', () => {
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

      service.create(new PartyGroup()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyGroup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          groupName: 'BBBBBB',
          localGroupName: 'BBBBBB',
          officeSiteName: 'BBBBBB',
          comments: 'BBBBBB',
          logoImageUrl: 'BBBBBB',
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

    it('should partial update a PartyGroup', () => {
      const patchObject = Object.assign(
        {
          officeSiteName: 'BBBBBB',
          comments: 'BBBBBB',
          logoImageUrl: 'BBBBBB',
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyGroup()
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

    it('should return a list of PartyGroup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 1,
          groupName: 'BBBBBB',
          localGroupName: 'BBBBBB',
          officeSiteName: 'BBBBBB',
          comments: 'BBBBBB',
          logoImageUrl: 'BBBBBB',
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

    it('should delete a PartyGroup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyGroupToCollectionIfMissing', () => {
      it('should add a PartyGroup to an empty array', () => {
        const partyGroup: IPartyGroup = { id: 123 };
        expectedResult = service.addPartyGroupToCollectionIfMissing([], partyGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyGroup);
      });

      it('should not add a PartyGroup to an array that contains it', () => {
        const partyGroup: IPartyGroup = { id: 123 };
        const partyGroupCollection: IPartyGroup[] = [
          {
            ...partyGroup,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyGroupToCollectionIfMissing(partyGroupCollection, partyGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyGroup to an array that doesn't contain it", () => {
        const partyGroup: IPartyGroup = { id: 123 };
        const partyGroupCollection: IPartyGroup[] = [{ id: 456 }];
        expectedResult = service.addPartyGroupToCollectionIfMissing(partyGroupCollection, partyGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyGroup);
      });

      it('should add only unique PartyGroup to an array', () => {
        const partyGroupArray: IPartyGroup[] = [{ id: 123 }, { id: 456 }, { id: 56733 }];
        const partyGroupCollection: IPartyGroup[] = [{ id: 123 }];
        expectedResult = service.addPartyGroupToCollectionIfMissing(partyGroupCollection, ...partyGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyGroup: IPartyGroup = { id: 123 };
        const partyGroup2: IPartyGroup = { id: 456 };
        expectedResult = service.addPartyGroupToCollectionIfMissing([], partyGroup, partyGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyGroup);
        expect(expectedResult).toContain(partyGroup2);
      });

      it('should accept null and undefined values', () => {
        const partyGroup: IPartyGroup = { id: 123 };
        expectedResult = service.addPartyGroupToCollectionIfMissing([], null, partyGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyGroup);
      });

      it('should return initial array if no PartyGroup is added', () => {
        const partyGroupCollection: IPartyGroup[] = [{ id: 123 }];
        expectedResult = service.addPartyGroupToCollectionIfMissing(partyGroupCollection, undefined, null);
        expect(expectedResult).toEqual(partyGroupCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
