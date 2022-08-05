import dayjs from 'dayjs/esm';
import { IWeather } from 'app/entities/weather/weather.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { IZone } from 'app/entities/zone/zone.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ITool } from 'app/entities/tool/tool.model';
import { ISeason } from 'app/entities/season/season.model';

export interface ICalendar {
  id?: number;
  gUID?: string | null;
  calenderDate?: dayjs.Dayjs | null;
  calenderYear?: number | null;
  dayOfWeek?: number | null;
  monthOfYear?: number | null;
  weekOfMonth?: number | null;
  weekOfQuarter?: number | null;
  weekOfYear?: number | null;
  dayOfQuarter?: number | null;
  dayOfYear?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  weathers?: IWeather[] | null;
  plantFactoryID?: IPlantFactory | null;
  zoneID?: IZone | null;
  cropID?: ICrop | null;
  toolID?: ITool | null;
  seasonID?: ISeason | null;
}

export class Calendar implements ICalendar {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public calenderDate?: dayjs.Dayjs | null,
    public calenderYear?: number | null,
    public dayOfWeek?: number | null,
    public monthOfYear?: number | null,
    public weekOfMonth?: number | null,
    public weekOfQuarter?: number | null,
    public weekOfYear?: number | null,
    public dayOfQuarter?: number | null,
    public dayOfYear?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public weathers?: IWeather[] | null,
    public plantFactoryID?: IPlantFactory | null,
    public zoneID?: IZone | null,
    public cropID?: ICrop | null,
    public toolID?: ITool | null,
    public seasonID?: ISeason | null
  ) {}
}

export function getCalendarIdentifier(calendar: ICalendar): number | undefined {
  return calendar.id;
}
