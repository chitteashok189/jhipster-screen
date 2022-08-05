import dayjs from 'dayjs/esm';
import { IDiseaseControl } from 'app/entities/disease-control/disease-control.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { IDisease } from 'app/entities/disease/disease.model';
import { IPest } from 'app/entities/pest/pest.model';

export interface ISymptom {
  id?: number;
  gUID?: string | null;
  observation?: string | null;
  symptomImageContentType?: string | null;
  symptomImage?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  diseaseControls?: IDiseaseControl[] | null;
  scoutingID?: IScouting | null;
  diseaseID?: IDisease | null;
  pestID?: IPest | null;
}

export class Symptom implements ISymptom {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public observation?: string | null,
    public symptomImageContentType?: string | null,
    public symptomImage?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public diseaseControls?: IDiseaseControl[] | null,
    public scoutingID?: IScouting | null,
    public diseaseID?: IDisease | null,
    public pestID?: IPest | null
  ) {}
}

export function getSymptomIdentifier(symptom: ISymptom): number | undefined {
  return symptom.id;
}
