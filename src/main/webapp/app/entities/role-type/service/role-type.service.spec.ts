import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRoleType, RoleType } from '../role-type.model';

import { RoleTypeService } from './role-type.service';

describe('RoleType Service', () => {
  let service: RoleTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IRoleType;
  let expectedResult: IRoleType | IRoleType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RoleTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      hasTable: false,
      description: 'AAAAAAA',
      childRoleType: 0,
      validPartyRelationshipType: 0,
      validFromPartyRelationshipType: 0,
      partyInvitationRoleAssociation: 'AAAAAAA',
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

    it('should create a RoleType', () => {
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

      service.create(new RoleType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RoleType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          hasTable: true,
          description: 'BBBBBB',
          childRoleType: 1,
          validPartyRelationshipType: 1,
          validFromPartyRelationshipType: 1,
          partyInvitationRoleAssociation: 'BBBBBB',
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

    it('should partial update a RoleType', () => {
      const patchObject = Object.assign(
        {
          hasTable: true,
          validPartyRelationshipType: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new RoleType()
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

    it('should return a list of RoleType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          hasTable: true,
          description: 'BBBBBB',
          childRoleType: 1,
          validPartyRelationshipType: 1,
          validFromPartyRelationshipType: 1,
          partyInvitationRoleAssociation: 'BBBBBB',
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

    it('should delete a RoleType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRoleTypeToCollectionIfMissing', () => {
      it('should add a RoleType to an empty array', () => {
        const roleType: IRoleType = { id: 123 };
        expectedResult = service.addRoleTypeToCollectionIfMissing([], roleType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleType);
      });

      it('should not add a RoleType to an array that contains it', () => {
        const roleType: IRoleType = { id: 123 };
        const roleTypeCollection: IRoleType[] = [
          {
            ...roleType,
          },
          { id: 456 },
        ];
        expectedResult = service.addRoleTypeToCollectionIfMissing(roleTypeCollection, roleType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RoleType to an array that doesn't contain it", () => {
        const roleType: IRoleType = { id: 123 };
        const roleTypeCollection: IRoleType[] = [{ id: 456 }];
        expectedResult = service.addRoleTypeToCollectionIfMissing(roleTypeCollection, roleType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleType);
      });

      it('should add only unique RoleType to an array', () => {
        const roleTypeArray: IRoleType[] = [{ id: 123 }, { id: 456 }, { id: 87342 }];
        const roleTypeCollection: IRoleType[] = [{ id: 123 }];
        expectedResult = service.addRoleTypeToCollectionIfMissing(roleTypeCollection, ...roleTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const roleType: IRoleType = { id: 123 };
        const roleType2: IRoleType = { id: 456 };
        expectedResult = service.addRoleTypeToCollectionIfMissing([], roleType, roleType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(roleType);
        expect(expectedResult).toContain(roleType2);
      });

      it('should accept null and undefined values', () => {
        const roleType: IRoleType = { id: 123 };
        expectedResult = service.addRoleTypeToCollectionIfMissing([], null, roleType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(roleType);
      });

      it('should return initial array if no RoleType is added', () => {
        const roleTypeCollection: IRoleType[] = [{ id: 123 }];
        expectedResult = service.addRoleTypeToCollectionIfMissing(roleTypeCollection, undefined, null);
        expect(expectedResult).toEqual(roleTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
