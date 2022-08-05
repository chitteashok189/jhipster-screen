import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRoleTypeAttribute, RoleTypeAttribute } from '../role-type-attribute.model';

import { RoleTypeAttributeService } from './role-type-attribute.service';

describe('RoleTypeAttribute Service', () => {
  let service: RoleTypeAttributeService;
  let httpMock: HttpTestingController;
  let elemDefault: IRoleTypeAttribute;
  let expectedResult: IRoleTypeAttribute | IRoleTypeAttribute[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RoleTypeAttributeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      attributeName: 'AAAAAAA',
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

    it('should create a RoleTypeAttribute', () => {
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

      service.create(new RoleTypeAttribute()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RoleTypeAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          attributeName: 'BBBBBB',
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

    it('should partial update a RoleTypeAttribute', () => {
      const patchObject = Object.assign(
        {
          attributeName: 'BBBBBB',
          createdBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new RoleTypeAttribute()
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

    it('should return a list of RoleTypeAttribute', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          attributeName: 'BBBBBB',
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

    it('should delete a RoleTypeAttribute', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRoleTypeAttributeToCollectionIfMissing', () => {
      it('should add a RoleTypeAttribute to an empty array', () => {
        const roleTypeAttribute: IRoleTypeAttribute = { id: 123 };
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing([], roleTypeAttribute);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleTypeAttribute);
      });

      it('should not add a RoleTypeAttribute to an array that contains it', () => {
        const roleTypeAttribute: IRoleTypeAttribute = { id: 123 };
        const roleTypeAttributeCollection: IRoleTypeAttribute[] = [
          {
            ...roleTypeAttribute,
          },
          { id: 456 },
        ];
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing(roleTypeAttributeCollection, roleTypeAttribute);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RoleTypeAttribute to an array that doesn't contain it", () => {
        const roleTypeAttribute: IRoleTypeAttribute = { id: 123 };
        const roleTypeAttributeCollection: IRoleTypeAttribute[] = [{ id: 456 }];
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing(roleTypeAttributeCollection, roleTypeAttribute);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleTypeAttribute);
      });

      it('should add only unique RoleTypeAttribute to an array', () => {
        const roleTypeAttributeArray: IRoleTypeAttribute[] = [{ id: 123 }, { id: 456 }, { id: 43366 }];
        const roleTypeAttributeCollection: IRoleTypeAttribute[] = [{ id: 123 }];
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing(roleTypeAttributeCollection, ...roleTypeAttributeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const roleTypeAttribute: IRoleTypeAttribute = { id: 123 };
        const roleTypeAttribute2: IRoleTypeAttribute = { id: 456 };
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing([], roleTypeAttribute, roleTypeAttribute2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleTypeAttribute);
        expect(expectedResult).toContain(roleTypeAttribute2);
      });

      it('should accept null and undefined values', () => {
        const roleTypeAttribute: IRoleTypeAttribute = { id: 123 };
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing([], null, roleTypeAttribute, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleTypeAttribute);
      });

      it('should return initial array if no RoleTypeAttribute is added', () => {
        const roleTypeAttributeCollection: IRoleTypeAttribute[] = [{ id: 123 }];
        expectedResult = service.addRoleTypeAttributeToCollectionIfMissing(roleTypeAttributeCollection, undefined, null);
        expect(expectedResult).toEqual(roleTypeAttributeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
