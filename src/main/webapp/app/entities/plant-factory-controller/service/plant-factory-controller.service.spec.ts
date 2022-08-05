import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { PlantSource } from 'app/entities/enumerations/plant-source.model';
import { IPlantFactoryController, PlantFactoryController } from '../plant-factory-controller.model';

import { PlantFactoryControllerService } from './plant-factory-controller.service';

describe('PlantFactoryController Service', () => {
  let service: PlantFactoryControllerService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlantFactoryController;
  let expectedResult: IPlantFactoryController | IPlantFactoryController[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlantFactoryControllerService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      source: PlantSource.Automatic,
      airTemperature: 0,
      relativeHumidity: 0,
      vPD: 0,
      evapotranspiration: 0,
      barometricPressure: 0,
      seaLevelPressure: 0,
      co2Level: 0,
      nutrientTankLevel: 0,
      dewPoint: 0,
      heatIndex: 0,
      turbidity: 0,
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

    it('should create a PlantFactoryController', () => {
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

      service.create(new PlantFactoryController()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlantFactoryController', () => {
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
          nutrientTankLevel: 1,
          dewPoint: 1,
          heatIndex: 1,
          turbidity: 1,
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

    it('should partial update a PlantFactoryController', () => {
      const patchObject = Object.assign(
        {
          source: 'BBBBBB',
          airTemperature: 1,
          vPD: 1,
          evapotranspiration: 1,
          co2Level: 1,
          nutrientTankLevel: 1,
          dewPoint: 1,
          heatIndex: 1,
          turbidity: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new PlantFactoryController()
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

    it('should return a list of PlantFactoryController', () => {
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
          nutrientTankLevel: 1,
          dewPoint: 1,
          heatIndex: 1,
          turbidity: 1,
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

    it('should delete a PlantFactoryController', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlantFactoryControllerToCollectionIfMissing', () => {
      it('should add a PlantFactoryController to an empty array', () => {
        const plantFactoryController: IPlantFactoryController = { id: 123 };
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing([], plantFactoryController);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantFactoryController);
      });

      it('should not add a PlantFactoryController to an array that contains it', () => {
        const plantFactoryController: IPlantFactoryController = { id: 123 };
        const plantFactoryControllerCollection: IPlantFactoryController[] = [
          {
            ...plantFactoryController,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing(plantFactoryControllerCollection, plantFactoryController);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlantFactoryController to an array that doesn't contain it", () => {
        const plantFactoryController: IPlantFactoryController = { id: 123 };
        const plantFactoryControllerCollection: IPlantFactoryController[] = [{ id: 456 }];
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing(plantFactoryControllerCollection, plantFactoryController);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantFactoryController);
      });

      it('should add only unique PlantFactoryController to an array', () => {
        const plantFactoryControllerArray: IPlantFactoryController[] = [{ id: 123 }, { id: 456 }, { id: 91215 }];
        const plantFactoryControllerCollection: IPlantFactoryController[] = [{ id: 123 }];
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing(
          plantFactoryControllerCollection,
          ...plantFactoryControllerArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plantFactoryController: IPlantFactoryController = { id: 123 };
        const plantFactoryController2: IPlantFactoryController = { id: 456 };
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing([], plantFactoryController, plantFactoryController2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantFactoryController);
        expect(expectedResult).toContain(plantFactoryController2);
      });

      it('should accept null and undefined values', () => {
        const plantFactoryController: IPlantFactoryController = { id: 123 };
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing([], null, plantFactoryController, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantFactoryController);
      });

      it('should return initial array if no PlantFactoryController is added', () => {
        const plantFactoryControllerCollection: IPlantFactoryController[] = [{ id: 123 }];
        expectedResult = service.addPlantFactoryControllerToCollectionIfMissing(plantFactoryControllerCollection, undefined, null);
        expect(expectedResult).toEqual(plantFactoryControllerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
