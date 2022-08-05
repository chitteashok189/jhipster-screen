import dayjs from 'dayjs/esm';
import { IHarvest } from 'app/entities/harvest/harvest.model';
import { IInspectionRecord } from 'app/entities/inspection-record/inspection-record.model';
import { ISeed } from 'app/entities/seed/seed.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { Unit } from 'app/entities/enumerations/unit.model';
import { Sowing } from 'app/entities/enumerations/sowing.model';
import { Transplantation } from 'app/entities/enumerations/transplantation.model';
import { HarTime } from 'app/entities/enumerations/har-time.model';

export interface ILot {
  id?: number;
  gUID?: string | null;
  lotCode?: string | null;
  lotQRCodeContentType?: string | null;
  lotQRCode?: string | null;
  lotSize?: number | null;
  unitType?: Unit | null;
  seedlingsGerminated?: number | null;
  seedlingsDied?: number | null;
  plantsWasted?: number | null;
  traysSown?: number | null;
  sowingTime?: Sowing | null;
  traysTranplanted?: number | null;
  transplantationTime?: Transplantation | null;
  plantsHarvested?: number | null;
  harvestTime?: HarTime | null;
  packingDate?: dayjs.Dayjs | null;
  tranportationDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  harvests?: IHarvest[] | null;
  inspectionRecords?: IInspectionRecord[] | null;
  seedID?: ISeed | null;
  cropID?: ICrop | null;
}

export class Lot implements ILot {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public lotCode?: string | null,
    public lotQRCodeContentType?: string | null,
    public lotQRCode?: string | null,
    public lotSize?: number | null,
    public unitType?: Unit | null,
    public seedlingsGerminated?: number | null,
    public seedlingsDied?: number | null,
    public plantsWasted?: number | null,
    public traysSown?: number | null,
    public sowingTime?: Sowing | null,
    public traysTranplanted?: number | null,
    public transplantationTime?: Transplantation | null,
    public plantsHarvested?: number | null,
    public harvestTime?: HarTime | null,
    public packingDate?: dayjs.Dayjs | null,
    public tranportationDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public harvests?: IHarvest[] | null,
    public inspectionRecords?: IInspectionRecord[] | null,
    public seedID?: ISeed | null,
    public cropID?: ICrop | null
  ) {}
}

export function getLotIdentifier(lot: ILot): number | undefined {
  return lot.id;
}
