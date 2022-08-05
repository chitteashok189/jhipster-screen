import dayjs from 'dayjs/esm';
import { IPartyRelationship } from 'app/entities/party-relationship/party-relationship.model';
import { IApplicationUserSecurityGroup } from 'app/entities/application-user-security-group/application-user-security-group.model';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';

export interface ISecurityGroup {
  id?: number;
  gUID?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyRelationships?: IPartyRelationship[] | null;
  applicationUserSecurityGroups?: IApplicationUserSecurityGroup[] | null;
  securityGroupPermissions?: ISecurityGroupPermission[] | null;
  applicationUserSecurityGroupID?: IApplicationUserSecurityGroup | null;
  securityGroupPermissionID?: ISecurityGroupPermission | null;
}

export class SecurityGroup implements ISecurityGroup {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyRelationships?: IPartyRelationship[] | null,
    public applicationUserSecurityGroups?: IApplicationUserSecurityGroup[] | null,
    public securityGroupPermissions?: ISecurityGroupPermission[] | null,
    public applicationUserSecurityGroupID?: IApplicationUserSecurityGroup | null,
    public securityGroupPermissionID?: ISecurityGroupPermission | null
  ) {}
}

export function getSecurityGroupIdentifier(securityGroup: ISecurityGroup): number | undefined {
  return securityGroup.id;
}
