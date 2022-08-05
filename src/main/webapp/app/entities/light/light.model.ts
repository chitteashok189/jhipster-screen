import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { LightSource } from 'app/entities/enumerations/light-source.model';

export interface ILight {
  id?: number;
  gUID?: string | null;
  source?: LightSource | null;
  lightIntensity?: number | null;
  dailyLightIntegral?: number | null;
  pAR?: number | null;
  pPFD?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  deviceID?: IDevice | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Light implements ILight {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public source?: LightSource | null,
    public lightIntensity?: number | null,
    public dailyLightIntegral?: number | null,
    public pAR?: number | null,
    public pPFD?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public deviceID?: IDevice | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getLightIdentifier(light: ILight): number | undefined {
  return light.id;
}
