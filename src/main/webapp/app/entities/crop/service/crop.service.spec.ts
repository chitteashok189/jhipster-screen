import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { CropTyp } from 'app/entities/enumerations/crop-typ.model';
import { Horti } from 'app/entities/enumerations/horti.model';
import { GroPhase } from 'app/entities/enumerations/gro-phase.model';
import { PlantingMat } from 'app/entities/enumerations/planting-mat.model';
import { ICrop, Crop } from '../crop.model';

import { CropService } from './crop.service';

describe('Crop Service', () => {
  let service: CropService;
  let httpMock: HttpTestingController;
  let elemDefault: ICrop;
  let expectedResult: ICrop | ICrop[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CropService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      cropCode: 'AAAAAAA',
      cropName: 'AAAAAAA',
      cropType: CropTyp.Food,
      horticultureType: Horti.Olericulture,
      isHybrid: false,
      cultivar: 'AAAAAAA',
      sowingDate: currentDate,
      sowingDepth: 0,
      rowSpacingMax: 0,
      rowSpacingMin: 0,
      seedDepthMax: 0,
      seedDepthMin: 0,
      seedSpacingMax: 0,
      seedSpacingMin: 0,
      yearlyCrops: 0,
      growingSeason: 'AAAAAAA',
      growingPhase: GroPhase.Initial,
      plantingDate: currentDate,
      plantSpacing: 0,
      plantingMaterial: PlantingMat.Seeds,
      transplantationDate: currentDate,
      fertigationscheduleID: 0,
      plannedYield: 0,
      actualYield: 0,
      yieldUnit: 0,
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
          sowingDate: currentDate.format(DATE_TIME_FORMAT),
          plantingDate: currentDate.format(DATE_TIME_FORMAT),
          transplantationDate: currentDate.format(DATE_TIME_FORMAT),
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

    it('should create a Crop', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          sowingDate: currentDate.format(DATE_TIME_FORMAT),
          plantingDate: currentDate.format(DATE_TIME_FORMAT),
          transplantationDate: currentDate.format(DATE_TIME_FORMAT),
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sowingDate: currentDate,
          plantingDate: currentDate,
          transplantationDate: currentDate,
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Crop()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Crop', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          cropCode: 'BBBBBB',
          cropName: 'BBBBBB',
          cropType: 'BBBBBB',
          horticultureType: 'BBBBBB',
          isHybrid: true,
          cultivar: 'BBBBBB',
          sowingDate: currentDate.format(DATE_TIME_FORMAT),
          sowingDepth: 1,
          rowSpacingMax: 1,
          rowSpacingMin: 1,
          seedDepthMax: 1,
          seedDepthMin: 1,
          seedSpacingMax: 1,
          seedSpacingMin: 1,
          yearlyCrops: 1,
          growingSeason: 'BBBBBB',
          growingPhase: 'BBBBBB',
          plantingDate: currentDate.format(DATE_TIME_FORMAT),
          plantSpacing: 1,
          plantingMaterial: 'BBBBBB',
          transplantationDate: currentDate.format(DATE_TIME_FORMAT),
          fertigationscheduleID: 1,
          plannedYield: 1,
          actualYield: 1,
          yieldUnit: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sowingDate: currentDate,
          plantingDate: currentDate,
          transplantationDate: currentDate,
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

    it('should partial update a Crop', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          cropType: 'BBBBBB',
          horticultureType: 'BBBBBB',
          isHybrid: true,
          sowingDate: currentDate.format(DATE_TIME_FORMAT),
          sowingDepth: 1,
          seedDepthMax: 1,
          seedDepthMin: 1,
          seedSpacingMax: 1,
          seedSpacingMin: 1,
          growingSeason: 'BBBBBB',
          growingPhase: 'BBBBBB',
          plantingDate: currentDate.format(DATE_TIME_FORMAT),
          plantSpacing: 1,
          plantingMaterial: 'BBBBBB',
          transplantationDate: currentDate.format(DATE_TIME_FORMAT),
          plannedYield: 1,
          actualYield: 1,
          yieldUnit: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Crop()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          sowingDate: currentDate,
          plantingDate: currentDate,
          transplantationDate: currentDate,
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

    it('should return a list of Crop', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          cropCode: 'BBBBBB',
          cropName: 'BBBBBB',
          cropType: 'BBBBBB',
          horticultureType: 'BBBBBB',
          isHybrid: true,
          cultivar: 'BBBBBB',
          sowingDate: currentDate.format(DATE_TIME_FORMAT),
          sowingDepth: 1,
          rowSpacingMax: 1,
          rowSpacingMin: 1,
          seedDepthMax: 1,
          seedDepthMin: 1,
          seedSpacingMax: 1,
          seedSpacingMin: 1,
          yearlyCrops: 1,
          growingSeason: 'BBBBBB',
          growingPhase: 'BBBBBB',
          plantingDate: currentDate.format(DATE_TIME_FORMAT),
          plantSpacing: 1,
          plantingMaterial: 'BBBBBB',
          transplantationDate: currentDate.format(DATE_TIME_FORMAT),
          fertigationscheduleID: 1,
          plannedYield: 1,
          actualYield: 1,
          yieldUnit: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          sowingDate: currentDate,
          plantingDate: currentDate,
          transplantationDate: currentDate,
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

    it('should delete a Crop', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCropToCollectionIfMissing', () => {
      it('should add a Crop to an empty array', () => {
        const crop: ICrop = { id: 123 };
        expectedResult = service.addCropToCollectionIfMissing([], crop);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(crop);
      });

      it('should not add a Crop to an array that contains it', () => {
        const crop: ICrop = { id: 123 };
        const cropCollection: ICrop[] = [
          {
            ...crop,
          },
          { id: 456 },
        ];
        expectedResult = service.addCropToCollectionIfMissing(cropCollection, crop);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Crop to an array that doesn't contain it", () => {
        const crop: ICrop = { id: 123 };
        const cropCollection: ICrop[] = [{ id: 456 }];
        expectedResult = service.addCropToCollectionIfMissing(cropCollection, crop);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(crop);
      });

      it('should add only unique Crop to an array', () => {
        const cropArray: ICrop[] = [{ id: 123 }, { id: 456 }, { id: 91968 }];
        const cropCollection: ICrop[] = [{ id: 123 }];
        expectedResult = service.addCropToCollectionIfMissing(cropCollection, ...cropArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const crop: ICrop = { id: 123 };
        const crop2: ICrop = { id: 456 };
        expectedResult = service.addCropToCollectionIfMissing([], crop, crop2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(crop);
        expect(expectedResult).toContain(crop2);
      });

      it('should accept null and undefined values', () => {
        const crop: ICrop = { id: 123 };
        expectedResult = service.addCropToCollectionIfMissing([], null, crop, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(crop);
      });

      it('should return initial array if no Crop is added', () => {
        const cropCollection: ICrop[] = [{ id: 123 }];
        expectedResult = service.addCropToCollectionIfMissing(cropCollection, undefined, null);
        expect(expectedResult).toEqual(cropCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
