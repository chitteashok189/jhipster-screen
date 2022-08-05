import dayjs from 'dayjs/esm';
import { IPartyRelationship } from 'app/entities/party-relationship/party-relationship.model';

export interface IPartyRelationshipType {
  id?: number;
  gUID?: number | null;
  hasTable?: boolean | null;
  partyRelationshipName?: string | null;
  description?: string | null;
  roleTypeIdValidFrom?: number | null;
  roleTypeIdValidTo?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyRelationships?: IPartyRelationship[] | null;
  partyRelationshipID?: IPartyRelationship | null;
}

export class PartyRelationshipType implements IPartyRelationshipType {
  constructor(
    public id?: number,
    public gUID?: number | null,
    public hasTable?: boolean | null,
    public partyRelationshipName?: string | null,
    public description?: string | null,
    public roleTypeIdValidFrom?: number | null,
    public roleTypeIdValidTo?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyRelationships?: IPartyRelationship[] | null,
    public partyRelationshipID?: IPartyRelationship | null
  ) {
    this.hasTable = this.hasTable ?? false;
  }
}

export function getPartyRelationshipTypeIdentifier(partyRelationshipType: IPartyRelationshipType): number | undefined {
  return partyRelationshipType.id;
}
