import dayjs from 'dayjs/esm';
import { IRecipe } from 'app/entities/recipe/recipe.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { Seeding } from 'app/entities/enumerations/seeding.model';
import { TransplantMonth } from 'app/entities/enumerations/transplant-month.model';
import { HarvestMonth } from 'app/entities/enumerations/harvest-month.model';

export interface IPlant {
  id?: number;
  gUID?: string | null;
  commonName?: string | null;
  scientificName?: string | null;
  familyName?: string | null;
  plantSpacing?: number | null;
  seedingMonth?: Seeding | null;
  transplantMonth?: TransplantMonth | null;
  harvestMonth?: HarvestMonth | null;
  originCountry?: number | null;
  yearlyCrops?: number | null;
  nativeTemperature?: number | null;
  nativeHumidity?: number | null;
  nativeDayDuration?: number | null;
  nativeNightDuration?: number | null;
  nativeSoilMoisture?: number | null;
  plantingPeriod?: number | null;
  yieldUnit?: number | null;
  growthHeightMin?: number | null;
  growthHeightMax?: number | null;
  grownSpreadMin?: number | null;
  grownSpreadMax?: number | null;
  grownWeightMax?: number | null;
  grownWeightMin?: number | null;
  growingMedia?: string | null;
  documents?: string | null;
  notes?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  recipes?: IRecipe[] | null;
  crops?: ICrop[] | null;
  cropID?: ICrop | null;
}

export class Plant implements IPlant {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public commonName?: string | null,
    public scientificName?: string | null,
    public familyName?: string | null,
    public plantSpacing?: number | null,
    public seedingMonth?: Seeding | null,
    public transplantMonth?: TransplantMonth | null,
    public harvestMonth?: HarvestMonth | null,
    public originCountry?: number | null,
    public yearlyCrops?: number | null,
    public nativeTemperature?: number | null,
    public nativeHumidity?: number | null,
    public nativeDayDuration?: number | null,
    public nativeNightDuration?: number | null,
    public nativeSoilMoisture?: number | null,
    public plantingPeriod?: number | null,
    public yieldUnit?: number | null,
    public growthHeightMin?: number | null,
    public growthHeightMax?: number | null,
    public grownSpreadMin?: number | null,
    public grownSpreadMax?: number | null,
    public grownWeightMax?: number | null,
    public grownWeightMin?: number | null,
    public growingMedia?: string | null,
    public documents?: string | null,
    public notes?: string | null,
    public attachmentsContentType?: string | null,
    public attachments?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public recipes?: IRecipe[] | null,
    public crops?: ICrop[] | null,
    public cropID?: ICrop | null
  ) {}
}

export function getPlantIdentifier(plant: IPlant): number | undefined {
  return plant.id;
}
