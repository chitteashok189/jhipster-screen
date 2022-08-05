import dayjs from 'dayjs/esm';
import { IDevice } from 'app/entities/device/device.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { IrriSource } from 'app/entities/enumerations/irri-source.model';

export interface IIrrigation {
  id?: number;
  gUID?: string | null;
  source?: IrriSource | null;
  nutrientLevel?: number | null;
  solarRadiation?: number | null;
  inletFlow?: number | null;
  outletFlow?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  deviceID?: IDevice | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Irrigation implements IIrrigation {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public source?: IrriSource | null,
    public nutrientLevel?: number | null,
    public solarRadiation?: number | null,
    public inletFlow?: number | null,
    public outletFlow?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public deviceID?: IDevice | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getIrrigationIdentifier(irrigation: IIrrigation): number | undefined {
  return irrigation.id;
}
