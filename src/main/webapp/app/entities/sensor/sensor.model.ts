import dayjs from 'dayjs/esm';
import { ILocation } from 'app/entities/location/location.model';
import { IDevice } from 'app/entities/device/device.model';
import { ISensorModel } from 'app/entities/sensor-model/sensor-model.model';

export interface ISensor {
  id?: number;
  gUID?: string | null;
  sensorName?: string | null;
  sensorModelName?: string | null;
  hardwareID?: number | null;
  port?: number | null;
  properties?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  locationID?: ILocation | null;
  deviceID?: IDevice | null;
  sensorModelID?: ISensorModel | null;
}

export class Sensor implements ISensor {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public sensorName?: string | null,
    public sensorModelName?: string | null,
    public hardwareID?: number | null,
    public port?: number | null,
    public properties?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public locationID?: ILocation | null,
    public deviceID?: IDevice | null,
    public sensorModelID?: ISensorModel | null
  ) {}
}

export function getSensorIdentifier(sensor: ISensor): number | undefined {
  return sensor.id;
}
