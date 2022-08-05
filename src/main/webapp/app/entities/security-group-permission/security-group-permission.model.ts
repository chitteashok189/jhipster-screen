import dayjs from 'dayjs/esm';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';

export interface ISecurityGroupPermission {
  id?: number;
  gUID?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  securityGroups?: ISecurityGroup[] | null;
  securityPermissions?: ISecurityPermission[] | null;
  securityGroupID?: ISecurityGroup | null;
  securityPermissionID?: ISecurityPermission | null;
}

export class SecurityGroupPermission implements ISecurityGroupPermission {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public securityGroups?: ISecurityGroup[] | null,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityGroupID?: ISecurityGroup | null,
    public securityPermissionID?: ISecurityPermission | null
  ) {}
}

export function getSecurityGroupPermissionIdentifier(securityGroupPermission: ISecurityGroupPermission): number | undefined {
  return securityGroupPermission.id;
}
