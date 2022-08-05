import dayjs from 'dayjs/esm';
import { IInspectionRecord } from 'app/entities/inspection-record/inspection-record.model';
import { IHarvest } from 'app/entities/harvest/harvest.model';
import { Defect } from 'app/entities/enumerations/defect.model';
import { Texture } from 'app/entities/enumerations/texture.model';
import { Aroma } from 'app/entities/enumerations/aroma.model';
import { Flavour } from 'app/entities/enumerations/flavour.model';
import { NutritionalValueType } from 'app/entities/enumerations/nutritional-value-type.model';

export interface IInspection {
  id?: number;
  gUID?: string | null;
  inspectionSize?: number | null;
  shape?: number | null;
  wholeness?: number | null;
  gloss?: number | null;
  consistency?: number | null;
  defects?: Defect | null;
  colour?: string | null;
  texture?: Texture | null;
  aroma?: Aroma | null;
  flavour?: Flavour | null;
  nutritionalValue?: number | null;
  nutritionalValueType?: NutritionalValueType | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  inspectionRecords?: IInspectionRecord[] | null;
  harvestID?: IHarvest | null;
}

export class Inspection implements IInspection {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public inspectionSize?: number | null,
    public shape?: number | null,
    public wholeness?: number | null,
    public gloss?: number | null,
    public consistency?: number | null,
    public defects?: Defect | null,
    public colour?: string | null,
    public texture?: Texture | null,
    public aroma?: Aroma | null,
    public flavour?: Flavour | null,
    public nutritionalValue?: number | null,
    public nutritionalValueType?: NutritionalValueType | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public inspectionRecords?: IInspectionRecord[] | null,
    public harvestID?: IHarvest | null
  ) {}
}

export function getInspectionIdentifier(inspection: IInspection): number | undefined {
  return inspection.id;
}
