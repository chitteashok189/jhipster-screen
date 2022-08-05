import dayjs from 'dayjs/esm';
import { IPest } from 'app/entities/pest/pest.model';
import { IDisease } from 'app/entities/disease/disease.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { IDisorder } from 'app/entities/disorder/disorder.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ScoutingType } from 'app/entities/enumerations/scouting-type.model';
import { CropState } from 'app/entities/enumerations/crop-state.model';

export interface IScouting {
  id?: number;
  gUID?: string | null;
  scoutingDate?: dayjs.Dayjs | null;
  scout?: string | null;
  scoutingType?: ScoutingType | null;
  scoutingCoordinates?: number | null;
  scoutingArea?: number | null;
  cropState?: CropState | null;
  comments?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  pests?: IPest[] | null;
  diseases?: IDisease[] | null;
  symptoms?: ISymptom[] | null;
  disorders?: IDisorder[] | null;
  plantFactoryID?: IPlantFactory | null;
  cropID?: ICrop | null;
}

export class Scouting implements IScouting {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public scoutingDate?: dayjs.Dayjs | null,
    public scout?: string | null,
    public scoutingType?: ScoutingType | null,
    public scoutingCoordinates?: number | null,
    public scoutingArea?: number | null,
    public cropState?: CropState | null,
    public comments?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public pests?: IPest[] | null,
    public diseases?: IDisease[] | null,
    public symptoms?: ISymptom[] | null,
    public disorders?: IDisorder[] | null,
    public plantFactoryID?: IPlantFactory | null,
    public cropID?: ICrop | null
  ) {}
}

export function getScoutingIdentifier(scouting: IScouting): number | undefined {
  return scouting.id;
}
