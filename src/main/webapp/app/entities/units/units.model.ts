import { IProducts } from 'app/entities/products/products.model';

export interface IUnits {
  id?: number;
  description?: string | null;
  products?: IProducts | null;
}

export class Units implements IUnits {
  constructor(public id?: number, public description?: string | null, public products?: IProducts | null) {}
}

export function getUnitsIdentifier(units: IUnits): number | undefined {
  return units.id;
}
