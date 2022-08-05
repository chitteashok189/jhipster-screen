import dayjs from 'dayjs/esm';
import { IPest } from 'app/entities/pest/pest.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ConType } from 'app/entities/enumerations/con-type.model';

export interface IPestControl {
  id?: number;
  gUID?: string | null;
  natureOfDamage?: string | null;
  controlType?: ConType | null;
  controlMeasures?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  pestID?: IPest | null;
  cropID?: ICrop | null;
}

export class PestControl implements IPestControl {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public natureOfDamage?: string | null,
    public controlType?: ConType | null,
    public controlMeasures?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public pestID?: IPest | null,
    public cropID?: ICrop | null
  ) {}
}

export function getPestControlIdentifier(pestControl: IPestControl): number | undefined {
  return pestControl.id;
}
