import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApplicationUserSecurityGroup, ApplicationUserSecurityGroup } from '../application-user-security-group.model';

import { ApplicationUserSecurityGroupService } from './application-user-security-group.service';

describe('ApplicationUserSecurityGroup Service', () => {
  let service: ApplicationUserSecurityGroupService;
  let httpMock: HttpTestingController;
  let elemDefault: IApplicationUserSecurityGroup;
  let expectedResult: IApplicationUserSecurityGroup | IApplicationUserSecurityGroup[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApplicationUserSecurityGroupService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      fromDate: currentDate,
      thruDate: currentDate,
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
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a ApplicationUserSecurityGroup', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new ApplicationUserSecurityGroup()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApplicationUserSecurityGroup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should partial update a ApplicationUserSecurityGroup', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new ApplicationUserSecurityGroup()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should return a list of ApplicationUserSecurityGroup', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          fromDate: currentDate.format(DATE_TIME_FORMAT),
          thruDate: currentDate.format(DATE_TIME_FORMAT),
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fromDate: currentDate,
          thruDate: currentDate,
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

    it('should delete a ApplicationUserSecurityGroup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addApplicationUserSecurityGroupToCollectionIfMissing', () => {
      it('should add a ApplicationUserSecurityGroup to an empty array', () => {
        const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 123 };
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing([], applicationUserSecurityGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationUserSecurityGroup);
      });

      it('should not add a ApplicationUserSecurityGroup to an array that contains it', () => {
        const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 123 };
        const applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[] = [
          {
            ...applicationUserSecurityGroup,
          },
          { id: 456 },
        ];
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing(
          applicationUserSecurityGroupCollection,
          applicationUserSecurityGroup
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApplicationUserSecurityGroup to an array that doesn't contain it", () => {
        const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 123 };
        const applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[] = [{ id: 456 }];
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing(
          applicationUserSecurityGroupCollection,
          applicationUserSecurityGroup
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationUserSecurityGroup);
      });

      it('should add only unique ApplicationUserSecurityGroup to an array', () => {
        const applicationUserSecurityGroupArray: IApplicationUserSecurityGroup[] = [{ id: 123 }, { id: 456 }, { id: 87659 }];
        const applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[] = [{ id: 123 }];
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing(
          applicationUserSecurityGroupCollection,
          ...applicationUserSecurityGroupArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 123 };
        const applicationUserSecurityGroup2: IApplicationUserSecurityGroup = { id: 456 };
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing(
          [],
          applicationUserSecurityGroup,
          applicationUserSecurityGroup2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationUserSecurityGroup);
        expect(expectedResult).toContain(applicationUserSecurityGroup2);
      });

      it('should accept null and undefined values', () => {
        const applicationUserSecurityGroup: IApplicationUserSecurityGroup = { id: 123 };
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing([], null, applicationUserSecurityGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationUserSecurityGroup);
      });

      it('should return initial array if no ApplicationUserSecurityGroup is added', () => {
        const applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[] = [{ id: 123 }];
        expectedResult = service.addApplicationUserSecurityGroupToCollectionIfMissing(
          applicationUserSecurityGroupCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(applicationUserSecurityGroupCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
