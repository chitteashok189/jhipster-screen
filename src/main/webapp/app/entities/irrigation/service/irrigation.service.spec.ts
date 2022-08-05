import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IrriSource } from 'app/entities/enumerations/irri-source.model';
import { IIrrigation, Irrigation } from '../irrigation.model';

import { IrrigationService } from './irrigation.service';

describe('Irrigation Service', () => {
  let service: IrrigationService;
  let httpMock: HttpTestingController;
  let elemDefault: IIrrigation;
  let expectedResult: IIrrigation | IIrrigation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IrrigationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      source: IrriSource.Automatic,
      nutrientLevel: 0,
      solarRadiation: 0,
      inletFlow: 0,
      outletFlow: 0,
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

    it('should create a Irrigation', () => {
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

      service.create(new Irrigation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Irrigation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          nutrientLevel: 1,
          solarRadiation: 1,
          inletFlow: 1,
          outletFlow: 1,
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

    it('should partial update a Irrigation', () => {
      const patchObject = Object.assign(
        {
          solarRadiation: 1,
          inletFlow: 1,
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Irrigation()
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

    it('should return a list of Irrigation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          nutrientLevel: 1,
          solarRadiation: 1,
          inletFlow: 1,
          outletFlow: 1,
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

    it('should delete a Irrigation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIrrigationToCollectionIfMissing', () => {
      it('should add a Irrigation to an empty array', () => {
        const irrigation: IIrrigation = { id: 123 };
        expectedResult = service.addIrrigationToCollectionIfMissing([], irrigation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(irrigation);
      });

      it('should not add a Irrigation to an array that contains it', () => {
        const irrigation: IIrrigation = { id: 123 };
        const irrigationCollection: IIrrigation[] = [
          {
            ...irrigation,
          },
          { id: 456 },
        ];
        expectedResult = service.addIrrigationToCollectionIfMissing(irrigationCollection, irrigation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Irrigation to an array that doesn't contain it", () => {
        const irrigation: IIrrigation = { id: 123 };
        const irrigationCollection: IIrrigation[] = [{ id: 456 }];
        expectedResult = service.addIrrigationToCollectionIfMissing(irrigationCollection, irrigation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(irrigation);
      });

      it('should add only unique Irrigation to an array', () => {
        const irrigationArray: IIrrigation[] = [{ id: 123 }, { id: 456 }, { id: 72079 }];
        const irrigationCollection: IIrrigation[] = [{ id: 123 }];
        expectedResult = service.addIrrigationToCollectionIfMissing(irrigationCollection, ...irrigationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const irrigation: IIrrigation = { id: 123 };
        const irrigation2: IIrrigation = { id: 456 };
        expectedResult = service.addIrrigationToCollectionIfMissing([], irrigation, irrigation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(irrigation);
        expect(expectedResult).toContain(irrigation2);
      });

      it('should accept null and undefined values', () => {
        const irrigation: IIrrigation = { id: 123 };
        expectedResult = service.addIrrigationToCollectionIfMissing([], null, irrigation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(irrigation);
      });

      it('should return initial array if no Irrigation is added', () => {
        const irrigationCollection: IIrrigation[] = [{ id: 123 }];
        expectedResult = service.addIrrigationToCollectionIfMissing(irrigationCollection, undefined, null);
        expect(expectedResult).toEqual(irrigationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
