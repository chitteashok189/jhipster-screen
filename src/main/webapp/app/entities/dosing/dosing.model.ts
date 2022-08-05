import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { DosingSource } from 'app/entities/enumerations/dosing-source.model';

export interface IDosing {
  id?: number;
  gUID?: string | null;
  source?: DosingSource | null;
  pH?: number | null;
  eC?: number | null;
  dO?: number | null;
  nutrientTemperature?: number | null;
  solarRadiation?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  deviceID?: IDevice | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Dosing implements IDosing {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public source?: DosingSource | null,
    public pH?: number | null,
    public eC?: number | null,
    public dO?: number | null,
    public nutrientTemperature?: number | null,
    public solarRadiation?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public deviceID?: IDevice | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getDosingIdentifier(dosing: IDosing): number | undefined {
  return dosing.id;
}
