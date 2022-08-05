import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IApplicationUser {
  id?: number;
  gUID?: string | null;
  currentPassword?: string | null;
  passwordHint?: string | null;
  isSystemEnables?: boolean | null;
  hasLoggedOut?: boolean | null;
  requirePasswordChange?: boolean | null;
  lastCurrencyUom?: number | null;
  lastLocale?: number | null;
  lastTimeZone?: number | null;
  disabledDateTime?: dayjs.Dayjs | null;
  successiveFailedLogins?: number | null;
  applicationUserSecurityGroup?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  parties?: IParty[] | null;
  partyID?: IParty | null;
}

export class ApplicationUser implements IApplicationUser {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public currentPassword?: string | null,
    public passwordHint?: string | null,
    public isSystemEnables?: boolean | null,
    public hasLoggedOut?: boolean | null,
    public requirePasswordChange?: boolean | null,
    public lastCurrencyUom?: number | null,
    public lastLocale?: number | null,
    public lastTimeZone?: number | null,
    public disabledDateTime?: dayjs.Dayjs | null,
    public successiveFailedLogins?: number | null,
    public applicationUserSecurityGroup?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public parties?: IParty[] | null,
    public partyID?: IParty | null
  ) {
    this.isSystemEnables = this.isSystemEnables ?? false;
    this.hasLoggedOut = this.hasLoggedOut ?? false;
    this.requirePasswordChange = this.requirePasswordChange ?? false;
  }
}

export function getApplicationUserIdentifier(applicationUser: IApplicationUser): number | undefined {
  return applicationUser.id;
}
