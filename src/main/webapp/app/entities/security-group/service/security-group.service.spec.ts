import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISecurityGroup, SecurityGroup } from '../security-group.model';

import { SecurityGroupService } from './security-group.service';

describe('SecurityGroup Service', () => {
  let service: SecurityGroupService;
  let httpMock: HttpTestingController;
  let elemDefault: ISecurityGroup;
  let expectedResult: ISecurityGroup | ISecurityGroup[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SecurityGroupService);
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

    it('should create a SecurityGroup', () => {
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

      service.create(new SecurityGroup()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecurityGroup', () => {
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

    it('should partial update a SecurityGroup', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new SecurityGroup()
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

    it('should return a list of SecurityGroup', () => {
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

    it('should delete a SecurityGroup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSecurityGroupToCollectionIfMissing', () => {
      it('should add a SecurityGroup to an empty array', () => {
        const securityGroup: ISecurityGroup = { id: 123 };
        expectedResult = service.addSecurityGroupToCollectionIfMissing([], securityGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityGroup);
      });

      it('should not add a SecurityGroup to an array that contains it', () => {
        const securityGroup: ISecurityGroup = { id: 123 };
        const securityGroupCollection: ISecurityGroup[] = [
          {
            ...securityGroup,
          },
          { id: 456 },
        ];
        expectedResult = service.addSecurityGroupToCollectionIfMissing(securityGroupCollection, securityGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecurityGroup to an array that doesn't contain it", () => {
        const securityGroup: ISecurityGroup = { id: 123 };
        const securityGroupCollection: ISecurityGroup[] = [{ id: 456 }];
        expectedResult = service.addSecurityGroupToCollectionIfMissing(securityGroupCollection, securityGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityGroup);
      });

      it('should add only unique SecurityGroup to an array', () => {
        const securityGroupArray: ISecurityGroup[] = [{ id: 123 }, { id: 456 }, { id: 63167 }];
        const securityGroupCollection: ISecurityGroup[] = [{ id: 123 }];
        expectedResult = service.addSecurityGroupToCollectionIfMissing(securityGroupCollection, ...securityGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const securityGroup: ISecurityGroup = { id: 123 };
        const securityGroup2: ISecurityGroup = { id: 456 };
        expectedResult = service.addSecurityGroupToCollectionIfMissing([], securityGroup, securityGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(securityGroup);
        expect(expectedResult).toContain(securityGroup2);
      });

      it('should accept null and undefined values', () => {
        const securityGroup: ISecurityGroup = { id: 123 };
        expectedResult = service.addSecurityGroupToCollectionIfMissing([], null, securityGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(securityGroup);
      });

      it('should return initial array if no SecurityGroup is added', () => {
        const securityGroupCollection: ISecurityGroup[] = [{ id: 123 }];
        expectedResult = service.addSecurityGroupToCollectionIfMissing(securityGroupCollection, undefined, null);
        expect(expectedResult).toEqual(securityGroupCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
