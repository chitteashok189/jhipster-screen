import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IPartyNote {
  id?: number;
  gUID?: string | null;
  noteID?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyID?: IParty | null;
}

export class PartyNote implements IPartyNote {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public noteID?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyID?: IParty | null
  ) {}
}

export function getPartyNoteIdentifier(partyNote: IPartyNote): number | undefined {
  return partyNote.id;
}
