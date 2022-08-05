import dayjs from 'dayjs/esm';
import { AlertType } from 'app/entities/enumerations/alert-type.model';
import { PreType } from 'app/entities/enumerations/pre-type.model';
import { AlertStatus } from 'app/entities/enumerations/alert-status.model';
import { Remediation } from 'app/entities/enumerations/remediation.model';

export interface IAlert {
  id?: number;
  gUID?: string | null;
  name?: string | null;
  alertType?: AlertType | null;
  description?: string | null;
  datePeriod?: number | null;
  durationDays?: number | null;
  minimumTemperature?: number | null;
  maximumTemperature?: number | null;
  minHumidity?: number | null;
  maxHumidity?: number | null;
  precipitationType?: PreType | null;
  minPrecipitation?: number | null;
  maxPrecipitation?: number | null;
  status?: AlertStatus | null;
  remediation?: Remediation | null;
  farmAssigned?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class Alert implements IAlert {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public name?: string | null,
    public alertType?: AlertType | null,
    public description?: string | null,
    public datePeriod?: number | null,
    public durationDays?: number | null,
    public minimumTemperature?: number | null,
    public maximumTemperature?: number | null,
    public minHumidity?: number | null,
    public maxHumidity?: number | null,
    public precipitationType?: PreType | null,
    public minPrecipitation?: number | null,
    public maxPrecipitation?: number | null,
    public status?: AlertStatus | null,
    public remediation?: Remediation | null,
    public farmAssigned?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getAlertIdentifier(alert: IAlert): number | undefined {
  return alert.id;
}
