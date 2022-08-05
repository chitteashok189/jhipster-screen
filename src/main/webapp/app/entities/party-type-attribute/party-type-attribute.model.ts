import dayjs from 'dayjs/esm';

export interface IPartyTypeAttribute {
  id?: number;
  gUID?: number | null;
  attributeName?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class PartyTypeAttribute implements IPartyTypeAttribute {
  constructor(
    public id?: number,
    public gUID?: number | null,
    public attributeName?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getPartyTypeAttributeIdentifier(partyTypeAttribute: IPartyTypeAttribute): number | undefined {
  return partyTypeAttribute.id;
}
