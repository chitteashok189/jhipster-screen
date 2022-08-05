import dayjs from 'dayjs/esm';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { IActivity } from 'app/entities/activity/activity.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { IGrowBed } from 'app/entities/grow-bed/grow-bed.model';
import { ZoneType } from 'app/entities/enumerations/zone-type.model';

export interface IZone {
  id?: number;
  gUID?: string | null;
  zoneType?: ZoneType | null;
  zoneName?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  calendars?: ICalendar[] | null;
  activities?: IActivity[] | null;
  plantFactoryID?: IPlantFactory | null;
  growBedID?: IGrowBed | null;
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public zoneType?: ZoneType | null,
    public zoneName?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public calendars?: ICalendar[] | null,
    public activities?: IActivity[] | null,
    public plantFactoryID?: IPlantFactory | null,
    public growBedID?: IGrowBed | null
  ) {}
}

export function getZoneIdentifier(zone: IZone): number | undefined {
  return zone.id;
}
