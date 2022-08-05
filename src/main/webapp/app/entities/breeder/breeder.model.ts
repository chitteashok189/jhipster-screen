import dayjs from 'dayjs/esm';

export interface IBreeder {
  id?: number;
  gUID?: string | null;
  breederName?: string | null;
  breederType?: number | null;
  breederStatus?: number | null;
  breederDescription?: string | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class Breeder implements IBreeder {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public breederName?: string | null,
    public breederType?: number | null,
    public breederStatus?: number | null,
    public breederDescription?: string | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getBreederIdentifier(breeder: IBreeder): number | undefined {
  return breeder.id;
}
