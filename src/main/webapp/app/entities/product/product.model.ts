import dayjs from 'dayjs/esm';
import { ICrop } from 'app/entities/crop/crop.model';
import { ProType } from 'app/entities/enumerations/pro-type.model';

export interface IProduct {
  id?: number;
  gUID?: string | null;
  productCode?: string | null;
  productName?: string | null;
  productPrice?: number | null;
  productType?: ProType | null;
  uOM?: number | null;
  otherProductDetails?: string | null;
  previousEntry?: number | null;
  manufacturer?: string | null;
  productDescription?: string | null;
  imageFileName?: string | null;
  productEntryName?: string | null;
  capacity?: number | null;
  length?: number | null;
  width?: number | null;
  height?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  crops?: ICrop[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public productCode?: string | null,
    public productName?: string | null,
    public productPrice?: number | null,
    public productType?: ProType | null,
    public uOM?: number | null,
    public otherProductDetails?: string | null,
    public previousEntry?: number | null,
    public manufacturer?: string | null,
    public productDescription?: string | null,
    public imageFileName?: string | null,
    public productEntryName?: string | null,
    public capacity?: number | null,
    public length?: number | null,
    public width?: number | null,
    public height?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public crops?: ICrop[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
