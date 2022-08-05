import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';
import { ParentType } from 'app/entities/enumerations/parent-type.model';

export interface IPartyType {
  id?: number;
  gUID?: string | null;
  parentTypeID?: ParentType | null;
  hasTable?: string | null;
  description?: string | null;
  partyTypeAttr?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  parties?: IParty[] | null;
}

export class PartyType implements IPartyType {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public parentTypeID?: ParentType | null,
    public hasTable?: string | null,
    public description?: string | null,
    public partyTypeAttr?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public parties?: IParty[] | null
  ) {}
}

export function getPartyTypeIdentifier(partyType: IPartyType): number | undefined {
  return partyType.id;
}
