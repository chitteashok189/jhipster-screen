export interface IEnnumeration {
  id?: number;
  enumCode?: number | null;
  description?: string | null;
}

export class Ennumeration implements IEnnumeration {
  constructor(public id?: number, public enumCode?: number | null, public description?: string | null) {}
}

export function getEnnumerationIdentifier(ennumeration: IEnnumeration): number | undefined {
  return ennumeration.id;
}
