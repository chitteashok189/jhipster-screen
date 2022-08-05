import dayjs from 'dayjs/esm';
import { MaterialType } from 'app/entities/enumerations/material-type.model';

export interface IRawMaterial {
  id?: number;
  gUID?: string | null;
  quantity?: number | null;
  uOM?: number | null;
  materialType?: MaterialType | null;
  price?: number | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class RawMaterial implements IRawMaterial {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public quantity?: number | null,
    public uOM?: number | null,
    public materialType?: MaterialType | null,
    public price?: number | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getRawMaterialIdentifier(rawMaterial: IRawMaterial): number | undefined {
  return rawMaterial.id;
}
