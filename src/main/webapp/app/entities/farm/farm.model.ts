import dayjs from 'dayjs/esm';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { IParty } from 'app/entities/party/party.model';
import { ILocation } from 'app/entities/location/location.model';
import { FarmType } from 'app/entities/enumerations/farm-type.model';

export interface IFarm {
  id?: number;
  gUID?: string | null;
  farmName?: string | null;
  farmType?: FarmType | null;
  farmDescription?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  plantFactories?: IPlantFactory[] | null;
  partyID?: IParty | null;
  locationID?: ILocation | null;
}

export class Farm implements IFarm {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public farmName?: string | null,
    public farmType?: FarmType | null,
    public farmDescription?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public plantFactories?: IPlantFactory[] | null,
    public partyID?: IParty | null,
    public locationID?: ILocation | null
  ) {}
}

export function getFarmIdentifier(farm: IFarm): number | undefined {
  return farm.id;
}
