import dayjs from 'dayjs/esm';
import { IScouting } from 'app/entities/scouting/scouting.model';

export interface IDisorder {
  id?: number;
  gUID?: string | null;
  physiologicalDisorder?: string | null;
  cause?: string | null;
  controlMeasure?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  scoutingID?: IScouting | null;
}

export class Disorder implements IDisorder {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public physiologicalDisorder?: string | null,
    public cause?: string | null,
    public controlMeasure?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public scoutingID?: IScouting | null
  ) {}
}

export function getDisorderIdentifier(disorder: IDisorder): number | undefined {
  return disorder.id;
}
