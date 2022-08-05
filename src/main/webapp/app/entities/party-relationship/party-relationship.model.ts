import dayjs from 'dayjs/esm';
import { IPartyRelationshipType } from 'app/entities/party-relationship-type/party-relationship-type.model';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';

export interface IPartyRelationship {
  id?: number;
  gUID?: string | null;
  partyIdTo?: number | null;
  partyIdFrom?: number | null;
  roleTypeIdFrom?: number | null;
  roleTypeIdTo?: number | null;
  fromDate?: dayjs.Dayjs | null;
  thruDate?: dayjs.Dayjs | null;
  relationshipName?: string | null;
  positionTitle?: string | null;
  comments?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyRelationshipTypes?: IPartyRelationshipType[] | null;
  partyRelationshipTypeID?: IPartyRelationshipType | null;
  securityGroupID?: ISecurityGroup | null;
}

export class PartyRelationship implements IPartyRelationship {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public partyIdTo?: number | null,
    public partyIdFrom?: number | null,
    public roleTypeIdFrom?: number | null,
    public roleTypeIdTo?: number | null,
    public fromDate?: dayjs.Dayjs | null,
    public thruDate?: dayjs.Dayjs | null,
    public relationshipName?: string | null,
    public positionTitle?: string | null,
    public comments?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyRelationshipTypes?: IPartyRelationshipType[] | null,
    public partyRelationshipTypeID?: IPartyRelationshipType | null,
    public securityGroupID?: ISecurityGroup | null
  ) {}
}

export function getPartyRelationshipIdentifier(partyRelationship: IPartyRelationship): number | undefined {
  return partyRelationship.id;
}
