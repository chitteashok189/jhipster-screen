import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPartyNote, PartyNote } from '../party-note.model';

import { PartyNoteService } from './party-note.service';

describe('PartyNote Service', () => {
  let service: PartyNoteService;
  let httpMock: HttpTestingController;
  let elemDefault: IPartyNote;
  let expectedResult: IPartyNote | IPartyNote[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartyNoteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      noteID: 0,
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

    it('should create a PartyNote', () => {
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

      service.create(new PartyNote()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartyNote', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          noteID: 1,
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

    it('should partial update a PartyNote', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          noteID: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PartyNote()
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

    it('should return a list of PartyNote', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          noteID: 1,
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

    it('should delete a PartyNote', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPartyNoteToCollectionIfMissing', () => {
      it('should add a PartyNote to an empty array', () => {
        const partyNote: IPartyNote = { id: 123 };
        expectedResult = service.addPartyNoteToCollectionIfMissing([], partyNote);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyNote);
      });

      it('should not add a PartyNote to an array that contains it', () => {
        const partyNote: IPartyNote = { id: 123 };
        const partyNoteCollection: IPartyNote[] = [
          {
            ...partyNote,
          },
          { id: 456 },
        ];
        expectedResult = service.addPartyNoteToCollectionIfMissing(partyNoteCollection, partyNote);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartyNote to an array that doesn't contain it", () => {
        const partyNote: IPartyNote = { id: 123 };
        const partyNoteCollection: IPartyNote[] = [{ id: 456 }];
        expectedResult = service.addPartyNoteToCollectionIfMissing(partyNoteCollection, partyNote);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyNote);
      });

      it('should add only unique PartyNote to an array', () => {
        const partyNoteArray: IPartyNote[] = [{ id: 123 }, { id: 456 }, { id: 55935 }];
        const partyNoteCollection: IPartyNote[] = [{ id: 123 }];
        expectedResult = service.addPartyNoteToCollectionIfMissing(partyNoteCollection, ...partyNoteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partyNote: IPartyNote = { id: 123 };
        const partyNote2: IPartyNote = { id: 456 };
        expectedResult = service.addPartyNoteToCollectionIfMissing([], partyNote, partyNote2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partyNote);
        expect(expectedResult).toContain(partyNote2);
      });

      it('should accept null and undefined values', () => {
        const partyNote: IPartyNote = { id: 123 };
        expectedResult = service.addPartyNoteToCollectionIfMissing([], null, partyNote, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partyNote);
      });

      it('should return initial array if no PartyNote is added', () => {
        const partyNoteCollection: IPartyNote[] = [{ id: 123 }];
        expectedResult = service.addPartyNoteToCollectionIfMissing(partyNoteCollection, undefined, null);
        expect(expectedResult).toEqual(partyNoteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
