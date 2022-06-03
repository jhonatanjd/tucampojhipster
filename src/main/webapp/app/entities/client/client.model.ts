import { IShopping } from 'app/entities/shopping/shopping.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { IDats } from 'app/entities/dats/dats.model';

export interface IClient {
  id?: number;
  shoppings?: IShopping[] | null;
  invoice?: IInvoice | null;
  dats?: IDats[] | null;
}

export class Client implements IClient {
  constructor(public id?: number, public shoppings?: IShopping[] | null, public invoice?: IInvoice | null, public dats?: IDats[] | null) {}
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
