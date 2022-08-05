import dayjs from 'dayjs/esm';
import { ICrop } from 'app/entities/crop/crop.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { SeaType } from 'app/entities/enumerations/sea-type.model';
import { SeaTime } from 'app/entities/enumerations/sea-time.model';

export interface ISeason {
  id?: number;
  gUID?: string | null;
  seasonType?: SeaType | null;
  cropName?: string | null;
  area?: number | null;
  seasonTime?: SeaTime | null;
  seasonstartDate?: dayjs.Dayjs | null;
  seasonEndDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  crops?: ICrop[] | null;
  calendars?: ICalendar[] | null;
}

export class Season implements ISeason {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public seasonType?: SeaType | null,
    public cropName?: string | null,
    public area?: number | null,
    public seasonTime?: SeaTime | null,
    public seasonstartDate?: dayjs.Dayjs | null,
    public seasonEndDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public crops?: ICrop[] | null,
    public calendars?: ICalendar[] | null
  ) {}
}

export function getSeasonIdentifier(season: ISeason): number | undefined {
  return season.id;
}
