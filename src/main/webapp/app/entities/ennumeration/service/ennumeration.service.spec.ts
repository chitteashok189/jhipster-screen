import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEnnumeration, Ennumeration } from '../ennumeration.model';

import { EnnumerationService } from './ennumeration.service';

describe('Ennumeration Service', () => {
  let service: EnnumerationService;
  let httpMock: HttpTestingController;
  let elemDefault: IEnnumeration;
  let expectedResult: IEnnumeration | IEnnumeration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EnnumerationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      enumCode: 0,
      description: 'AAAAAAA',
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

    it('should create a Ennumeration', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Ennumeration()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ennumeration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          enumCode: 1,
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ennumeration', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
        },
        new Ennumeration()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ennumeration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          enumCode: 1,
          description: 'BBBBBB',
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

    it('should delete a Ennumeration', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEnnumerationToCollectionIfMissing', () => {
      it('should add a Ennumeration to an empty array', () => {
        const ennumeration: IEnnumeration = { id: 123 };
        expectedResult = service.addEnnumerationToCollectionIfMissing([], ennumeration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ennumeration);
      });

      it('should not add a Ennumeration to an array that contains it', () => {
        const ennumeration: IEnnumeration = { id: 123 };
        const ennumerationCollection: IEnnumeration[] = [
          {
            ...ennumeration,
          },
          { id: 456 },
        ];
        expectedResult = service.addEnnumerationToCollectionIfMissing(ennumerationCollection, ennumeration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ennumeration to an array that doesn't contain it", () => {
        const ennumeration: IEnnumeration = { id: 123 };
        const ennumerationCollection: IEnnumeration[] = [{ id: 456 }];
        expectedResult = service.addEnnumerationToCollectionIfMissing(ennumerationCollection, ennumeration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ennumeration);
      });

      it('should add only unique Ennumeration to an array', () => {
        const ennumerationArray: IEnnumeration[] = [{ id: 123 }, { id: 456 }, { id: 59309 }];
        const ennumerationCollection: IEnnumeration[] = [{ id: 123 }];
        expectedResult = service.addEnnumerationToCollectionIfMissing(ennumerationCollection, ...ennumerationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ennumeration: IEnnumeration = { id: 123 };
        const ennumeration2: IEnnumeration = { id: 456 };
        expectedResult = service.addEnnumerationToCollectionIfMissing([], ennumeration, ennumeration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ennumeration);
        expect(expectedResult).toContain(ennumeration2);
      });

      it('should accept null and undefined values', () => {
        const ennumeration: IEnnumeration = { id: 123 };
        expectedResult = service.addEnnumerationToCollectionIfMissing([], null, ennumeration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ennumeration);
      });

      it('should return initial array if no Ennumeration is added', () => {
        const ennumerationCollection: IEnnumeration[] = [{ id: 123 }];
        expectedResult = service.addEnnumerationToCollectionIfMissing(ennumerationCollection, undefined, null);
        expect(expectedResult).toEqual(ennumerationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
