import dayjs from 'dayjs/esm';
import { ILot } from 'app/entities/lot/lot.model';
import { SeedClass } from 'app/entities/enumerations/seed-class.model';
import { SeedRate } from 'app/entities/enumerations/seed-rate.model';
import { Treatment } from 'app/entities/enumerations/treatment.model';

export interface ISeed {
  id?: number;
  gUID?: string | null;
  breederID?: string | null;
  seedClass?: SeedClass | null;
  variety?: string | null;
  seedRate?: SeedRate | null;
  germinationPercentage?: number | null;
  treatment?: Treatment | null;
  origin?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  lots?: ILot[] | null;
}

export class Seed implements ISeed {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public breederID?: string | null,
    public seedClass?: SeedClass | null,
    public variety?: string | null,
    public seedRate?: SeedRate | null,
    public germinationPercentage?: number | null,
    public treatment?: Treatment | null,
    public origin?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public lots?: ILot[] | null
  ) {}
}

export function getSeedIdentifier(seed: ISeed): number | undefined {
  return seed.id;
}
