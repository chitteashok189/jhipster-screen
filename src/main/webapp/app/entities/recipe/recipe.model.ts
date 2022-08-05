import dayjs from 'dayjs/esm';
import { IPlant } from 'app/entities/plant/plant.model';
import { Ec } from 'app/entities/enumerations/ec.model';

export interface IRecipe {
  id?: number;
  gUID?: string | null;
  plantName?: string | null;
  recipeType?: string | null;
  pHMin?: number | null;
  pHMax?: number | null;
  ecMin?: Ec | null;
  eCMax?: number | null;
  airTempMax?: number | null;
  airTempMin?: number | null;
  humidityMax?: number | null;
  humidityMin?: number | null;
  nutrientTempMax?: number | null;
  nutrientTempMin?: number | null;
  luxGermMax?: number | null;
  luxGermMin?: number | null;
  lightGermDor?: number | null;
  lightGermCycle?: number | null;
  luxGrowMax?: number | null;
  luxGrowMin?: number | null;
  lightGrowDor?: number | null;
  lightGrowCycle?: number | null;
  co2LightMax?: number | null;
  co2LightMin?: number | null;
  co2DarkMax?: number | null;
  co2DarkMin?: number | null;
  dOMax?: number | null;
  dOMin?: number | null;
  mediaMoistureMax?: number | null;
  mediaMoistureMin?: number | null;
  nitrogen?: number | null;
  phosphorus?: number | null;
  potassium?: number | null;
  sulphur?: number | null;
  calcium?: number | null;
  magnesium?: number | null;
  manganese?: number | null;
  iron?: number | null;
  boron?: number | null;
  copper?: number | null;
  zinc?: number | null;
  molybdenum?: number | null;
  germinationTAT?: number | null;
  identificationComment?: string | null;
  growingComment?: string | null;
  usageComment?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  plantID?: IPlant | null;
}

export class Recipe implements IRecipe {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public plantName?: string | null,
    public recipeType?: string | null,
    public pHMin?: number | null,
    public pHMax?: number | null,
    public ecMin?: Ec | null,
    public eCMax?: number | null,
    public airTempMax?: number | null,
    public airTempMin?: number | null,
    public humidityMax?: number | null,
    public humidityMin?: number | null,
    public nutrientTempMax?: number | null,
    public nutrientTempMin?: number | null,
    public luxGermMax?: number | null,
    public luxGermMin?: number | null,
    public lightGermDor?: number | null,
    public lightGermCycle?: number | null,
    public luxGrowMax?: number | null,
    public luxGrowMin?: number | null,
    public lightGrowDor?: number | null,
    public lightGrowCycle?: number | null,
    public co2LightMax?: number | null,
    public co2LightMin?: number | null,
    public co2DarkMax?: number | null,
    public co2DarkMin?: number | null,
    public dOMax?: number | null,
    public dOMin?: number | null,
    public mediaMoistureMax?: number | null,
    public mediaMoistureMin?: number | null,
    public nitrogen?: number | null,
    public phosphorus?: number | null,
    public potassium?: number | null,
    public sulphur?: number | null,
    public calcium?: number | null,
    public magnesium?: number | null,
    public manganese?: number | null,
    public iron?: number | null,
    public boron?: number | null,
    public copper?: number | null,
    public zinc?: number | null,
    public molybdenum?: number | null,
    public germinationTAT?: number | null,
    public identificationComment?: string | null,
    public growingComment?: string | null,
    public usageComment?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public plantID?: IPlant | null
  ) {}
}

export function getRecipeIdentifier(recipe: IRecipe): number | undefined {
  return recipe.id;
}
