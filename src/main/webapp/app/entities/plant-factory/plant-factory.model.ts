import dayjs from 'dayjs/esm';
import { IZone } from 'app/entities/zone/zone.model';
import { IDevice } from 'app/entities/device/device.model';
import { IClimate } from 'app/entities/climate/climate.model';
import { IIrrigation } from 'app/entities/irrigation/irrigation.model';
import { IDosing } from 'app/entities/dosing/dosing.model';
import { ILight } from 'app/entities/light/light.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { IPest } from 'app/entities/pest/pest.model';
import { IDisease } from 'app/entities/disease/disease.model';
import { IFarm } from 'app/entities/farm/farm.model';
import { ProFarmType } from 'app/entities/enumerations/pro-farm-type.model';
import { ProSubType } from 'app/entities/enumerations/pro-sub-type.model';

export interface IPlantFactory {
  id?: number;
  gUID?: string | null;
  plantFactoryName?: string | null;
  plantFactoryTypeID?: ProFarmType | null;
  plantFactorySubType?: ProSubType | null;
  plantFactoryDescription?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  zones?: IZone[] | null;
  devices?: IDevice[] | null;
  climates?: IClimate[] | null;
  irrigations?: IIrrigation[] | null;
  dosings?: IDosing[] | null;
  lights?: ILight[] | null;
  crops?: ICrop[] | null;
  calendars?: ICalendar[] | null;
  scoutings?: IScouting[] | null;
  pests?: IPest[] | null;
  diseases?: IDisease[] | null;
  farmID?: IFarm | null;
  deviceID?: IDevice | null;
}

export class PlantFactory implements IPlantFactory {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public plantFactoryName?: string | null,
    public plantFactoryTypeID?: ProFarmType | null,
    public plantFactorySubType?: ProSubType | null,
    public plantFactoryDescription?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public zones?: IZone[] | null,
    public devices?: IDevice[] | null,
    public climates?: IClimate[] | null,
    public irrigations?: IIrrigation[] | null,
    public dosings?: IDosing[] | null,
    public lights?: ILight[] | null,
    public crops?: ICrop[] | null,
    public calendars?: ICalendar[] | null,
    public scoutings?: IScouting[] | null,
    public pests?: IPest[] | null,
    public diseases?: IDisease[] | null,
    public farmID?: IFarm | null,
    public deviceID?: IDevice | null
  ) {}
}

export function getPlantFactoryIdentifier(plantFactory: IPlantFactory): number | undefined {
  return plantFactory.id;
}
