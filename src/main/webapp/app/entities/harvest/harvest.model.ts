import dayjs from 'dayjs/esm';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ILot } from 'app/entities/lot/lot.model';

export interface IHarvest {
  id?: number;
  gUID?: string | null;
  harvestingDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  inspections?: IInspection[] | null;
  cropID?: ICrop | null;
  lotID?: ILot | null;
}

export class Harvest implements IHarvest {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public harvestingDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public inspections?: IInspection[] | null,
    public cropID?: ICrop | null,
    public lotID?: ILot | null
  ) {}
}

export function getHarvestIdentifier(harvest: IHarvest): number | undefined {
  return harvest.id;
}
