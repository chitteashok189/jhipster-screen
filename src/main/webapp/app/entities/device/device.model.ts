import dayjs from 'dayjs/esm';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { ISensor } from 'app/entities/sensor/sensor.model';
import { IClimate } from 'app/entities/climate/climate.model';
import { IIrrigation } from 'app/entities/irrigation/irrigation.model';
import { IDosing } from 'app/entities/dosing/dosing.model';
import { ILight } from 'app/entities/light/light.model';
import { IDeviceLevel } from 'app/entities/device-level/device-level.model';
import { IDeviceModel } from 'app/entities/device-model/device-model.model';
import { DeviceType } from 'app/entities/enumerations/device-type.model';

export interface IDevice {
  id?: number;
  gUID?: string | null;
  deviceModel?: string | null;
  deviceAsset?: string | null;
  deviceType?: DeviceType | null;
  hardwareID?: number | null;
  reportingIntervalLocation?: number | null;
  parentDevice?: string | null;
  properties?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  plantFactories?: IPlantFactory[] | null;
  sensors?: ISensor[] | null;
  climates?: IClimate[] | null;
  irrigations?: IIrrigation[] | null;
  dosings?: IDosing[] | null;
  lights?: ILight[] | null;
  plantFactoryID?: IPlantFactory | null;
  deviceLevelID?: IDeviceLevel | null;
  deviceModelID?: IDeviceModel | null;
}

export class Device implements IDevice {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public deviceModel?: string | null,
    public deviceAsset?: string | null,
    public deviceType?: DeviceType | null,
    public hardwareID?: number | null,
    public reportingIntervalLocation?: number | null,
    public parentDevice?: string | null,
    public properties?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public plantFactories?: IPlantFactory[] | null,
    public sensors?: ISensor[] | null,
    public climates?: IClimate[] | null,
    public irrigations?: IIrrigation[] | null,
    public dosings?: IDosing[] | null,
    public lights?: ILight[] | null,
    public plantFactoryID?: IPlantFactory | null,
    public deviceLevelID?: IDeviceLevel | null,
    public deviceModelID?: IDeviceModel | null
  ) {}
}

export function getDeviceIdentifier(device: IDevice): number | undefined {
  return device.id;
}
