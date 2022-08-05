import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInspectionRecord, InspectionRecord } from '../inspection-record.model';

import { InspectionRecordService } from './inspection-record.service';

describe('InspectionRecord Service', () => {
  let service: InspectionRecordService;
  let httpMock: HttpTestingController;
  let elemDefault: IInspectionRecord;
  let expectedResult: IInspectionRecord | IInspectionRecord[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InspectionRecordService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      rawMaterialBatchNo: 0,
      productBatchNo: 0,
      rawMaterialBatchCode: 0,
      inputBatchNo: 0,
      inputBatchCode: 0,
      attachmentsContentType: 'image/png',
      attachments: 'AAAAAAA',
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

    it('should create a InspectionRecord', () => {
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

      service.create(new InspectionRecord()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InspectionRecord', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          rawMaterialBatchNo: 1,
          productBatchNo: 1,
          rawMaterialBatchCode: 1,
          inputBatchNo: 1,
          inputBatchCode: 1,
          attachments: 'BBBBBB',
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

    it('should partial update a InspectionRecord', () => {
      const patchObject = Object.assign(
        {
          productBatchNo: 1,
          rawMaterialBatchCode: 1,
          inputBatchCode: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new InspectionRecord()
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

    it('should return a list of InspectionRecord', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          rawMaterialBatchNo: 1,
          productBatchNo: 1,
          rawMaterialBatchCode: 1,
          inputBatchNo: 1,
          inputBatchCode: 1,
          attachments: 'BBBBBB',
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

    it('should delete a InspectionRecord', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInspectionRecordToCollectionIfMissing', () => {
      it('should add a InspectionRecord to an empty array', () => {
        const inspectionRecord: IInspectionRecord = { id: 123 };
        expectedResult = service.addInspectionRecordToCollectionIfMissing([], inspectionRecord);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inspectionRecord);
      });

      it('should not add a InspectionRecord to an array that contains it', () => {
        const inspectionRecord: IInspectionRecord = { id: 123 };
        const inspectionRecordCollection: IInspectionRecord[] = [
          {
            ...inspectionRecord,
          },
          { id: 456 },
        ];
        expectedResult = service.addInspectionRecordToCollectionIfMissing(inspectionRecordCollection, inspectionRecord);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InspectionRecord to an array that doesn't contain it", () => {
        const inspectionRecord: IInspectionRecord = { id: 123 };
        const inspectionRecordCollection: IInspectionRecord[] = [{ id: 456 }];
        expectedResult = service.addInspectionRecordToCollectionIfMissing(inspectionRecordCollection, inspectionRecord);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inspectionRecord);
      });

      it('should add only unique InspectionRecord to an array', () => {
        const inspectionRecordArray: IInspectionRecord[] = [{ id: 123 }, { id: 456 }, { id: 16308 }];
        const inspectionRecordCollection: IInspectionRecord[] = [{ id: 123 }];
        expectedResult = service.addInspectionRecordToCollectionIfMissing(inspectionRecordCollection, ...inspectionRecordArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inspectionRecord: IInspectionRecord = { id: 123 };
        const inspectionRecord2: IInspectionRecord = { id: 456 };
        expectedResult = service.addInspectionRecordToCollectionIfMissing([], inspectionRecord, inspectionRecord2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inspectionRecord);
        expect(expectedResult).toContain(inspectionRecord2);
      });

      it('should accept null and undefined values', () => {
        const inspectionRecord: IInspectionRecord = { id: 123 };
        expectedResult = service.addInspectionRecordToCollectionIfMissing([], null, inspectionRecord, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inspectionRecord);
      });

      it('should return initial array if no InspectionRecord is added', () => {
        const inspectionRecordCollection: IInspectionRecord[] = [{ id: 123 }];
        expectedResult = service.addInspectionRecordToCollectionIfMissing(inspectionRecordCollection, undefined, null);
        expect(expectedResult).toEqual(inspectionRecordCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
