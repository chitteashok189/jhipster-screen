import dayjs from 'dayjs/esm';
import { ICrop } from 'app/entities/crop/crop.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { ToolType } from 'app/entities/enumerations/tool-type.model';

export interface ITool {
  id?: number;
  gUID?: string | null;
  toolType?: ToolType | null;
  toolName?: string | null;
  manufacturer?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  crops?: ICrop[] | null;
  calendars?: ICalendar[] | null;
}

export class Tool implements ITool {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public toolType?: ToolType | null,
    public toolName?: string | null,
    public manufacturer?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public crops?: ICrop[] | null,
    public calendars?: ICalendar[] | null
  ) {}
}

export function getToolIdentifier(tool: ITool): number | undefined {
  return tool.id;
}
