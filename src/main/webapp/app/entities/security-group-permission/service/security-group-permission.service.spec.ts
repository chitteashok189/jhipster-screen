import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISecurityGroupPermission, SecurityGroupPermission } from '../security-group-permission.model';

import { SecurityGroupPermissionService } from './security-group-permission.service';

describe('SecurityGroupPermission Service', () => {
  let service: SecurityGroupPermissionService;
  let httpMock: HttpTestingController;
  let elemDefault: ISecurityGroupPermission;
  let expectedResult: ISecurityGroupPermission | ISecurityGroupPermission[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecurityGroupPermissionService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
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

    it('should create a SecurityGroupPermission', () => {
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

      service.create(new SecurityGroupPermission()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecurityGroupPermission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
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

    it('should partial update a SecurityGroupPermission', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          updatedBy: 1,
        },
        new SecurityGroupPermission()
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

    it('should return a list of SecurityGroupPermission', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
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

    it('should delete a SecurityGroupPermission', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSecurityGroupPermissionToCollectionIfMissing', () => {
      it('should add a SecurityGroupPermission to an empty array', () => {
        const securityGroupPermission: ISecurityGroupPermission = { id: 123 };
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing([], securityGroupPermission);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityGroupPermission);
      });

      it('should not add a SecurityGroupPermission to an array that contains it', () => {
        const securityGroupPermission: ISecurityGroupPermission = { id: 123 };
        const securityGroupPermissionCollection: ISecurityGroupPermission[] = [
          {
            ...securityGroupPermission,
          },
          { id: 456 },
        ];
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing(
          securityGroupPermissionCollection,
          securityGroupPermission
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecurityGroupPermission to an array that doesn't contain it", () => {
        const securityGroupPermission: ISecurityGroupPermission = { id: 123 };
        const securityGroupPermissionCollection: ISecurityGroupPermission[] = [{ id: 456 }];
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing(
          securityGroupPermissionCollection,
          securityGroupPermission
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityGroupPermission);
      });

      it('should add only unique SecurityGroupPermission to an array', () => {
        const securityGroupPermissionArray: ISecurityGroupPermission[] = [{ id: 123 }, { id: 456 }, { id: 48096 }];
        const securityGroupPermissionCollection: ISecurityGroupPermission[] = [{ id: 123 }];
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing(
          securityGroupPermissionCollection,
          ...securityGroupPermissionArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securityGroupPermission: ISecurityGroupPermission = { id: 123 };
        const securityGroupPermission2: ISecurityGroupPermission = { id: 456 };
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing([], securityGroupPermission, securityGroupPermission2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityGroupPermission);
        expect(expectedResult).toContain(securityGroupPermission2);
      });

      it('should accept null and undefined values', () => {
        const securityGroupPermission: ISecurityGroupPermission = { id: 123 };
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing([], null, securityGroupPermission, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityGroupPermission);
      });

      it('should return initial array if no SecurityGroupPermission is added', () => {
        const securityGroupPermissionCollection: ISecurityGroupPermission[] = [{ id: 123 }];
        expectedResult = service.addSecurityGroupPermissionToCollectionIfMissing(securityGroupPermissionCollection, undefined, null);
        expect(expectedResult).toEqual(securityGroupPermissionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
