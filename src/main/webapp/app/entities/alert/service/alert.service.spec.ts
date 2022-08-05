import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { AlertType } from 'app/entities/enumerations/alert-type.model';
import { PreType } from 'app/entities/enumerations/pre-type.model';
import { AlertStatus } from 'app/entities/enumerations/alert-status.model';
import { Remediation } from 'app/entities/enumerations/remediation.model';
import { IAlert, Alert } from '../alert.model';

import { AlertService } from './alert.service';

describe('Alert Service', () => {
  let service: AlertService;
  let httpMock: HttpTestingController;
  let elemDefault: IAlert;
  let expectedResult: IAlert | IAlert[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AlertService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      name: 'AAAAAAA',
      alertType: AlertType.Disease,
      description: 'AAAAAAA',
      datePeriod: 0,
      durationDays: 0,
      minimumTemperature: 0,
      maximumTemperature: 0,
      minHumidity: 0,
      maxHumidity: 0,
      precipitationType: PreType.Rain,
      minPrecipitation: 0,
      maxPrecipitation: 0,
      status: AlertStatus.Active,
      remediation: Remediation.Manual,
      farmAssigned: 'AAAAAAA',
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

    it('should create a Alert', () => {
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

      service.create(new Alert()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Alert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          name: 'BBBBBB',
          alertType: 'BBBBBB',
          description: 'BBBBBB',
          datePeriod: 1,
          durationDays: 1,
          minimumTemperature: 1,
          maximumTemperature: 1,
          minHumidity: 1,
          maxHumidity: 1,
          precipitationType: 'BBBBBB',
          minPrecipitation: 1,
          maxPrecipitation: 1,
          status: 'BBBBBB',
          remediation: 'BBBBBB',
          farmAssigned: 'BBBBBB',
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

    it('should partial update a Alert', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          datePeriod: 1,
          durationDays: 1,
          precipitationType: 'BBBBBB',
          minPrecipitation: 1,
          maxPrecipitation: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Alert()
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

    it('should return a list of Alert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          name: 'BBBBBB',
          alertType: 'BBBBBB',
          description: 'BBBBBB',
          datePeriod: 1,
          durationDays: 1,
          minimumTemperature: 1,
          maximumTemperature: 1,
          minHumidity: 1,
          maxHumidity: 1,
          precipitationType: 'BBBBBB',
          minPrecipitation: 1,
          maxPrecipitation: 1,
          status: 'BBBBBB',
          remediation: 'BBBBBB',
          farmAssigned: 'BBBBBB',
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

    it('should delete a Alert', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAlertToCollectionIfMissing', () => {
      it('should add a Alert to an empty array', () => {
        const alert: IAlert = { id: 123 };
        expectedResult = service.addAlertToCollectionIfMissing([], alert);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alert);
      });

      it('should not add a Alert to an array that contains it', () => {
        const alert: IAlert = { id: 123 };
        const alertCollection: IAlert[] = [
          {
            ...alert,
          },
          { id: 456 },
        ];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, alert);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Alert to an array that doesn't contain it", () => {
        const alert: IAlert = { id: 123 };
        const alertCollection: IAlert[] = [{ id: 456 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, alert);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alert);
      });

      it('should add only unique Alert to an array', () => {
        const alertArray: IAlert[] = [{ id: 123 }, { id: 456 }, { id: 95528 }];
        const alertCollection: IAlert[] = [{ id: 123 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, ...alertArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const alert: IAlert = { id: 123 };
        const alert2: IAlert = { id: 456 };
        expectedResult = service.addAlertToCollectionIfMissing([], alert, alert2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alert);
        expect(expectedResult).toContain(alert2);
      });

      it('should accept null and undefined values', () => {
        const alert: IAlert = { id: 123 };
        expectedResult = service.addAlertToCollectionIfMissing([], null, alert, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alert);
      });

      it('should return initial array if no Alert is added', () => {
        const alertCollection: IAlert[] = [{ id: 123 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, undefined, null);
        expect(expectedResult).toEqual(alertCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
