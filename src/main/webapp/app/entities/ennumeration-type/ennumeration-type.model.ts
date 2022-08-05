export interface IEnnumerationType {
  id?: number;
  ennumerationType?: number | null;
  hasTable?: boolean | null;
  description?: string | null;
  ennumeration?: string | null;
}

export class EnnumerationType implements IEnnumerationType {
  constructor(
    public id?: number,
    public ennumerationType?: number | null,
    public hasTable?: boolean | null,
    public description?: string | null,
    public ennumeration?: string | null
  ) {
    this.hasTable = this.hasTable ?? false;
  }
}

export function getEnnumerationTypeIdentifier(ennumerationType: IEnnumerationType): number | undefined {
  return ennumerationType.id;
}
