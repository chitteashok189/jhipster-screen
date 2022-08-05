import dayjs from 'dayjs/esm';
import { IFarm } from 'app/entities/farm/farm.model';
import { ISensor } from 'app/entities/sensor/sensor.model';

export interface ILocation {
  id?: number;
  gUID?: string | null;
  buildingNo?: number | null;
  street?: string | null;
  area?: string | null;
  country?: number | null;
  state?: number | null;
  city?: number | null;
  postalCode?: string | null;
  otherAddress?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  farms?: IFarm[] | null;
  sensors?: ISensor[] | null;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public buildingNo?: number | null,
    public street?: string | null,
    public area?: string | null,
    public country?: number | null,
    public state?: number | null,
    public city?: number | null,
    public postalCode?: string | null,
    public otherAddress?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public farms?: IFarm[] | null,
    public sensors?: ISensor[] | null
  ) {}
}

export function getLocationIdentifier(location: ILocation): number | undefined {
  return location.id;
}
