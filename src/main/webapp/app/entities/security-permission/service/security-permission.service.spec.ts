import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISecurityPermission, SecurityPermission } from '../security-permission.model';

import { SecurityPermissionService } from './security-permission.service';

describe('SecurityPermission Service', () => {
  let service: SecurityPermissionService;
  let httpMock: HttpTestingController;
  let elemDefault: ISecurityPermission;
  let expectedResult: ISecurityPermission | ISecurityPermission[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecurityPermissionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
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

    it('should create a SecurityPermission', () => {
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

      service.create(new SecurityPermission()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecurityPermission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
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

    it('should partial update a SecurityPermission', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          description: 'BBBBBB',
          createdBy: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new SecurityPermission()
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

    it('should return a list of SecurityPermission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
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

    it('should delete a SecurityPermission', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSecurityPermissionToCollectionIfMissing', () => {
      it('should add a SecurityPermission to an empty array', () => {
        const securityPermission: ISecurityPermission = { id: 123 };
        expectedResult = service.addSecurityPermissionToCollectionIfMissing([], securityPermission);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityPermission);
      });

      it('should not add a SecurityPermission to an array that contains it', () => {
        const securityPermission: ISecurityPermission = { id: 123 };
        const securityPermissionCollection: ISecurityPermission[] = [
          {
            ...securityPermission,
          },
          { id: 456 },
        ];
        expectedResult = service.addSecurityPermissionToCollectionIfMissing(securityPermissionCollection, securityPermission);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecurityPermission to an array that doesn't contain it", () => {
        const securityPermission: ISecurityPermission = { id: 123 };
        const securityPermissionCollection: ISecurityPermission[] = [{ id: 456 }];
        expectedResult = service.addSecurityPermissionToCollectionIfMissing(securityPermissionCollection, securityPermission);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityPermission);
      });

      it('should add only unique SecurityPermission to an array', () => {
        const securityPermissionArray: ISecurityPermission[] = [{ id: 123 }, { id: 456 }, { id: 64514 }];
        const securityPermissionCollection: ISecurityPermission[] = [{ id: 123 }];
        expectedResult = service.addSecurityPermissionToCollectionIfMissing(securityPermissionCollection, ...securityPermissionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securityPermission: ISecurityPermission = { id: 123 };
        const securityPermission2: ISecurityPermission = { id: 456 };
        expectedResult = service.addSecurityPermissionToCollectionIfMissing([], securityPermission, securityPermission2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityPermission);
        expect(expectedResult).toContain(securityPermission2);
      });

      it('should accept null and undefined values', () => {
        const securityPermission: ISecurityPermission = { id: 123 };
        expectedResult = service.addSecurityPermissionToCollectionIfMissing([], null, securityPermission, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityPermission);
      });

      it('should return initial array if no SecurityPermission is added', () => {
        const securityPermissionCollection: ISecurityPermission[] = [{ id: 123 }];
        expectedResult = service.addSecurityPermissionToCollectionIfMissing(securityPermissionCollection, undefined, null);
        expect(expectedResult).toEqual(securityPermissionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
