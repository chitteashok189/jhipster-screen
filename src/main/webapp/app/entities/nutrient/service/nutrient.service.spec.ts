import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { NutrientType } from 'app/entities/enumerations/nutrient-type.model';
import { NutForms } from 'app/entities/enumerations/nut-forms.model';
import { NutTypeID } from 'app/entities/enumerations/nut-type-id.model';
import { INutrient, Nutrient } from '../nutrient.model';

import { NutrientService } from './nutrient.service';

describe('Nutrient Service', () => {
  let service: NutrientService;
  let httpMock: HttpTestingController;
  let elemDefault: INutrient;
  let expectedResult: INutrient | INutrient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NutrientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      nutrientName: 'AAAAAAA',
      type: NutrientType.Organic,
      brandName: 'AAAAAAA',
      nutrientLabel: 0,
      nutrientForms: NutForms.Solid,
      nutrientTypeID: NutTypeID.Inorganic,
      price: 0,
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

    it('should create a Nutrient', () => {
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

      service.create(new Nutrient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Nutrient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          nutrientName: 'BBBBBB',
          type: 'BBBBBB',
          brandName: 'BBBBBB',
          nutrientLabel: 1,
          nutrientForms: 'BBBBBB',
          nutrientTypeID: 'BBBBBB',
          price: 1,
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

    it('should partial update a Nutrient', () => {
      const patchObject = Object.assign(
        {
          nutrientName: 'BBBBBB',
          type: 'BBBBBB',
          nutrientLabel: 1,
          nutrientForms: 'BBBBBB',
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Nutrient()
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

    it('should return a list of Nutrient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          nutrientName: 'BBBBBB',
          type: 'BBBBBB',
          brandName: 'BBBBBB',
          nutrientLabel: 1,
          nutrientForms: 'BBBBBB',
          nutrientTypeID: 'BBBBBB',
          price: 1,
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

    it('should delete a Nutrient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addNutrientToCollectionIfMissing', () => {
      it('should add a Nutrient to an empty array', () => {
        const nutrient: INutrient = { id: 123 };
        expectedResult = service.addNutrientToCollectionIfMissing([], nutrient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nutrient);
      });

      it('should not add a Nutrient to an array that contains it', () => {
        const nutrient: INutrient = { id: 123 };
        const nutrientCollection: INutrient[] = [
          {
            ...nutrient,
          },
          { id: 456 },
        ];
        expectedResult = service.addNutrientToCollectionIfMissing(nutrientCollection, nutrient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Nutrient to an array that doesn't contain it", () => {
        const nutrient: INutrient = { id: 123 };
        const nutrientCollection: INutrient[] = [{ id: 456 }];
        expectedResult = service.addNutrientToCollectionIfMissing(nutrientCollection, nutrient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nutrient);
      });

      it('should add only unique Nutrient to an array', () => {
        const nutrientArray: INutrient[] = [{ id: 123 }, { id: 456 }, { id: 95071 }];
        const nutrientCollection: INutrient[] = [{ id: 123 }];
        expectedResult = service.addNutrientToCollectionIfMissing(nutrientCollection, ...nutrientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nutrient: INutrient = { id: 123 };
        const nutrient2: INutrient = { id: 456 };
        expectedResult = service.addNutrientToCollectionIfMissing([], nutrient, nutrient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nutrient);
        expect(expectedResult).toContain(nutrient2);
      });

      it('should accept null and undefined values', () => {
        const nutrient: INutrient = { id: 123 };
        expectedResult = service.addNutrientToCollectionIfMissing([], null, nutrient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nutrient);
      });

      it('should return initial array if no Nutrient is added', () => {
        const nutrientCollection: INutrient[] = [{ id: 123 }];
        expectedResult = service.addNutrientToCollectionIfMissing(nutrientCollection, undefined, null);
        expect(expectedResult).toEqual(nutrientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
