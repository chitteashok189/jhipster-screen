import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDeviceModel, DeviceModel } from '../device-model.model';

import { DeviceModelService } from './device-model.service';

describe('DeviceModel Service', () => {
  let service: DeviceModelService;
  let httpMock: HttpTestingController;
  let elemDefault: IDeviceModel;
  let expectedResult: IDeviceModel | IDeviceModel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeviceModelService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      modelName: 'AAAAAAA',
      type: 0,
      manufacturer: 'AAAAAAA',
      productCode: 0,
      properties: 0,
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

    it('should create a DeviceModel', () => {
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

      service.create(new DeviceModel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DeviceModel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          modelName: 'BBBBBB',
          type: 1,
          manufacturer: 'BBBBBB',
          productCode: 1,
          properties: 1,
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

    it('should partial update a DeviceModel', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new DeviceModel()
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

    it('should return a list of DeviceModel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          modelName: 'BBBBBB',
          type: 1,
          manufacturer: 'BBBBBB',
          productCode: 1,
          properties: 1,
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

    it('should delete a DeviceModel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDeviceModelToCollectionIfMissing', () => {
      it('should add a DeviceModel to an empty array', () => {
        const deviceModel: IDeviceModel = { id: 123 };
        expectedResult = service.addDeviceModelToCollectionIfMissing([], deviceModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deviceModel);
      });

      it('should not add a DeviceModel to an array that contains it', () => {
        const deviceModel: IDeviceModel = { id: 123 };
        const deviceModelCollection: IDeviceModel[] = [
          {
            ...deviceModel,
          },
          { id: 456 },
        ];
        expectedResult = service.addDeviceModelToCollectionIfMissing(deviceModelCollection, deviceModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DeviceModel to an array that doesn't contain it", () => {
        const deviceModel: IDeviceModel = { id: 123 };
        const deviceModelCollection: IDeviceModel[] = [{ id: 456 }];
        expectedResult = service.addDeviceModelToCollectionIfMissing(deviceModelCollection, deviceModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deviceModel);
      });

      it('should add only unique DeviceModel to an array', () => {
        const deviceModelArray: IDeviceModel[] = [{ id: 123 }, { id: 456 }, { id: 70521 }];
        const deviceModelCollection: IDeviceModel[] = [{ id: 123 }];
        expectedResult = service.addDeviceModelToCollectionIfMissing(deviceModelCollection, ...deviceModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const deviceModel: IDeviceModel = { id: 123 };
        const deviceModel2: IDeviceModel = { id: 456 };
        expectedResult = service.addDeviceModelToCollectionIfMissing([], deviceModel, deviceModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deviceModel);
        expect(expectedResult).toContain(deviceModel2);
      });

      it('should accept null and undefined values', () => {
        const deviceModel: IDeviceModel = { id: 123 };
        expectedResult = service.addDeviceModelToCollectionIfMissing([], null, deviceModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deviceModel);
      });

      it('should return initial array if no DeviceModel is added', () => {
        const deviceModelCollection: IDeviceModel[] = [{ id: 123 }];
        expectedResult = service.addDeviceModelToCollectionIfMissing(deviceModelCollection, undefined, null);
        expect(expectedResult).toEqual(deviceModelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
