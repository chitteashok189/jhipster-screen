import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { CliSource } from 'app/entities/enumerations/cli-source.model';

export interface IClimate {
  id?: number;
  gUID?: string | null;
  source?: CliSource | null;
  airTemperature?: number | null;
  relativeHumidity?: number | null;
  vPD?: number | null;
  evapotranspiration?: number | null;
  barometricPressure?: number | null;
  seaLevelPressure?: number | null;
  co2Level?: number | null;
  dewPoint?: number | null;
  solarRadiation?: number | null;
  heatIndex?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  deviceID?: IDevice | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Climate implements IClimate {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public source?: CliSource | null,
    public airTemperature?: number | null,
    public relativeHumidity?: number | null,
    public vPD?: number | null,
    public evapotranspiration?: number | null,
    public barometricPressure?: number | null,
    public seaLevelPressure?: number | null,
    public co2Level?: number | null,
    public dewPoint?: number | null,
    public solarRadiation?: number | null,
    public heatIndex?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public deviceID?: IDevice | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getClimateIdentifier(climate: IClimate): number | undefined {
  return climate.id;
}
