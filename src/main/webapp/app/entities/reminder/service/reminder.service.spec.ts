import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { RemItem } from 'app/entities/enumerations/rem-item.model';
import { IReminder, Reminder } from '../reminder.model';

import { ReminderService } from './reminder.service';

describe('Reminder Service', () => {
  let service: ReminderService;
  let httpMock: HttpTestingController;
  let elemDefault: IReminder;
  let expectedResult: IReminder | IReminder[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReminderService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      name: 'AAAAAAA',
      date: currentDate,
      time: 0,
      item: RemItem.Pest,
      description: 0,
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
          date: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a Reminder', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Reminder()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Reminder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          name: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          time: 1,
          item: 'BBBBBB',
          description: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
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

    it('should partial update a Reminder', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          time: 1,
          description: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new Reminder()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
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

    it('should return a list of Reminder', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          name: 'BBBBBB',
          date: currentDate.format(DATE_TIME_FORMAT),
          time: 1,
          item: 'BBBBBB',
          description: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
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

    it('should delete a Reminder', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReminderToCollectionIfMissing', () => {
      it('should add a Reminder to an empty array', () => {
        const reminder: IReminder = { id: 123 };
        expectedResult = service.addReminderToCollectionIfMissing([], reminder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reminder);
      });

      it('should not add a Reminder to an array that contains it', () => {
        const reminder: IReminder = { id: 123 };
        const reminderCollection: IReminder[] = [
          {
            ...reminder,
          },
          { id: 456 },
        ];
        expectedResult = service.addReminderToCollectionIfMissing(reminderCollection, reminder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Reminder to an array that doesn't contain it", () => {
        const reminder: IReminder = { id: 123 };
        const reminderCollection: IReminder[] = [{ id: 456 }];
        expectedResult = service.addReminderToCollectionIfMissing(reminderCollection, reminder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reminder);
      });

      it('should add only unique Reminder to an array', () => {
        const reminderArray: IReminder[] = [{ id: 123 }, { id: 456 }, { id: 90282 }];
        const reminderCollection: IReminder[] = [{ id: 123 }];
        expectedResult = service.addReminderToCollectionIfMissing(reminderCollection, ...reminderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reminder: IReminder = { id: 123 };
        const reminder2: IReminder = { id: 456 };
        expectedResult = service.addReminderToCollectionIfMissing([], reminder, reminder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reminder);
        expect(expectedResult).toContain(reminder2);
      });

      it('should accept null and undefined values', () => {
        const reminder: IReminder = { id: 123 };
        expectedResult = service.addReminderToCollectionIfMissing([], null, reminder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reminder);
      });

      it('should return initial array if no Reminder is added', () => {
        const reminderCollection: IReminder[] = [{ id: 123 }];
        expectedResult = service.addReminderToCollectionIfMissing(reminderCollection, undefined, null);
        expect(expectedResult).toEqual(reminderCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
