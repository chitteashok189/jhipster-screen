import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Seeding } from 'app/entities/enumerations/seeding.model';
import { TransplantMonth } from 'app/entities/enumerations/transplant-month.model';
import { HarvestMonth } from 'app/entities/enumerations/harvest-month.model';
import { IPlant, Plant } from '../plant.model';

import { PlantService } from './plant.service';

describe('Plant Service', () => {
  let service: PlantService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlant;
  let expectedResult: IPlant | IPlant[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlantService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      commonName: 'AAAAAAA',
      scientificName: 'AAAAAAA',
      familyName: 'AAAAAAA',
      plantSpacing: 0,
      seedingMonth: Seeding.January,
      transplantMonth: TransplantMonth.January,
      harvestMonth: HarvestMonth.January,
      originCountry: 0,
      yearlyCrops: 0,
      nativeTemperature: 0,
      nativeHumidity: 0,
      nativeDayDuration: 0,
      nativeNightDuration: 0,
      nativeSoilMoisture: 0,
      plantingPeriod: 0,
      yieldUnit: 0,
      growthHeightMin: 0,
      growthHeightMax: 0,
      grownSpreadMin: 0,
      grownSpreadMax: 0,
      grownWeightMax: 0,
      grownWeightMin: 0,
      growingMedia: 'AAAAAAA',
      documents: 'AAAAAAA',
      notes: 'AAAAAAA',
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

    it('should create a Plant', () => {
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

      service.create(new Plant()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Plant', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          commonName: 'BBBBBB',
          scientificName: 'BBBBBB',
          familyName: 'BBBBBB',
          plantSpacing: 1,
          seedingMonth: 'BBBBBB',
          transplantMonth: 'BBBBBB',
          harvestMonth: 'BBBBBB',
          originCountry: 1,
          yearlyCrops: 1,
          nativeTemperature: 1,
          nativeHumidity: 1,
          nativeDayDuration: 1,
          nativeNightDuration: 1,
          nativeSoilMoisture: 1,
          plantingPeriod: 1,
          yieldUnit: 1,
          growthHeightMin: 1,
          growthHeightMax: 1,
          grownSpreadMin: 1,
          grownSpreadMax: 1,
          grownWeightMax: 1,
          grownWeightMin: 1,
          growingMedia: 'BBBBBB',
          documents: 'BBBBBB',
          notes: 'BBBBBB',
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

    it('should partial update a Plant', () => {
      const patchObject = Object.assign(
        {
          scientificName: 'BBBBBB',
          familyName: 'BBBBBB',
          transplantMonth: 'BBBBBB',
          harvestMonth: 'BBBBBB',
          originCountry: 1,
          yearlyCrops: 1,
          nativeTemperature: 1,
          nativeHumidity: 1,
          nativeDayDuration: 1,
          nativeNightDuration: 1,
          yieldUnit: 1,
          growthHeightMin: 1,
          growthHeightMax: 1,
          grownWeightMax: 1,
          grownWeightMin: 1,
          attachments: 'BBBBBB',
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Plant()
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

    it('should return a list of Plant', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          commonName: 'BBBBBB',
          scientificName: 'BBBBBB',
          familyName: 'BBBBBB',
          plantSpacing: 1,
          seedingMonth: 'BBBBBB',
          transplantMonth: 'BBBBBB',
          harvestMonth: 'BBBBBB',
          originCountry: 1,
          yearlyCrops: 1,
          nativeTemperature: 1,
          nativeHumidity: 1,
          nativeDayDuration: 1,
          nativeNightDuration: 1,
          nativeSoilMoisture: 1,
          plantingPeriod: 1,
          yieldUnit: 1,
          growthHeightMin: 1,
          growthHeightMax: 1,
          grownSpreadMin: 1,
          grownSpreadMax: 1,
          grownWeightMax: 1,
          grownWeightMin: 1,
          growingMedia: 'BBBBBB',
          documents: 'BBBBBB',
          notes: 'BBBBBB',
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

    it('should delete a Plant', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlantToCollectionIfMissing', () => {
      it('should add a Plant to an empty array', () => {
        const plant: IPlant = { id: 123 };
        expectedResult = service.addPlantToCollectionIfMissing([], plant);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plant);
      });

      it('should not add a Plant to an array that contains it', () => {
        const plant: IPlant = { id: 123 };
        const plantCollection: IPlant[] = [
          {
            ...plant,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlantToCollectionIfMissing(plantCollection, plant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Plant to an array that doesn't contain it", () => {
        const plant: IPlant = { id: 123 };
        const plantCollection: IPlant[] = [{ id: 456 }];
        expectedResult = service.addPlantToCollectionIfMissing(plantCollection, plant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plant);
      });

      it('should add only unique Plant to an array', () => {
        const plantArray: IPlant[] = [{ id: 123 }, { id: 456 }, { id: 11341 }];
        const plantCollection: IPlant[] = [{ id: 123 }];
        expectedResult = service.addPlantToCollectionIfMissing(plantCollection, ...plantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plant: IPlant = { id: 123 };
        const plant2: IPlant = { id: 456 };
        expectedResult = service.addPlantToCollectionIfMissing([], plant, plant2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plant);
        expect(expectedResult).toContain(plant2);
      });

      it('should accept null and undefined values', () => {
        const plant: IPlant = { id: 123 };
        expectedResult = service.addPlantToCollectionIfMissing([], null, plant, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plant);
      });

      it('should return initial array if no Plant is added', () => {
        const plantCollection: IPlant[] = [{ id: 123 }];
        expectedResult = service.addPlantToCollectionIfMissing(plantCollection, undefined, null);
        expect(expectedResult).toEqual(plantCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
