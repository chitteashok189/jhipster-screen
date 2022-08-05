import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { CliSource } from 'app/entities/enumerations/cli-source.model';
import { IClimate, Climate } from '../climate.model';

import { ClimateService } from './climate.service';

describe('Climate Service', () => {
  let service: ClimateService;
  let httpMock: HttpTestingController;
  let elemDefault: IClimate;
  let expectedResult: IClimate | IClimate[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClimateService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      source: CliSource.Automatic,
      airTemperature: 0,
      relativeHumidity: 0,
      vPD: 0,
      evapotranspiration: 0,
      barometricPressure: 0,
      seaLevelPressure: 0,
      co2Level: 0,
      dewPoint: 0,
      solarRadiation: 0,
      heatIndex: 0,
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

    it('should create a Climate', () => {
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

      service.create(new Climate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Climate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          airTemperature: 1,
          relativeHumidity: 1,
          vPD: 1,
          evapotranspiration: 1,
          barometricPressure: 1,
          seaLevelPressure: 1,
          co2Level: 1,
          dewPoint: 1,
          solarRadiation: 1,
          heatIndex: 1,
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

    it('should partial update a Climate', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          vPD: 1,
          evapotranspiration: 1,
          barometricPressure: 1,
          seaLevelPressure: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new Climate()
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

    it('should return a list of Climate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          source: 'BBBBBB',
          airTemperature: 1,
          relativeHumidity: 1,
          vPD: 1,
          evapotranspiration: 1,
          barometricPressure: 1,
          seaLevelPressure: 1,
          co2Level: 1,
          dewPoint: 1,
          solarRadiation: 1,
          heatIndex: 1,
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

    it('should delete a Climate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addClimateToCollectionIfMissing', () => {
      it('should add a Climate to an empty array', () => {
        const climate: IClimate = { id: 123 };
        expectedResult = service.addClimateToCollectionIfMissing([], climate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(climate);
      });

      it('should not add a Climate to an array that contains it', () => {
        const climate: IClimate = { id: 123 };
        const climateCollection: IClimate[] = [
          {
            ...climate,
          },
          { id: 456 },
        ];
        expectedResult = service.addClimateToCollectionIfMissing(climateCollection, climate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Climate to an array that doesn't contain it", () => {
        const climate: IClimate = { id: 123 };
        const climateCollection: IClimate[] = [{ id: 456 }];
        expectedResult = service.addClimateToCollectionIfMissing(climateCollection, climate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(climate);
      });

      it('should add only unique Climate to an array', () => {
        const climateArray: IClimate[] = [{ id: 123 }, { id: 456 }, { id: 53320 }];
        const climateCollection: IClimate[] = [{ id: 123 }];
        expectedResult = service.addClimateToCollectionIfMissing(climateCollection, ...climateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const climate: IClimate = { id: 123 };
        const climate2: IClimate = { id: 456 };
        expectedResult = service.addClimateToCollectionIfMissing([], climate, climate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(climate);
        expect(expectedResult).toContain(climate2);
      });

      it('should accept null and undefined values', () => {
        const climate: IClimate = { id: 123 };
        expectedResult = service.addClimateToCollectionIfMissing([], null, climate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(climate);
      });

      it('should return initial array if no Climate is added', () => {
        const climateCollection: IClimate[] = [{ id: 123 }];
        expectedResult = service.addClimateToCollectionIfMissing(climateCollection, undefined, null);
        expect(expectedResult).toEqual(climateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
