import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEnnumerationType, EnnumerationType } from '../ennumeration-type.model';

import { EnnumerationTypeService } from './ennumeration-type.service';

describe('EnnumerationType Service', () => {
  let service: EnnumerationTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IEnnumerationType;
  let expectedResult: IEnnumerationType | IEnnumerationType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EnnumerationTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      ennumerationType: 0,
      hasTable: false,
      description: 'AAAAAAA',
      ennumeration: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a EnnumerationType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EnnumerationType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnnumerationType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ennumerationType: 1,
          hasTable: true,
          description: 'BBBBBB',
          ennumeration: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnnumerationType', () => {
      const patchObject = Object.assign(
        {
          ennumerationType: 1,
          ennumeration: 'BBBBBB',
        },
        new EnnumerationType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnnumerationType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          ennumerationType: 1,
          hasTable: true,
          description: 'BBBBBB',
          ennumeration: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a EnnumerationType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEnnumerationTypeToCollectionIfMissing', () => {
      it('should add a EnnumerationType to an empty array', () => {
        const ennumerationType: IEnnumerationType = { id: 123 };
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing([], ennumerationType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ennumerationType);
      });

      it('should not add a EnnumerationType to an array that contains it', () => {
        const ennumerationType: IEnnumerationType = { id: 123 };
        const ennumerationTypeCollection: IEnnumerationType[] = [
          {
            ...ennumerationType,
          },
          { id: 456 },
        ];
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing(ennumerationTypeCollection, ennumerationType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnnumerationType to an array that doesn't contain it", () => {
        const ennumerationType: IEnnumerationType = { id: 123 };
        const ennumerationTypeCollection: IEnnumerationType[] = [{ id: 456 }];
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing(ennumerationTypeCollection, ennumerationType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ennumerationType);
      });

      it('should add only unique EnnumerationType to an array', () => {
        const ennumerationTypeArray: IEnnumerationType[] = [{ id: 123 }, { id: 456 }, { id: 24981 }];
        const ennumerationTypeCollection: IEnnumerationType[] = [{ id: 123 }];
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing(ennumerationTypeCollection, ...ennumerationTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ennumerationType: IEnnumerationType = { id: 123 };
        const ennumerationType2: IEnnumerationType = { id: 456 };
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing([], ennumerationType, ennumerationType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ennumerationType);
        expect(expectedResult).toContain(ennumerationType2);
      });

      it('should accept null and undefined values', () => {
        const ennumerationType: IEnnumerationType = { id: 123 };
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing([], null, ennumerationType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ennumerationType);
      });

      it('should return initial array if no EnnumerationType is added', () => {
        const ennumerationTypeCollection: IEnnumerationType[] = [{ id: 123 }];
        expectedResult = service.addEnnumerationTypeToCollectionIfMissing(ennumerationTypeCollection, undefined, null);
        expect(expectedResult).toEqual(ennumerationTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
