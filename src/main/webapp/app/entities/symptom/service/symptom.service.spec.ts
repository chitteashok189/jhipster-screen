import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISymptom, Symptom } from '../symptom.model';

import { SymptomService } from './symptom.service';

describe('Symptom Service', () => {
  let service: SymptomService;
  let httpMock: HttpTestingController;
  let elemDefault: ISymptom;
  let expectedResult: ISymptom | ISymptom[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SymptomService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      observation: 'AAAAAAA',
      symptomImageContentType: 'image/png',
      symptomImage: 'AAAAAAA',
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

    it('should create a Symptom', () => {
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

      service.create(new Symptom()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Symptom', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          observation: 'BBBBBB',
          symptomImage: 'BBBBBB',
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

    it('should partial update a Symptom', () => {
      const patchObject = Object.assign(
        {
          observation: 'BBBBBB',
          createdBy: 1,
        },
        new Symptom()
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

    it('should return a list of Symptom', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          observation: 'BBBBBB',
          symptomImage: 'BBBBBB',
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

    it('should delete a Symptom', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSymptomToCollectionIfMissing', () => {
      it('should add a Symptom to an empty array', () => {
        const symptom: ISymptom = { id: 123 };
        expectedResult = service.addSymptomToCollectionIfMissing([], symptom);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(symptom);
      });

      it('should not add a Symptom to an array that contains it', () => {
        const symptom: ISymptom = { id: 123 };
        const symptomCollection: ISymptom[] = [
          {
            ...symptom,
          },
          { id: 456 },
        ];
        expectedResult = service.addSymptomToCollectionIfMissing(symptomCollection, symptom);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Symptom to an array that doesn't contain it", () => {
        const symptom: ISymptom = { id: 123 };
        const symptomCollection: ISymptom[] = [{ id: 456 }];
        expectedResult = service.addSymptomToCollectionIfMissing(symptomCollection, symptom);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(symptom);
      });

      it('should add only unique Symptom to an array', () => {
        const symptomArray: ISymptom[] = [{ id: 123 }, { id: 456 }, { id: 48783 }];
        const symptomCollection: ISymptom[] = [{ id: 123 }];
        expectedResult = service.addSymptomToCollectionIfMissing(symptomCollection, ...symptomArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const symptom: ISymptom = { id: 123 };
        const symptom2: ISymptom = { id: 456 };
        expectedResult = service.addSymptomToCollectionIfMissing([], symptom, symptom2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(symptom);
        expect(expectedResult).toContain(symptom2);
      });

      it('should accept null and undefined values', () => {
        const symptom: ISymptom = { id: 123 };
        expectedResult = service.addSymptomToCollectionIfMissing([], null, symptom, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(symptom);
      });

      it('should return initial array if no Symptom is added', () => {
        const symptomCollection: ISymptom[] = [{ id: 123 }];
        expectedResult = service.addSymptomToCollectionIfMissing(symptomCollection, undefined, null);
        expect(expectedResult).toEqual(symptomCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
