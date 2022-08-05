import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';
import { IRoleType } from 'app/entities/role-type/role-type.model';

export interface IPartyRole {
  id?: number;
  gUID?: string | null;
  fromAgreement?: string | null;
  toAgreement?: string | null;
  fromCommunicationEvent?: string | null;
  toCommunicationEvent?: string | null;
  partyIdFrom?: number | null;
  partyIdTO?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  parties?: IParty[] | null;
  roleTypes?: IRoleType[] | null;
  roleTypeID?: IRoleType | null;
  partyID?: IParty | null;
}

export class PartyRole implements IPartyRole {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public fromAgreement?: string | null,
    public toAgreement?: string | null,
    public fromCommunicationEvent?: string | null,
    public toCommunicationEvent?: string | null,
    public partyIdFrom?: number | null,
    public partyIdTO?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public parties?: IParty[] | null,
    public roleTypes?: IRoleType[] | null,
    public roleTypeID?: IRoleType | null,
    public partyID?: IParty | null
  ) {}
}

export function getPartyRoleIdentifier(partyRole: IPartyRole): number | undefined {
  return partyRole.id;
}
