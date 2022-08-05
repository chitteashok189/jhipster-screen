import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IPartyGroup {
  id?: number;
  gUID?: number | null;
  groupName?: string | null;
  localGroupName?: string | null;
  officeSiteName?: string | null;
  comments?: string | null;
  logoImageUrl?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  parties?: IParty[] | null;
}

export class PartyGroup implements IPartyGroup {
  constructor(
    public id?: number,
    public gUID?: number | null,
    public groupName?: string | null,
    public localGroupName?: string | null,
    public officeSiteName?: string | null,
    public comments?: string | null,
    public logoImageUrl?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public parties?: IParty[] | null
  ) {}
}

export function getPartyGroupIdentifier(partyGroup: IPartyGroup): number | undefined {
  return partyGroup.id;
}
