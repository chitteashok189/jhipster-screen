import dayjs from 'dayjs/esm';
import { IDisease } from 'app/entities/disease/disease.model';
import { ICrop } from 'app/entities/crop/crop.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { DisConType } from 'app/entities/enumerations/dis-con-type.model';

export interface IDiseaseControl {
  id?: number;
  gUID?: string | null;
  controlType?: DisConType | null;
  treatment?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  diseaseID?: IDisease | null;
  cropID?: ICrop | null;
  symptomID?: ISymptom | null;
}

export class DiseaseControl implements IDiseaseControl {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public controlType?: DisConType | null,
    public treatment?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public diseaseID?: IDisease | null,
    public cropID?: ICrop | null,
    public symptomID?: ISymptom | null
  ) {}
}

export function getDiseaseControlIdentifier(diseaseControl: IDiseaseControl): number | undefined {
  return diseaseControl.id;
}
