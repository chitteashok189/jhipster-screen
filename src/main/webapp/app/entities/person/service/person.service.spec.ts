import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPerson, Person } from '../person.model';

import { PersonService } from './person.service';

describe('Person Service', () => {
  let service: PersonService;
  let httpMock: HttpTestingController;
  let elemDefault: IPerson;
  let expectedResult: IPerson | IPerson[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      salutation: 'AAAAAAA',
      firstName: 'AAAAAAA',
      middleName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      personalTitle: 'AAAAAAA',
      suffix: 'AAAAAAA',
      nickName: 'AAAAAAA',
      firstNameLocal: 'AAAAAAA',
      middleNameLocal: 'AAAAAAA',
      lastNameLocal: 'AAAAAAA',
      otherLocal: 'AAAAAAA',
      gender: 'AAAAAAA',
      birthDate: currentDate,
      heigth: 0,
      weight: 0,
      mothersMaidenName: 'AAAAAAA',
      maritialStatus: 'AAAAAAA',
      socialSecurityNo: 0,
      passportNo: 'AAAAAAA',
      passportExpiryDate: 'AAAAAAA',
      totalYearsWorkExperience: 0,
      comments: 'AAAAAAA',
      occupation: 'AAAAAAA',
      yearswithEmployer: 0,
      monthsWithEmployer: 0,
      existingCustomer: 0,
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
          birthDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a Person', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          birthDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Person()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Person', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          salutation: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          personalTitle: 'BBBBBB',
          suffix: 'BBBBBB',
          nickName: 'BBBBBB',
          firstNameLocal: 'BBBBBB',
          middleNameLocal: 'BBBBBB',
          lastNameLocal: 'BBBBBB',
          otherLocal: 'BBBBBB',
          gender: 'BBBBBB',
          birthDate: currentDate.format(DATE_TIME_FORMAT),
          heigth: 1,
          weight: 1,
          mothersMaidenName: 'BBBBBB',
          maritialStatus: 'BBBBBB',
          socialSecurityNo: 1,
          passportNo: 'BBBBBB',
          passportExpiryDate: 'BBBBBB',
          totalYearsWorkExperience: 1,
          comments: 'BBBBBB',
          occupation: 'BBBBBB',
          yearswithEmployer: 1,
          monthsWithEmployer: 1,
          existingCustomer: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
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

    it('should partial update a Person', () => {
      const patchObject = Object.assign(
        {
          salutation: 'BBBBBB',
          middleName: 'BBBBBB',
          personalTitle: 'BBBBBB',
          suffix: 'BBBBBB',
          firstNameLocal: 'BBBBBB',
          middleNameLocal: 'BBBBBB',
          otherLocal: 'BBBBBB',
          heigth: 1,
          mothersMaidenName: 'BBBBBB',
          socialSecurityNo: 1,
          totalYearsWorkExperience: 1,
          occupation: 'BBBBBB',
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Person()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          birthDate: currentDate,
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

    it('should return a list of Person', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          salutation: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          personalTitle: 'BBBBBB',
          suffix: 'BBBBBB',
          nickName: 'BBBBBB',
          firstNameLocal: 'BBBBBB',
          middleNameLocal: 'BBBBBB',
          lastNameLocal: 'BBBBBB',
          otherLocal: 'BBBBBB',
          gender: 'BBBBBB',
          birthDate: currentDate.format(DATE_TIME_FORMAT),
          heigth: 1,
          weight: 1,
          mothersMaidenName: 'BBBBBB',
          maritialStatus: 'BBBBBB',
          socialSecurityNo: 1,
          passportNo: 'BBBBBB',
          passportExpiryDate: 'BBBBBB',
          totalYearsWorkExperience: 1,
          comments: 'BBBBBB',
          occupation: 'BBBBBB',
          yearswithEmployer: 1,
          monthsWithEmployer: 1,
          existingCustomer: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
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

    it('should delete a Person', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPersonToCollectionIfMissing', () => {
      it('should add a Person to an empty array', () => {
        const person: IPerson = { id: 123 };
        expectedResult = service.addPersonToCollectionIfMissing([], person);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(person);
      });

      it('should not add a Person to an array that contains it', () => {
        const person: IPerson = { id: 123 };
        const personCollection: IPerson[] = [
          {
            ...person,
          },
          { id: 456 },
        ];
        expectedResult = service.addPersonToCollectionIfMissing(personCollection, person);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Person to an array that doesn't contain it", () => {
        const person: IPerson = { id: 123 };
        const personCollection: IPerson[] = [{ id: 456 }];
        expectedResult = service.addPersonToCollectionIfMissing(personCollection, person);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(person);
      });

      it('should add only unique Person to an array', () => {
        const personArray: IPerson[] = [{ id: 123 }, { id: 456 }, { id: 36639 }];
        const personCollection: IPerson[] = [{ id: 123 }];
        expectedResult = service.addPersonToCollectionIfMissing(personCollection, ...personArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const person: IPerson = { id: 123 };
        const person2: IPerson = { id: 456 };
        expectedResult = service.addPersonToCollectionIfMissing([], person, person2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(person);
        expect(expectedResult).toContain(person2);
      });

      it('should accept null and undefined values', () => {
        const person: IPerson = { id: 123 };
        expectedResult = service.addPersonToCollectionIfMissing([], null, person, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(person);
      });

      it('should return initial array if no Person is added', () => {
        const personCollection: IPerson[] = [{ id: 123 }];
        expectedResult = service.addPersonToCollectionIfMissing(personCollection, undefined, null);
        expect(expectedResult).toEqual(personCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
