import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';

export interface IDeviceModel {
  id?: number;
  gUID?: string | null;
  modelName?: string | null;
  type?: number | null;
  manufacturer?: string | null;
  productCode?: number | null;
  properties?: number | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  devices?: IDevice[] | null;
}

export class DeviceModel implements IDeviceModel {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public modelName?: string | null,
    public type?: number | null,
    public manufacturer?: string | null,
    public productCode?: number | null,
    public properties?: number | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public devices?: IDevice[] | null
  ) {}
}

export function getDeviceModelIdentifier(deviceModel: IDeviceModel): number | undefined {
  return deviceModel.id;
}
