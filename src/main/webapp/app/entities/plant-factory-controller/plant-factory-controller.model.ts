import dayjs from 'dayjs/esm';
import { PlantSource } from 'app/entities/enumerations/plant-source.model';

export interface IPlantFactoryController {
  id?: number;
  gUID?: string | null;
  source?: PlantSource | null;
  airTemperature?: number | null;
  relativeHumidity?: number | null;
  vPD?: number | null;
  evapotranspiration?: number | null;
  barometricPressure?: number | null;
  seaLevelPressure?: number | null;
  co2Level?: number | null;
  nutrientTankLevel?: number | null;
  dewPoint?: number | null;
  heatIndex?: number | null;
  turbidity?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class PlantFactoryController implements IPlantFactoryController {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public source?: PlantSource | null,
    public airTemperature?: number | null,
    public relativeHumidity?: number | null,
    public vPD?: number | null,
    public evapotranspiration?: number | null,
    public barometricPressure?: number | null,
    public seaLevelPressure?: number | null,
    public co2Level?: number | null,
    public nutrientTankLevel?: number | null,
    public dewPoint?: number | null,
    public heatIndex?: number | null,
    public turbidity?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getPlantFactoryControllerIdentifier(plantFactoryController: IPlantFactoryController): number | undefined {
  return plantFactoryController.id;
}
