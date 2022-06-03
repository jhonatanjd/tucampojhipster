import { IUnits } from 'app/entities/units/units.model';
import { ISale } from 'app/entities/sale/sale.model';
import { ICategory } from 'app/entities/category/category.model';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';

export interface IProducts {
  id?: number;
  name?: string | null;
  units?: IUnits[] | null;
  sale?: ISale | null;
  category?: ICategory | null;
  orderDetai?: IOrderDetai | null;
}

export class Products implements IProducts {
  constructor(
    public id?: number,
    public name?: string | null,
    public units?: IUnits[] | null,
    public sale?: ISale | null,
    public category?: ICategory | null,
    public orderDetai?: IOrderDetai | null
  ) {}
}

export function getProductsIdentifier(products: IProducts): number | undefined {
  return products.id;
}
