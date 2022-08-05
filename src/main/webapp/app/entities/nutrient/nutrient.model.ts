import dayjs from 'dayjs/esm';
import { NutrientType } from 'app/entities/enumerations/nutrient-type.model';
import { NutForms } from 'app/entities/enumerations/nut-forms.model';
import { NutTypeID } from 'app/entities/enumerations/nut-type-id.model';

export interface INutrient {
  id?: number;
  gUID?: string | null;
  nutrientName?: string | null;
  type?: NutrientType | null;
  brandName?: string | null;
  nutrientLabel?: number | null;
  nutrientForms?: NutForms | null;
  nutrientTypeID?: NutTypeID | null;
  price?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
}

export class Nutrient implements INutrient {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public nutrientName?: string | null,
    public type?: NutrientType | null,
    public brandName?: string | null,
    public nutrientLabel?: number | null,
    public nutrientForms?: NutForms | null,
    public nutrientTypeID?: NutTypeID | null,
    public price?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null
  ) {}
}

export function getNutrientIdentifier(nutrient: INutrient): number | undefined {
  return nutrient.id;
}
