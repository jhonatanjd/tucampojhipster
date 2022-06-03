import { IProducts } from 'app/entities/products/products.model';
import { ISale } from 'app/entities/sale/sale.model';

export interface IOrderDetai {
  id?: number;
  products?: IProducts[] | null;
  sales?: ISale[] | null;
}

export class OrderDetai implements IOrderDetai {
  constructor(public id?: number, public products?: IProducts[] | null, public sales?: ISale[] | null) {}
}

export function getOrderDetaiIdentifier(orderDetai: IOrderDetai): number | undefined {
  return orderDetai.id;
}
