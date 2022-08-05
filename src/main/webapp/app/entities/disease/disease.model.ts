import dayjs from 'dayjs/esm';
import { IDiseaseControl } from 'app/entities/disease-control/disease-control.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { DisThreatLevel } from 'app/entities/enumerations/dis-threat-level.model';

export interface IDisease {
  id?: number;
  gUID?: string | null;
  threatLevel?: DisThreatLevel | null;
  name?: string | null;
  causalOrganism?: string | null;
  attachments?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  diseaseControls?: IDiseaseControl[] | null;
  symptoms?: ISymptom[] | null;
  scoutingID?: IScouting | null;
  plantFactoryID?: IPlantFactory | null;
}

export class Disease implements IDisease {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public threatLevel?: DisThreatLevel | null,
    public name?: string | null,
    public causalOrganism?: string | null,
    public attachments?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public diseaseControls?: IDiseaseControl[] | null,
    public symptoms?: ISymptom[] | null,
    public scoutingID?: IScouting | null,
    public plantFactoryID?: IPlantFactory | null
  ) {}
}

export function getDiseaseIdentifier(disease: IDisease): number | undefined {
  return disease.id;
}
