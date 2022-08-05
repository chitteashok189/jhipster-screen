import dayjs from 'dayjs/esm';
import { RemItem } from 'app/entities/enumerations/rem-item.model';

export interface IReminder {
  id?: number;
  gUID?: string | null;
  name?: string | null;
  date?: dayjs.Dayjs | null;
  time?: number | null;
  item?: RemItem | null;
  description?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class Reminder implements IReminder {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public name?: string | null,
    public date?: dayjs.Dayjs | null,
    public time?: number | null,
    public item?: RemItem | null,
    public description?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getReminderIdentifier(reminder: IReminder): number | undefined {
  return reminder.id;
}
