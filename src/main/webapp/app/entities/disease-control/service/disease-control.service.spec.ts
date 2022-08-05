import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DisConType } from 'app/entities/enumerations/dis-con-type.model';
import { IDiseaseControl, DiseaseControl } from '../disease-control.model';

import { DiseaseControlService } from './disease-control.service';

describe('DiseaseControl Service', () => {
  let service: DiseaseControlService;
  let httpMock: HttpTestingController;
  let elemDefault: IDiseaseControl;
  let expectedResult: IDiseaseControl | IDiseaseControl[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiseaseControlService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      controlType: DisConType.Biological,
      treatment: 'AAAAAAA',
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

    it('should create a DiseaseControl', () => {
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

      service.create(new DiseaseControl()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DiseaseControl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          controlType: 'BBBBBB',
          treatment: 'BBBBBB',
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

    it('should partial update a DiseaseControl', () => {
      const patchObject = Object.assign(
        {
          controlType: 'BBBBBB',
          treatment: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new DiseaseControl()
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

    it('should return a list of DiseaseControl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          controlType: 'BBBBBB',
          treatment: 'BBBBBB',
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

    it('should delete a DiseaseControl', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDiseaseControlToCollectionIfMissing', () => {
      it('should add a DiseaseControl to an empty array', () => {
        const diseaseControl: IDiseaseControl = { id: 123 };
        expectedResult = service.addDiseaseControlToCollectionIfMissing([], diseaseControl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diseaseControl);
      });

      it('should not add a DiseaseControl to an array that contains it', () => {
        const diseaseControl: IDiseaseControl = { id: 123 };
        const diseaseControlCollection: IDiseaseControl[] = [
          {
            ...diseaseControl,
          },
          { id: 456 },
        ];
        expectedResult = service.addDiseaseControlToCollectionIfMissing(diseaseControlCollection, diseaseControl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DiseaseControl to an array that doesn't contain it", () => {
        const diseaseControl: IDiseaseControl = { id: 123 };
        const diseaseControlCollection: IDiseaseControl[] = [{ id: 456 }];
        expectedResult = service.addDiseaseControlToCollectionIfMissing(diseaseControlCollection, diseaseControl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diseaseControl);
      });

      it('should add only unique DiseaseControl to an array', () => {
        const diseaseControlArray: IDiseaseControl[] = [{ id: 123 }, { id: 456 }, { id: 14007 }];
        const diseaseControlCollection: IDiseaseControl[] = [{ id: 123 }];
        expectedResult = service.addDiseaseControlToCollectionIfMissing(diseaseControlCollection, ...diseaseControlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const diseaseControl: IDiseaseControl = { id: 123 };
        const diseaseControl2: IDiseaseControl = { id: 456 };
        expectedResult = service.addDiseaseControlToCollectionIfMissing([], diseaseControl, diseaseControl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diseaseControl);
        expect(expectedResult).toContain(diseaseControl2);
      });

      it('should accept null and undefined values', () => {
        const diseaseControl: IDiseaseControl = { id: 123 };
        expectedResult = service.addDiseaseControlToCollectionIfMissing([], null, diseaseControl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diseaseControl);
      });

      it('should return initial array if no DiseaseControl is added', () => {
        const diseaseControlCollection: IDiseaseControl[] = [{ id: 123 }];
        expectedResult = service.addDiseaseControlToCollectionIfMissing(diseaseControlCollection, undefined, null);
        expect(expectedResult).toEqual(diseaseControlCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
