import dayjs from 'dayjs/esm';
import { IParty } from 'app/entities/party/party.model';

export interface IPerson {
  id?: number;
  gUID?: string | null;
  salutation?: string | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  personalTitle?: string | null;
  suffix?: string | null;
  nickName?: string | null;
  firstNameLocal?: string | null;
  middleNameLocal?: string | null;
  lastNameLocal?: string | null;
  otherLocal?: string | null;
  gender?: string | null;
  birthDate?: dayjs.Dayjs | null;
  heigth?: number | null;
  weight?: number | null;
  mothersMaidenName?: string | null;
  maritialStatus?: string | null;
  socialSecurityNo?: number | null;
  passportNo?: string | null;
  passportExpiryDate?: string | null;
  totalYearsWorkExperience?: number | null;
  comments?: string | null;
  occupation?: string | null;
  yearswithEmployer?: number | null;
  monthsWithEmployer?: number | null;
  existingCustomer?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  parties?: IParty[] | null;
  partyID?: IParty | null;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public salutation?: string | null,
    public firstName?: string | null,
    public middleName?: string | null,
    public lastName?: string | null,
    public personalTitle?: string | null,
    public suffix?: string | null,
    public nickName?: string | null,
    public firstNameLocal?: string | null,
    public middleNameLocal?: string | null,
    public lastNameLocal?: string | null,
    public otherLocal?: string | null,
    public gender?: string | null,
    public birthDate?: dayjs.Dayjs | null,
    public heigth?: number | null,
    public weight?: number | null,
    public mothersMaidenName?: string | null,
    public maritialStatus?: string | null,
    public socialSecurityNo?: number | null,
    public passportNo?: string | null,
    public passportExpiryDate?: string | null,
    public totalYearsWorkExperience?: number | null,
    public comments?: string | null,
    public occupation?: string | null,
    public yearswithEmployer?: number | null,
    public monthsWithEmployer?: number | null,
    public existingCustomer?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public parties?: IParty[] | null,
    public partyID?: IParty | null
  ) {}
}

export function getPersonIdentifier(person: IPerson): number | undefined {
  return person.id;
}
