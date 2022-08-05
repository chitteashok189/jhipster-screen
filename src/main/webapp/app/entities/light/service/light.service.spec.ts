import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { LightSource } from 'app/entities/enumerations/light-source.model';
import { ILight, Light } from '../light.model';

import { LightService } from './light.service';

describe('Light Service', () => {
  let service: LightService;
  let httpMock: HttpTestingController;
  let elemDefault: ILight;
  let expectedResult: ILight | ILight[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LightService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      source: LightSource.Automatic,
      lightIntensity: 0,
      dailyLightIntegral: 0,
      pAR: 0,
      pPFD: 0,
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

    it('should create a Light', () => {
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

      service.create(new Light()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Light', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          lightIntensity: 1,
          dailyLightIntegral: 1,
          pAR: 1,
          pPFD: 1,
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

    it('should partial update a Light', () => {
      const patchObject = Object.assign(
        {
          lightIntensity: 1,
          pAR: 1,
          pPFD: 1,
          createdBy: 1,
          updatedBy: 1,
        },
        new Light()
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

    it('should return a list of Light', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          lightIntensity: 1,
          dailyLightIntegral: 1,
          pAR: 1,
          pPFD: 1,
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

    it('should delete a Light', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLightToCollectionIfMissing', () => {
      it('should add a Light to an empty array', () => {
        const light: ILight = { id: 123 };
        expectedResult = service.addLightToCollectionIfMissing([], light);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(light);
      });

      it('should not add a Light to an array that contains it', () => {
        const light: ILight = { id: 123 };
        const lightCollection: ILight[] = [
          {
            ...light,
          },
          { id: 456 },
        ];
        expectedResult = service.addLightToCollectionIfMissing(lightCollection, light);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Light to an array that doesn't contain it", () => {
        const light: ILight = { id: 123 };
        const lightCollection: ILight[] = [{ id: 456 }];
        expectedResult = service.addLightToCollectionIfMissing(lightCollection, light);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(light);
      });

      it('should add only unique Light to an array', () => {
        const lightArray: ILight[] = [{ id: 123 }, { id: 456 }, { id: 94404 }];
        const lightCollection: ILight[] = [{ id: 123 }];
        expectedResult = service.addLightToCollectionIfMissing(lightCollection, ...lightArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const light: ILight = { id: 123 };
        const light2: ILight = { id: 456 };
        expectedResult = service.addLightToCollectionIfMissing([], light, light2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(light);
        expect(expectedResult).toContain(light2);
      });

      it('should accept null and undefined values', () => {
        const light: ILight = { id: 123 };
        expectedResult = service.addLightToCollectionIfMissing([], null, light, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(light);
      });

      it('should return initial array if no Light is added', () => {
        const lightCollection: ILight[] = [{ id: 123 }];
        expectedResult = service.addLightToCollectionIfMissing(lightCollection, undefined, null);
        expect(expectedResult).toEqual(lightCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
