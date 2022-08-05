import dayjs from 'dayjs/esm';

export interface IPartyStatus {
  id?: number;
  gUID?: string | null;
  statusDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class PartyStatus implements IPartyStatus {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public statusDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getPartyStatusIdentifier(partyStatus: IPartyStatus): number | undefined {
  return partyStatus.id;
}
