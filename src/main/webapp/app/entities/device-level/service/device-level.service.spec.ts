import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDeviceLevel, DeviceLevel } from '../device-level.model';

import { DeviceLevelService } from './device-level.service';

describe('DeviceLevel Service', () => {
  let service: DeviceLevelService;
  let httpMock: HttpTestingController;
  let elemDefault: IDeviceLevel;
  let expectedResult: IDeviceLevel | IDeviceLevel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeviceLevelService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      levelName: 'AAAAAAA',
      deviceLevelType: 0,
      manufacturer: 'AAAAAAA',
      productCode: 0,
      ports: 0,
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

    it('should create a DeviceLevel', () => {
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

      service.create(new DeviceLevel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DeviceLevel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          levelName: 'BBBBBB',
          deviceLevelType: 1,
          manufacturer: 'BBBBBB',
          productCode: 1,
          ports: 1,
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

    it('should partial update a DeviceLevel', () => {
      const patchObject = Object.assign(
        {
          productCode: 1,
          description: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new DeviceLevel()
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

    it('should return a list of DeviceLevel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          levelName: 'BBBBBB',
          deviceLevelType: 1,
          manufacturer: 'BBBBBB',
          productCode: 1,
          ports: 1,
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

    it('should delete a DeviceLevel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDeviceLevelToCollectionIfMissing', () => {
      it('should add a DeviceLevel to an empty array', () => {
        const deviceLevel: IDeviceLevel = { id: 123 };
        expectedResult = service.addDeviceLevelToCollectionIfMissing([], deviceLevel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deviceLevel);
      });

      it('should not add a DeviceLevel to an array that contains it', () => {
        const deviceLevel: IDeviceLevel = { id: 123 };
        const deviceLevelCollection: IDeviceLevel[] = [
          {
            ...deviceLevel,
          },
          { id: 456 },
        ];
        expectedResult = service.addDeviceLevelToCollectionIfMissing(deviceLevelCollection, deviceLevel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DeviceLevel to an array that doesn't contain it", () => {
        const deviceLevel: IDeviceLevel = { id: 123 };
        const deviceLevelCollection: IDeviceLevel[] = [{ id: 456 }];
        expectedResult = service.addDeviceLevelToCollectionIfMissing(deviceLevelCollection, deviceLevel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deviceLevel);
      });

      it('should add only unique DeviceLevel to an array', () => {
        const deviceLevelArray: IDeviceLevel[] = [{ id: 123 }, { id: 456 }, { id: 63835 }];
        const deviceLevelCollection: IDeviceLevel[] = [{ id: 123 }];
        expectedResult = service.addDeviceLevelToCollectionIfMissing(deviceLevelCollection, ...deviceLevelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const deviceLevel: IDeviceLevel = { id: 123 };
        const deviceLevel2: IDeviceLevel = { id: 456 };
        expectedResult = service.addDeviceLevelToCollectionIfMissing([], deviceLevel, deviceLevel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deviceLevel);
        expect(expectedResult).toContain(deviceLevel2);
      });

      it('should accept null and undefined values', () => {
        const deviceLevel: IDeviceLevel = { id: 123 };
        expectedResult = service.addDeviceLevelToCollectionIfMissing([], null, deviceLevel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deviceLevel);
      });

      it('should return initial array if no DeviceLevel is added', () => {
        const deviceLevelCollection: IDeviceLevel[] = [{ id: 123 }];
        expectedResult = service.addDeviceLevelToCollectionIfMissing(deviceLevelCollection, undefined, null);
        expect(expectedResult).toEqual(deviceLevelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
