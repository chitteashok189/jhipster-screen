import dayjs from 'dayjs/esm';
import { IPestControl } from 'app/entities/pest-control/pest-control.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { ThreatLevel } from 'app/entities/enumerations/threat-level.model';

export interface IPest {
  id?: number;
  gUID?: string | null;
  threatLevel?: ThreatLevel | null;
  name?: string | null;
  scientificName?: string | null;
  attachements?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  pestControls?: IPestControl[] | null;
  symptoms?: ISymptom[] | null;
  scoutingID?: IScouting | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Pest implements IPest {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public threatLevel?: ThreatLevel | null,
    public name?: string | null,
    public scientificName?: string | null,
    public attachements?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public pestControls?: IPestControl[] | null,
    public symptoms?: ISymptom[] | null,
    public scoutingID?: IScouting | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getPestIdentifier(pest: IPest): number | undefined {
  return pest.id;
}
