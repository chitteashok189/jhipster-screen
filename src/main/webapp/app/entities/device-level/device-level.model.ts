import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';

export interface IDeviceLevel {
  id?: number;
  gUID?: string | null;
  levelName?: string | null;
  deviceLevelType?: number | null;
  manufacturer?: string | null;
  productCode?: number | null;
  ports?: number | null;
  properties?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  devices?: IDevice[] | null;
}

export class DeviceLevel implements IDeviceLevel {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public levelName?: string | null,
    public deviceLevelType?: number | null,
    public manufacturer?: string | null,
    public productCode?: number | null,
    public ports?: number | null,
    public properties?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public devices?: IDevice[] | null
  ) {}
}

export function getDeviceLevelIdentifier(deviceLevel: IDeviceLevel): number | undefined {
  return deviceLevel.id;
}
