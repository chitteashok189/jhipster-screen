import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApplicationUser, ApplicationUser } from '../application-user.model';

import { ApplicationUserService } from './application-user.service';

describe('ApplicationUser Service', () => {
  let service: ApplicationUserService;
  let httpMock: HttpTestingController;
  let elemDefault: IApplicationUser;
  let expectedResult: IApplicationUser | IApplicationUser[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApplicationUserService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      currentPassword: 'AAAAAAA',
      passwordHint: 'AAAAAAA',
      isSystemEnables: false,
      hasLoggedOut: false,
      requirePasswordChange: false,
      lastCurrencyUom: 0,
      lastLocale: 0,
      lastTimeZone: 0,
      disabledDateTime: currentDate,
      successiveFailedLogins: 0,
      applicationUserSecurityGroup: 'AAAAAAA',
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
          disabledDateTime: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a ApplicationUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          disabledDateTime: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          disabledDateTime: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new ApplicationUser()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApplicationUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          currentPassword: 'BBBBBB',
          passwordHint: 'BBBBBB',
          isSystemEnables: true,
          hasLoggedOut: true,
          requirePasswordChange: true,
          lastCurrencyUom: 1,
          lastLocale: 1,
          lastTimeZone: 1,
          disabledDateTime: currentDate.format(DATE_TIME_FORMAT),
          successiveFailedLogins: 1,
          applicationUserSecurityGroup: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          disabledDateTime: currentDate,
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

    it('should partial update a ApplicationUser', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          currentPassword: 'BBBBBB',
          isSystemEnables: true,
          requirePasswordChange: true,
          lastCurrencyUom: 1,
          lastLocale: 1,
          lastTimeZone: 1,
          applicationUserSecurityGroup: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new ApplicationUser()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          disabledDateTime: currentDate,
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

    it('should return a list of ApplicationUser', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          currentPassword: 'BBBBBB',
          passwordHint: 'BBBBBB',
          isSystemEnables: true,
          hasLoggedOut: true,
          requirePasswordChange: true,
          lastCurrencyUom: 1,
          lastLocale: 1,
          lastTimeZone: 1,
          disabledDateTime: currentDate.format(DATE_TIME_FORMAT),
          successiveFailedLogins: 1,
          applicationUserSecurityGroup: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          disabledDateTime: currentDate,
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

    it('should delete a ApplicationUser', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addApplicationUserToCollectionIfMissing', () => {
      it('should add a ApplicationUser to an empty array', () => {
        const applicationUser: IApplicationUser = { id: 123 };
        expectedResult = service.addApplicationUserToCollectionIfMissing([], applicationUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationUser);
      });

      it('should not add a ApplicationUser to an array that contains it', () => {
        const applicationUser: IApplicationUser = { id: 123 };
        const applicationUserCollection: IApplicationUser[] = [
          {
            ...applicationUser,
          },
          { id: 456 },
        ];
        expectedResult = service.addApplicationUserToCollectionIfMissing(applicationUserCollection, applicationUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApplicationUser to an array that doesn't contain it", () => {
        const applicationUser: IApplicationUser = { id: 123 };
        const applicationUserCollection: IApplicationUser[] = [{ id: 456 }];
        expectedResult = service.addApplicationUserToCollectionIfMissing(applicationUserCollection, applicationUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationUser);
      });

      it('should add only unique ApplicationUser to an array', () => {
        const applicationUserArray: IApplicationUser[] = [{ id: 123 }, { id: 456 }, { id: 13672 }];
        const applicationUserCollection: IApplicationUser[] = [{ id: 123 }];
        expectedResult = service.addApplicationUserToCollectionIfMissing(applicationUserCollection, ...applicationUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const applicationUser: IApplicationUser = { id: 123 };
        const applicationUser2: IApplicationUser = { id: 456 };
        expectedResult = service.addApplicationUserToCollectionIfMissing([], applicationUser, applicationUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationUser);
        expect(expectedResult).toContain(applicationUser2);
      });

      it('should accept null and undefined values', () => {
        const applicationUser: IApplicationUser = { id: 123 };
        expectedResult = service.addApplicationUserToCollectionIfMissing([], null, applicationUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationUser);
      });

      it('should return initial array if no ApplicationUser is added', () => {
        const applicationUserCollection: IApplicationUser[] = [{ id: 123 }];
        expectedResult = service.addApplicationUserToCollectionIfMissing(applicationUserCollection, undefined, null);
        expect(expectedResult).toEqual(applicationUserCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
