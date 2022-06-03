import { IProducts } from 'app/entities/products/products.model';
import { State } from 'app/entities/enumerations/state.model';

export interface ICategory {
  id?: number;
  name?: string;
  stateCategory?: State | null;
  products?: IProducts[] | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public stateCategory?: State | null, public products?: IProducts[] | null) {}
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
