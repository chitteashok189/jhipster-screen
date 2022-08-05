import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { DisThreatLevel } from 'app/entities/enumerations/dis-threat-level.model';
import { IDisease, Disease } from '../disease.model';

import { DiseaseService } from './disease.service';

describe('Disease Service', () => {
  let service: DiseaseService;
  let httpMock: HttpTestingController;
  let elemDefault: IDisease;
  let expectedResult: IDisease | IDisease[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiseaseService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      threatLevel: DisThreatLevel.High,
      name: 'AAAAAAA',
      causalOrganism: 'AAAAAAA',
      attachments: 0,
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

    it('should create a Disease', () => {
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

      service.create(new Disease()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Disease', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          threatLevel: 'BBBBBB',
          name: 'BBBBBB',
          causalOrganism: 'BBBBBB',
          attachments: 1,
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

    it('should partial update a Disease', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          threatLevel: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Disease()
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

    it('should return a list of Disease', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          threatLevel: 'BBBBBB',
          name: 'BBBBBB',
          causalOrganism: 'BBBBBB',
          attachments: 1,
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

    it('should delete a Disease', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDiseaseToCollectionIfMissing', () => {
      it('should add a Disease to an empty array', () => {
        const disease: IDisease = { id: 123 };
        expectedResult = service.addDiseaseToCollectionIfMissing([], disease);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disease);
      });

      it('should not add a Disease to an array that contains it', () => {
        const disease: IDisease = { id: 123 };
        const diseaseCollection: IDisease[] = [
          {
            ...disease,
          },
          { id: 456 },
        ];
        expectedResult = service.addDiseaseToCollectionIfMissing(diseaseCollection, disease);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Disease to an array that doesn't contain it", () => {
        const disease: IDisease = { id: 123 };
        const diseaseCollection: IDisease[] = [{ id: 456 }];
        expectedResult = service.addDiseaseToCollectionIfMissing(diseaseCollection, disease);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disease);
      });

      it('should add only unique Disease to an array', () => {
        const diseaseArray: IDisease[] = [{ id: 123 }, { id: 456 }, { id: 76074 }];
        const diseaseCollection: IDisease[] = [{ id: 123 }];
        expectedResult = service.addDiseaseToCollectionIfMissing(diseaseCollection, ...diseaseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const disease: IDisease = { id: 123 };
        const disease2: IDisease = { id: 456 };
        expectedResult = service.addDiseaseToCollectionIfMissing([], disease, disease2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disease);
        expect(expectedResult).toContain(disease2);
      });

      it('should accept null and undefined values', () => {
        const disease: IDisease = { id: 123 };
        expectedResult = service.addDiseaseToCollectionIfMissing([], null, disease, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disease);
      });

      it('should return initial array if no Disease is added', () => {
        const diseaseCollection: IDisease[] = [{ id: 123 }];
        expectedResult = service.addDiseaseToCollectionIfMissing(diseaseCollection, undefined, null);
        expect(expectedResult).toEqual(diseaseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
