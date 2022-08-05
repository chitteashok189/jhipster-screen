import dayjs from 'dayjs/esm';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';

export interface IApplicationUserSecurityGroup {
  id?: number;
  gUID?: string | null;
  fromDate?: dayjs.Dayjs | null;
  thruDate?: dayjs.Dayjs | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  securityGroups?: ISecurityGroup[] | null;
  securityGroupID?: ISecurityGroup | null;
}

export class ApplicationUserSecurityGroup implements IApplicationUserSecurityGroup {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public fromDate?: dayjs.Dayjs | null,
    public thruDate?: dayjs.Dayjs | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public securityGroups?: ISecurityGroup[] | null,
    public securityGroupID?: ISecurityGroup | null
  ) {}
}

export function getApplicationUserSecurityGroupIdentifier(applicationUserSecurityGroup: IApplicationUserSecurityGroup): number | undefined {
  return applicationUserSecurityGroup.id;
}
