import dayjs from 'dayjs/esm';
import { ISensor } from 'app/entities/sensor/sensor.model';

export interface ISensorModel {
  id?: number;
  gUID?: string | null;
  sensorType?: number | null;
  manufacturer?: string | null;
  productCode?: string | null;
  sensorMeasure?: number | null;
  modelName?: string | null;
  properties?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  sensors?: ISensor[] | null;
}

export class SensorModel implements ISensorModel {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public sensorType?: number | null,
    public manufacturer?: string | null,
    public productCode?: string | null,
    public sensorMeasure?: number | null,
    public modelName?: string | null,
    public properties?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public sensors?: ISensor[] | null
  ) {}
}

export function getSensorModelIdentifier(sensorModel: ISensorModel): number | undefined {
  return sensorModel.id;
}
