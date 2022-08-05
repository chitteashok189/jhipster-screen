import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IPartyAttribute {
  id?: number;
  gUID?: string | null;
  attributeName?: string | null;
  attributeValue?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyID?: IParty | null;
}

export class PartyAttribute implements IPartyAttribute {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public attributeName?: string | null,
    public attributeValue?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyID?: IParty | null
  ) {}
}

export function getPartyAttributeIdentifier(partyAttribute: IPartyAttribute): number | undefined {
  return partyAttribute.id;
}
