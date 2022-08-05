import dayjs from 'dayjs/esm';
import { IZone } from 'app/entities/zone/zone.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ActType } from 'app/entities/enumerations/act-type.model';

export interface IActivity {
  id?: number;
  gUID?: string | null;
  activityType?: ActType | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  zoneID?: IZone | null;
  cropID?: ICrop | null;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public activityType?: ActType | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public zoneID?: IZone | null,
    public cropID?: ICrop | null
  ) {}
}

export function getActivityIdentifier(activity: IActivity): number | undefined {
  return activity.id;
}
