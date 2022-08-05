import dayjs from 'dayjs/esm';
import { IRoleType } from 'app/entities/role-type/role-type.model';

export interface IRoleTypeAttribute {
  id?: number;
  gUID?: string | null;
  attributeName?: string | null;
  description?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  roleTypes?: IRoleType[] | null;
  roleTypeID?: IRoleType | null;
}

export class RoleTypeAttribute implements IRoleTypeAttribute {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public attributeName?: string | null,
    public description?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public roleTypes?: IRoleType[] | null,
    public roleTypeID?: IRoleType | null
  ) {}
}

export function getRoleTypeAttributeIdentifier(roleTypeAttribute: IRoleTypeAttribute): number | undefined {
  return roleTypeAttribute.id;
}
