import dayjs from 'dayjs/esm';
import { IPartyAttribute } from 'app/entities/party-attribute/party-attribute.model';
import { IPartyClassification } from 'app/entities/party-classification/party-classification.model';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { IPartyNote } from 'app/entities/party-note/party-note.model';
import { IPerson } from 'app/entities/person/person.model';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { IFarm } from 'app/entities/farm/farm.model';
import { IPartyGroup } from 'app/entities/party-group/party-group.model';
import { IPartyType } from 'app/entities/party-type/party-type.model';

export interface IParty {
  id?: number;
  gUID?: string | null;
  partyName?: string | null;
  statusID?: boolean | null;
  description?: string | null;
  externalID?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  partyAttributes?: IPartyAttribute[] | null;
  partyClassifications?: IPartyClassification[] | null;
  partyRoles?: IPartyRole[] | null;
  partyNotes?: IPartyNote[] | null;
  people?: IPerson[] | null;
  applicationUsers?: IApplicationUser[] | null;
  farms?: IFarm[] | null;
  partyGroupID?: IPartyGroup | null;
  partyTypeID?: IPartyType | null;
  applicationUserID?: IApplicationUser | null;
  partyRoleID?: IPartyRole | null;
  personID?: IPerson | null;
}

export class Party implements IParty {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public partyName?: string | null,
    public statusID?: boolean | null,
    public description?: string | null,
    public externalID?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public partyAttributes?: IPartyAttribute[] | null,
    public partyClassifications?: IPartyClassification[] | null,
    public partyRoles?: IPartyRole[] | null,
    public partyNotes?: IPartyNote[] | null,
    public people?: IPerson[] | null,
    public applicationUsers?: IApplicationUser[] | null,
    public farms?: IFarm[] | null,
    public partyGroupID?: IPartyGroup | null,
    public partyTypeID?: IPartyType | null,
    public applicationUserID?: IApplicationUser | null,
    public partyRoleID?: IPartyRole | null,
    public personID?: IPerson | null
  ) {
    this.statusID = this.statusID ?? false;
  }
}

export function getPartyIdentifier(party: IParty): number | undefined {
  return party.id;
}
