import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISensorModel, SensorModel } from '../sensor-model.model';

import { SensorModelService } from './sensor-model.service';

describe('SensorModel Service', () => {
  let service: SensorModelService;
  let httpMock: HttpTestingController;
  let elemDefault: ISensorModel;
  let expectedResult: ISensorModel | ISensorModel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SensorModelService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      sensorType: 0,
      manufacturer: 'AAAAAAA',
      productCode: 'AAAAAAA',
      sensorMeasure: 0,
      modelName: 'AAAAAAA',
      properties: 'AAAAAAA',
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

    it('should create a SensorModel', () => {
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

      service.create(new SensorModel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SensorModel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          sensorType: 1,
          manufacturer: 'BBBBBB',
          productCode: 'BBBBBB',
          sensorMeasure: 1,
          modelName: 'BBBBBB',
          properties: 'BBBBBB',
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

    it('should partial update a SensorModel', () => {
      const patchObject = Object.assign(
        {
          sensorType: 1,
          productCode: 'BBBBBB',
          modelName: 'BBBBBB',
          description: 'BBBBBB',
          createdBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new SensorModel()
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

    it('should return a list of SensorModel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          sensorType: 1,
          manufacturer: 'BBBBBB',
          productCode: 'BBBBBB',
          sensorMeasure: 1,
          modelName: 'BBBBBB',
          properties: 'BBBBBB',
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

    it('should delete a SensorModel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSensorModelToCollectionIfMissing', () => {
      it('should add a SensorModel to an empty array', () => {
        const sensorModel: ISensorModel = { id: 123 };
        expectedResult = service.addSensorModelToCollectionIfMissing([], sensorModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sensorModel);
      });

      it('should not add a SensorModel to an array that contains it', () => {
        const sensorModel: ISensorModel = { id: 123 };
        const sensorModelCollection: ISensorModel[] = [
          {
            ...sensorModel,
          },
          { id: 456 },
        ];
        expectedResult = service.addSensorModelToCollectionIfMissing(sensorModelCollection, sensorModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SensorModel to an array that doesn't contain it", () => {
        const sensorModel: ISensorModel = { id: 123 };
        const sensorModelCollection: ISensorModel[] = [{ id: 456 }];
        expectedResult = service.addSensorModelToCollectionIfMissing(sensorModelCollection, sensorModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sensorModel);
      });

      it('should add only unique SensorModel to an array', () => {
        const sensorModelArray: ISensorModel[] = [{ id: 123 }, { id: 456 }, { id: 38326 }];
        const sensorModelCollection: ISensorModel[] = [{ id: 123 }];
        expectedResult = service.addSensorModelToCollectionIfMissing(sensorModelCollection, ...sensorModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sensorModel: ISensorModel = { id: 123 };
        const sensorModel2: ISensorModel = { id: 456 };
        expectedResult = service.addSensorModelToCollectionIfMissing([], sensorModel, sensorModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sensorModel);
        expect(expectedResult).toContain(sensorModel2);
      });

      it('should accept null and undefined values', () => {
        const sensorModel: ISensorModel = { id: 123 };
        expectedResult = service.addSensorModelToCollectionIfMissing([], null, sensorModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sensorModel);
      });

      it('should return initial array if no SensorModel is added', () => {
        const sensorModelCollection: ISensorModel[] = [{ id: 123 }];
        expectedResult = service.addSensorModelToCollectionIfMissing(sensorModelCollection, undefined, null);
        expect(expectedResult).toEqual(sensorModelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
