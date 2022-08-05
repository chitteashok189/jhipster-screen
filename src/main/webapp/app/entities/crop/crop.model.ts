import dayjs from 'dayjs/esm';
import { IPlant } from 'app/entities/plant/plant.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { IPestControl } from 'app/entities/pest-control/pest-control.model';
import { IDiseaseControl } from 'app/entities/disease-control/disease-control.model';
import { IActivity } from 'app/entities/activity/activity.model';
import { IHarvest } from 'app/entities/harvest/harvest.model';
import { ILot } from 'app/entities/lot/lot.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { ITool } from 'app/entities/tool/tool.model';
import { ISeason } from 'app/entities/season/season.model';
import { IProduct } from 'app/entities/product/product.model';
import { CropTyp } from 'app/entities/enumerations/crop-typ.model';
import { Horti } from 'app/entities/enumerations/horti.model';
import { GroPhase } from 'app/entities/enumerations/gro-phase.model';
import { PlantingMat } from 'app/entities/enumerations/planting-mat.model';

export interface ICrop {
  id?: number;
  gUID?: string | null;
  cropCode?: string | null;
  cropName?: string | null;
  cropType?: CropTyp | null;
  horticultureType?: Horti | null;
  isHybrid?: boolean | null;
  cultivar?: string | null;
  sowingDate?: dayjs.Dayjs | null;
  sowingDepth?: number | null;
  rowSpacingMax?: number | null;
  rowSpacingMin?: number | null;
  seedDepthMax?: number | null;
  seedDepthMin?: number | null;
  seedSpacingMax?: number | null;
  seedSpacingMin?: number | null;
  yearlyCrops?: number | null;
  growingSeason?: string | null;
  growingPhase?: GroPhase | null;
  plantingDate?: dayjs.Dayjs | null;
  plantSpacing?: number | null;
  plantingMaterial?: PlantingMat | null;
  transplantationDate?: dayjs.Dayjs | null;
  fertigationscheduleID?: number | null;
  plannedYield?: number | null;
  actualYield?: number | null;
  yieldUnit?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  plants?: IPlant[] | null;
  calendars?: ICalendar[] | null;
  scoutings?: IScouting[] | null;
  pestControls?: IPestControl[] | null;
  diseaseControls?: IDiseaseControl[] | null;
  activities?: IActivity[] | null;
  harvests?: IHarvest[] | null;
  lots?: ILot[] | null;
  plantID?: IPlant | null;
  plantFactoryID?: IPlantFactory | null;
  toolID?: ITool | null;
  seasonID?: ISeason | null;
  productID?: IProduct | null;
}

export class Crop implements ICrop {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public cropCode?: string | null,
    public cropName?: string | null,
    public cropType?: CropTyp | null,
    public horticultureType?: Horti | null,
    public isHybrid?: boolean | null,
    public cultivar?: string | null,
    public sowingDate?: dayjs.Dayjs | null,
    public sowingDepth?: number | null,
    public rowSpacingMax?: number | null,
    public rowSpacingMin?: number | null,
    public seedDepthMax?: number | null,
    public seedDepthMin?: number | null,
    public seedSpacingMax?: number | null,
    public seedSpacingMin?: number | null,
    public yearlyCrops?: number | null,
    public growingSeason?: string | null,
    public growingPhase?: GroPhase | null,
    public plantingDate?: dayjs.Dayjs | null,
    public plantSpacing?: number | null,
    public plantingMaterial?: PlantingMat | null,
    public transplantationDate?: dayjs.Dayjs | null,
    public fertigationscheduleID?: number | null,
    public plannedYield?: number | null,
    public actualYield?: number | null,
    public yieldUnit?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public plants?: IPlant[] | null,
    public calendars?: ICalendar[] | null,
    public scoutings?: IScouting[] | null,
    public pestControls?: IPestControl[] | null,
    public diseaseControls?: IDiseaseControl[] | null,
    public activities?: IActivity[] | null,
    public harvests?: IHarvest[] | null,
    public lots?: ILot[] | null,
    public plantID?: IPlant | null,
    public plantFactoryID?: IPlantFactory | null,
    public toolID?: ITool | null,
    public seasonID?: ISeason | null,
    public productID?: IProduct | null
  ) {
    this.isHybrid = this.isHybrid ?? false;
  }
}

export function getCropIdentifier(crop: ICrop): number | undefined {
  return crop.id;
}
