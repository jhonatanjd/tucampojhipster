import { IShopping } from 'app/entities/shopping/shopping.model';
import { IClient } from 'app/entities/client/client.model';
import { IWayToPay } from 'app/entities/way-to-pay/way-to-pay.model';

export interface IInvoice {
  id?: number;
  unitPrice?: string;
  amount?: number;
  priceTotal?: number;
  shoppings?: IShopping[] | null;
  clients?: IClient[] | null;
  wayToPay?: IWayToPay | null;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public unitPrice?: string,
    public amount?: number,
    public priceTotal?: number,
    public shoppings?: IShopping[] | null,
    public clients?: IClient[] | null,
    public wayToPay?: IWayToPay | null
  ) {}
}

export function getInvoiceIdentifier(invoice: IInvoice): number | undefined {
  return invoice.id;
}
