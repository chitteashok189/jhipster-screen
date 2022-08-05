import dayjs from 'dayjs/esm';
import { IZone } from 'app/entities/zone/zone.model';
import { GrowBedType } from 'app/entities/enumerations/grow-bed-type.model';

export interface IGrowBed {
  id?: number;
  gUID?: string | null;
  growBedType?: GrowBedType | null;
  growBedName?: string | null;
  manufacturer?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  zones?: IZone[] | null;
}

export class GrowBed implements IGrowBed {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public growBedType?: GrowBedType | null,
    public growBedName?: string | null,
    public manufacturer?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public zones?: IZone[] | null
  ) {}
}

export function getGrowBedIdentifier(growBed: IGrowBed): number | undefined {
  return growBed.id;
}
