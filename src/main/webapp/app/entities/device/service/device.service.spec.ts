import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DeviceType } from 'app/entities/enumerations/device-type.model';
import { IDevice, Device } from '../device.model';

import { DeviceService } from './device.service';

describe('Device Service', () => {
  let service: DeviceService;
  let httpMock: HttpTestingController;
  let elemDefault: IDevice;
  let expectedResult: IDevice | IDevice[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeviceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      deviceModel: 'AAAAAAA',
      deviceAsset: 'AAAAAAA',
      deviceType: DeviceType.Input,
      hardwareID: 0,
      reportingIntervalLocation: 0,
      parentDevice: 'AAAAAAA',
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

    it('should create a Device', () => {
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

      service.create(new Device()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Device', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          deviceModel: 'BBBBBB',
          deviceAsset: 'BBBBBB',
          deviceType: 'BBBBBB',
          hardwareID: 1,
          reportingIntervalLocation: 1,
          parentDevice: 'BBBBBB',
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

    it('should partial update a Device', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          hardwareID: 1,
          reportingIntervalLocation: 1,
          properties: 'BBBBBB',
          description: 'BBBBBB',
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Device()
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

    it('should return a list of Device', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          deviceModel: 'BBBBBB',
          deviceAsset: 'BBBBBB',
          deviceType: 'BBBBBB',
          hardwareID: 1,
          reportingIntervalLocation: 1,
          parentDevice: 'BBBBBB',
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

    it('should delete a Device', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDeviceToCollectionIfMissing', () => {
      it('should add a Device to an empty array', () => {
        const device: IDevice = { id: 123 };
        expectedResult = service.addDeviceToCollectionIfMissing([], device);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(device);
      });

      it('should not add a Device to an array that contains it', () => {
        const device: IDevice = { id: 123 };
        const deviceCollection: IDevice[] = [
          {
            ...device,
          },
          { id: 456 },
        ];
        expectedResult = service.addDeviceToCollectionIfMissing(deviceCollection, device);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Device to an array that doesn't contain it", () => {
        const device: IDevice = { id: 123 };
        const deviceCollection: IDevice[] = [{ id: 456 }];
        expectedResult = service.addDeviceToCollectionIfMissing(deviceCollection, device);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(device);
      });

      it('should add only unique Device to an array', () => {
        const deviceArray: IDevice[] = [{ id: 123 }, { id: 456 }, { id: 24835 }];
        const deviceCollection: IDevice[] = [{ id: 123 }];
        expectedResult = service.addDeviceToCollectionIfMissing(deviceCollection, ...deviceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const device: IDevice = { id: 123 };
        const device2: IDevice = { id: 456 };
        expectedResult = service.addDeviceToCollectionIfMissing([], device, device2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(device);
        expect(expectedResult).toContain(device2);
      });

      it('should accept null and undefined values', () => {
        const device: IDevice = { id: 123 };
        expectedResult = service.addDeviceToCollectionIfMissing([], null, device, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(device);
      });

      it('should return initial array if no Device is added', () => {
        const deviceCollection: IDevice[] = [{ id: 123 }];
        expectedResult = service.addDeviceToCollectionIfMissing(deviceCollection, undefined, null);
        expect(expectedResult).toEqual(deviceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
