import dayjs from 'dayjs/esm';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { IRoleTypeAttribute } from 'app/entities/role-type-attribute/role-type-attribute.model';

export interface IRoleType {
  id?: number;
  gUID?: string | null;
  hasTable?: boolean | null;
  description?: string | null;
  childRoleType?: number | null;
  validPartyRelationshipType?: number | null;
  validFromPartyRelationshipType?: number | null;
  partyInvitationRoleAssociation?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyRoles?: IPartyRole[] | null;
  roleTypeAttributes?: IRoleTypeAttribute[] | null;
  partyRoleID?: IPartyRole | null;
  roleTypeAttributeID?: IRoleTypeAttribute | null;
}

export class RoleType implements IRoleType {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public hasTable?: boolean | null,
    public description?: string | null,
    public childRoleType?: number | null,
    public validPartyRelationshipType?: number | null,
    public validFromPartyRelationshipType?: number | null,
    public partyInvitationRoleAssociation?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyRoles?: IPartyRole[] | null,
    public roleTypeAttributes?: IRoleTypeAttribute[] | null,
    public partyRoleID?: IPartyRole | null,
    public roleTypeAttributeID?: IRoleTypeAttribute | null
  ) {
    this.hasTable = this.hasTable ?? false;
  }
}

export function getRoleTypeIdentifier(roleType: IRoleType): number | undefined {
  return roleType.id;
}
