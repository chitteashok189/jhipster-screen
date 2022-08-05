import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ConType } from 'app/entities/enumerations/con-type.model';
import { IPestControl, PestControl } from '../pest-control.model';

import { PestControlService } from './pest-control.service';

describe('PestControl Service', () => {
  let service: PestControlService;
  let httpMock: HttpTestingController;
  let elemDefault: IPestControl;
  let expectedResult: IPestControl | IPestControl[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PestControlService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      natureOfDamage: 'AAAAAAA',
      controlType: ConType.Biological,
      controlMeasures: 'AAAAAAA',
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

    it('should create a PestControl', () => {
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

      service.create(new PestControl()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PestControl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          natureOfDamage: 'BBBBBB',
          controlType: 'BBBBBB',
          controlMeasures: 'BBBBBB',
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

    it('should partial update a PestControl', () => {
      const patchObject = Object.assign(
        {
          controlType: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new PestControl()
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

    it('should return a list of PestControl', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          natureOfDamage: 'BBBBBB',
          controlType: 'BBBBBB',
          controlMeasures: 'BBBBBB',
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

    it('should delete a PestControl', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPestControlToCollectionIfMissing', () => {
      it('should add a PestControl to an empty array', () => {
        const pestControl: IPestControl = { id: 123 };
        expectedResult = service.addPestControlToCollectionIfMissing([], pestControl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pestControl);
      });

      it('should not add a PestControl to an array that contains it', () => {
        const pestControl: IPestControl = { id: 123 };
        const pestControlCollection: IPestControl[] = [
          {
            ...pestControl,
          },
          { id: 456 },
        ];
        expectedResult = service.addPestControlToCollectionIfMissing(pestControlCollection, pestControl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PestControl to an array that doesn't contain it", () => {
        const pestControl: IPestControl = { id: 123 };
        const pestControlCollection: IPestControl[] = [{ id: 456 }];
        expectedResult = service.addPestControlToCollectionIfMissing(pestControlCollection, pestControl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pestControl);
      });

      it('should add only unique PestControl to an array', () => {
        const pestControlArray: IPestControl[] = [{ id: 123 }, { id: 456 }, { id: 58881 }];
        const pestControlCollection: IPestControl[] = [{ id: 123 }];
        expectedResult = service.addPestControlToCollectionIfMissing(pestControlCollection, ...pestControlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pestControl: IPestControl = { id: 123 };
        const pestControl2: IPestControl = { id: 456 };
        expectedResult = service.addPestControlToCollectionIfMissing([], pestControl, pestControl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pestControl);
        expect(expectedResult).toContain(pestControl2);
      });

      it('should accept null and undefined values', () => {
        const pestControl: IPestControl = { id: 123 };
        expectedResult = service.addPestControlToCollectionIfMissing([], null, pestControl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pestControl);
      });

      it('should return initial array if no PestControl is added', () => {
        const pestControlCollection: IPestControl[] = [{ id: 123 }];
        expectedResult = service.addPestControlToCollectionIfMissing(pestControlCollection, undefined, null);
        expect(expectedResult).toEqual(pestControlCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
