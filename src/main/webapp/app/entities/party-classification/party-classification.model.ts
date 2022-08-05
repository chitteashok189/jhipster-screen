import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IPartyClassification {
  id?: number;
  gUID?: string | null;
  fromDate?: dayjs.Dayjs | null;
  thruDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyID?: IParty | null;
}

export class PartyClassification implements IPartyClassification {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public fromDate?: dayjs.Dayjs | null,
    public thruDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyID?: IParty | null
  ) {}
}

export function getPartyClassificationIdentifier(partyClassification: IPartyClassification): number | undefined {
  return partyClassification.id;
}
