import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ProFarmType } from 'app/entities/enumerations/pro-farm-type.model';
import { ProSubType } from 'app/entities/enumerations/pro-sub-type.model';
import { IPlantFactory, PlantFactory } from '../plant-factory.model';

import { PlantFactoryService } from './plant-factory.service';

describe('PlantFactory Service', () => {
  let service: PlantFactoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlantFactory;
  let expectedResult: IPlantFactory | IPlantFactory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlantFactoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      plantFactoryName: 'AAAAAAA',
      plantFactoryTypeID: ProFarmType.SN_Shade,
      plantFactorySubType: ProSubType.Femto,
      plantFactoryDescription: 'AAAAAAA',
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

    it('should create a PlantFactory', () => {
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

      service.create(new PlantFactory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlantFactory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          plantFactoryName: 'BBBBBB',
          plantFactoryTypeID: 'BBBBBB',
          plantFactorySubType: 'BBBBBB',
          plantFactoryDescription: 'BBBBBB',
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

    it('should partial update a PlantFactory', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          plantFactoryName: 'BBBBBB',
          plantFactoryTypeID: 'BBBBBB',
          plantFactorySubType: 'BBBBBB',
          plantFactoryDescription: 'BBBBBB',
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new PlantFactory()
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

    it('should return a list of PlantFactory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          plantFactoryName: 'BBBBBB',
          plantFactoryTypeID: 'BBBBBB',
          plantFactorySubType: 'BBBBBB',
          plantFactoryDescription: 'BBBBBB',
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

    it('should delete a PlantFactory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlantFactoryToCollectionIfMissing', () => {
      it('should add a PlantFactory to an empty array', () => {
        const plantFactory: IPlantFactory = { id: 123 };
        expectedResult = service.addPlantFactoryToCollectionIfMissing([], plantFactory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantFactory);
      });

      it('should not add a PlantFactory to an array that contains it', () => {
        const plantFactory: IPlantFactory = { id: 123 };
        const plantFactoryCollection: IPlantFactory[] = [
          {
            ...plantFactory,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlantFactoryToCollectionIfMissing(plantFactoryCollection, plantFactory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlantFactory to an array that doesn't contain it", () => {
        const plantFactory: IPlantFactory = { id: 123 };
        const plantFactoryCollection: IPlantFactory[] = [{ id: 456 }];
        expectedResult = service.addPlantFactoryToCollectionIfMissing(plantFactoryCollection, plantFactory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantFactory);
      });

      it('should add only unique PlantFactory to an array', () => {
        const plantFactoryArray: IPlantFactory[] = [{ id: 123 }, { id: 456 }, { id: 98398 }];
        const plantFactoryCollection: IPlantFactory[] = [{ id: 123 }];
        expectedResult = service.addPlantFactoryToCollectionIfMissing(plantFactoryCollection, ...plantFactoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plantFactory: IPlantFactory = { id: 123 };
        const plantFactory2: IPlantFactory = { id: 456 };
        expectedResult = service.addPlantFactoryToCollectionIfMissing([], plantFactory, plantFactory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantFactory);
        expect(expectedResult).toContain(plantFactory2);
      });

      it('should accept null and undefined values', () => {
        const plantFactory: IPlantFactory = { id: 123 };
        expectedResult = service.addPlantFactoryToCollectionIfMissing([], null, plantFactory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantFactory);
      });

      it('should return initial array if no PlantFactory is added', () => {
        const plantFactoryCollection: IPlantFactory[] = [{ id: 123 }];
        expectedResult = service.addPlantFactoryToCollectionIfMissing(plantFactoryCollection, undefined, null);
        expect(expectedResult).toEqual(plantFactoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
