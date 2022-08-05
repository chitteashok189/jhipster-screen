import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { MaterialType } from 'app/entities/enumerations/material-type.model';
import { IRawMaterial, RawMaterial } from '../raw-material.model';

import { RawMaterialService } from './raw-material.service';

describe('RawMaterial Service', () => {
  let service: RawMaterialService;
  let httpMock: HttpTestingController;
  let elemDefault: IRawMaterial;
  let expectedResult: IRawMaterial | IRawMaterial[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RawMaterialService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      quantity: 0,
      uOM: 0,
      materialType: MaterialType.Vegetables,
      price: 0,
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

    it('should create a RawMaterial', () => {
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

      service.create(new RawMaterial()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RawMaterial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          quantity: 1,
          uOM: 1,
          materialType: 'BBBBBB',
          price: 1,
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

    it('should partial update a RawMaterial', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new RawMaterial()
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

    it('should return a list of RawMaterial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          quantity: 1,
          uOM: 1,
          materialType: 'BBBBBB',
          price: 1,
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

    it('should delete a RawMaterial', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRawMaterialToCollectionIfMissing', () => {
      it('should add a RawMaterial to an empty array', () => {
        const rawMaterial: IRawMaterial = { id: 123 };
        expectedResult = service.addRawMaterialToCollectionIfMissing([], rawMaterial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterial);
      });

      it('should not add a RawMaterial to an array that contains it', () => {
        const rawMaterial: IRawMaterial = { id: 123 };
        const rawMaterialCollection: IRawMaterial[] = [
          {
            ...rawMaterial,
          },
          { id: 456 },
        ];
        expectedResult = service.addRawMaterialToCollectionIfMissing(rawMaterialCollection, rawMaterial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RawMaterial to an array that doesn't contain it", () => {
        const rawMaterial: IRawMaterial = { id: 123 };
        const rawMaterialCollection: IRawMaterial[] = [{ id: 456 }];
        expectedResult = service.addRawMaterialToCollectionIfMissing(rawMaterialCollection, rawMaterial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterial);
      });

      it('should add only unique RawMaterial to an array', () => {
        const rawMaterialArray: IRawMaterial[] = [{ id: 123 }, { id: 456 }, { id: 19699 }];
        const rawMaterialCollection: IRawMaterial[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialToCollectionIfMissing(rawMaterialCollection, ...rawMaterialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const rawMaterial: IRawMaterial = { id: 123 };
        const rawMaterial2: IRawMaterial = { id: 456 };
        expectedResult = service.addRawMaterialToCollectionIfMissing([], rawMaterial, rawMaterial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(rawMaterial);
        expect(expectedResult).toContain(rawMaterial2);
      });

      it('should accept null and undefined values', () => {
        const rawMaterial: IRawMaterial = { id: 123 };
        expectedResult = service.addRawMaterialToCollectionIfMissing([], null, rawMaterial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(rawMaterial);
      });

      it('should return initial array if no RawMaterial is added', () => {
        const rawMaterialCollection: IRawMaterial[] = [{ id: 123 }];
        expectedResult = service.addRawMaterialToCollectionIfMissing(rawMaterialCollection, undefined, null);
        expect(expectedResult).toEqual(rawMaterialCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
