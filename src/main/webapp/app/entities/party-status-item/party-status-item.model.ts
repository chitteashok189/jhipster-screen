import dayjs from 'dayjs/esm';

export interface IPartyStatusItem {
  id?: number;
  gUID?: string | null;
  statusCode?: number | null;
  sequenceID?: number | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class PartyStatusItem implements IPartyStatusItem {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public statusCode?: number | null,
    public sequenceID?: number | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getPartyStatusItemIdentifier(partyStatusItem: IPartyStatusItem): number | undefined {
  return partyStatusItem.id;
}
