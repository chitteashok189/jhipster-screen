import dayjs from 'dayjs/esm';

export interface IPartyStatusType {
  id?: number;
  gUID?: string | null;
  parentTypeID?: number | null;
  hasTable?: boolean | null;
  description?: string | null;
  childStatusType?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class PartyStatusType implements IPartyStatusType {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public parentTypeID?: number | null,
    public hasTable?: boolean | null,
    public description?: string | null,
    public childStatusType?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {
    this.hasTable = this.hasTable ?? false;
  }
}

export function getPartyStatusTypeIdentifier(partyStatusType: IPartyStatusType): number | undefined {
  return partyStatusType.id;
}
