import dayjs from 'dayjs/esm';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';

export interface ISecurityPermission {
  id?: number;
  gUID?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  securityGroupPermissions?: ISecurityGroupPermission[] | null;
  securityGroupPermissionID?: ISecurityGroupPermission | null;
}

export class SecurityPermission implements ISecurityPermission {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public securityGroupPermissions?: ISecurityGroupPermission[] | null,
    public securityGroupPermissionID?: ISecurityGroupPermission | null
  ) {}
}

export function getSecurityPermissionIdentifier(securityPermission: ISecurityPermission): number | undefined {
  return securityPermission.id;
}
