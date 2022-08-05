import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Ec } from 'app/entities/enumerations/ec.model';
import { IRecipe, Recipe } from '../recipe.model';

import { RecipeService } from './recipe.service';

describe('Recipe Service', () => {
  let service: RecipeService;
  let httpMock: HttpTestingController;
  let elemDefault: IRecipe;
  let expectedResult: IRecipe | IRecipe[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RecipeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      plantName: 'AAAAAAA',
      recipeType: 'AAAAAAA',
      pHMin: 0,
      pHMax: 0,
      ecMin: Ec.Olericulture,
      eCMax: 0,
      airTempMax: 0,
      airTempMin: 0,
      humidityMax: 0,
      humidityMin: 0,
      nutrientTempMax: 0,
      nutrientTempMin: 0,
      luxGermMax: 0,
      luxGermMin: 0,
      lightGermDor: 0,
      lightGermCycle: 0,
      luxGrowMax: 0,
      luxGrowMin: 0,
      lightGrowDor: 0,
      lightGrowCycle: 0,
      co2LightMax: 0,
      co2LightMin: 0,
      co2DarkMax: 0,
      co2DarkMin: 0,
      dOMax: 0,
      dOMin: 0,
      mediaMoistureMax: 0,
      mediaMoistureMin: 0,
      nitrogen: 0,
      phosphorus: 0,
      potassium: 0,
      sulphur: 0,
      calcium: 0,
      magnesium: 0,
      manganese: 0,
      iron: 0,
      boron: 0,
      copper: 0,
      zinc: 0,
      molybdenum: 0,
      germinationTAT: 0,
      identificationComment: 'AAAAAAA',
      growingComment: 'AAAAAAA',
      usageComment: 'AAAAAAA',
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

    it('should create a Recipe', () => {
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

      service.create(new Recipe()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Recipe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          plantName: 'BBBBBB',
          recipeType: 'BBBBBB',
          pHMin: 1,
          pHMax: 1,
          ecMin: 'BBBBBB',
          eCMax: 1,
          airTempMax: 1,
          airTempMin: 1,
          humidityMax: 1,
          humidityMin: 1,
          nutrientTempMax: 1,
          nutrientTempMin: 1,
          luxGermMax: 1,
          luxGermMin: 1,
          lightGermDor: 1,
          lightGermCycle: 1,
          luxGrowMax: 1,
          luxGrowMin: 1,
          lightGrowDor: 1,
          lightGrowCycle: 1,
          co2LightMax: 1,
          co2LightMin: 1,
          co2DarkMax: 1,
          co2DarkMin: 1,
          dOMax: 1,
          dOMin: 1,
          mediaMoistureMax: 1,
          mediaMoistureMin: 1,
          nitrogen: 1,
          phosphorus: 1,
          potassium: 1,
          sulphur: 1,
          calcium: 1,
          magnesium: 1,
          manganese: 1,
          iron: 1,
          boron: 1,
          copper: 1,
          zinc: 1,
          molybdenum: 1,
          germinationTAT: 1,
          identificationComment: 'BBBBBB',
          growingComment: 'BBBBBB',
          usageComment: 'BBBBBB',
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

    it('should partial update a Recipe', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          plantName: 'BBBBBB',
          recipeType: 'BBBBBB',
          pHMin: 1,
          pHMax: 1,
          eCMax: 1,
          airTempMax: 1,
          airTempMin: 1,
          humidityMin: 1,
          luxGrowMin: 1,
          lightGrowDor: 1,
          co2DarkMin: 1,
          dOMin: 1,
          nitrogen: 1,
          phosphorus: 1,
          sulphur: 1,
          calcium: 1,
          magnesium: 1,
          boron: 1,
          copper: 1,
          molybdenum: 1,
          identificationComment: 'BBBBBB',
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
        },
        new Recipe()
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

    it('should return a list of Recipe', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          plantName: 'BBBBBB',
          recipeType: 'BBBBBB',
          pHMin: 1,
          pHMax: 1,
          ecMin: 'BBBBBB',
          eCMax: 1,
          airTempMax: 1,
          airTempMin: 1,
          humidityMax: 1,
          humidityMin: 1,
          nutrientTempMax: 1,
          nutrientTempMin: 1,
          luxGermMax: 1,
          luxGermMin: 1,
          lightGermDor: 1,
          lightGermCycle: 1,
          luxGrowMax: 1,
          luxGrowMin: 1,
          lightGrowDor: 1,
          lightGrowCycle: 1,
          co2LightMax: 1,
          co2LightMin: 1,
          co2DarkMax: 1,
          co2DarkMin: 1,
          dOMax: 1,
          dOMin: 1,
          mediaMoistureMax: 1,
          mediaMoistureMin: 1,
          nitrogen: 1,
          phosphorus: 1,
          potassium: 1,
          sulphur: 1,
          calcium: 1,
          magnesium: 1,
          manganese: 1,
          iron: 1,
          boron: 1,
          copper: 1,
          zinc: 1,
          molybdenum: 1,
          germinationTAT: 1,
          identificationComment: 'BBBBBB',
          growingComment: 'BBBBBB',
          usageComment: 'BBBBBB',
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

    it('should delete a Recipe', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRecipeToCollectionIfMissing', () => {
      it('should add a Recipe to an empty array', () => {
        const recipe: IRecipe = { id: 123 };
        expectedResult = service.addRecipeToCollectionIfMissing([], recipe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recipe);
      });

      it('should not add a Recipe to an array that contains it', () => {
        const recipe: IRecipe = { id: 123 };
        const recipeCollection: IRecipe[] = [
          {
            ...recipe,
          },
          { id: 456 },
        ];
        expectedResult = service.addRecipeToCollectionIfMissing(recipeCollection, recipe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Recipe to an array that doesn't contain it", () => {
        const recipe: IRecipe = { id: 123 };
        const recipeCollection: IRecipe[] = [{ id: 456 }];
        expectedResult = service.addRecipeToCollectionIfMissing(recipeCollection, recipe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recipe);
      });

      it('should add only unique Recipe to an array', () => {
        const recipeArray: IRecipe[] = [{ id: 123 }, { id: 456 }, { id: 70847 }];
        const recipeCollection: IRecipe[] = [{ id: 123 }];
        expectedResult = service.addRecipeToCollectionIfMissing(recipeCollection, ...recipeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const recipe: IRecipe = { id: 123 };
        const recipe2: IRecipe = { id: 456 };
        expectedResult = service.addRecipeToCollectionIfMissing([], recipe, recipe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recipe);
        expect(expectedResult).toContain(recipe2);
      });

      it('should accept null and undefined values', () => {
        const recipe: IRecipe = { id: 123 };
        expectedResult = service.addRecipeToCollectionIfMissing([], null, recipe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recipe);
      });

      it('should return initial array if no Recipe is added', () => {
        const recipeCollection: IRecipe[] = [{ id: 123 }];
        expectedResult = service.addRecipeToCollectionIfMissing(recipeCollection, undefined, null);
        expect(expectedResult).toEqual(recipeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
