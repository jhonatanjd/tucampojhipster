import dayjs from 'dayjs/esm';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { IClient } from 'app/entities/client/client.model';

export interface IShopping {
  id?: number;
  nameProducts?: string;
  amount?: number;
  directions?: string;
  city?: string;
  orderdate?: dayjs.Dayjs;
  dateOfDelivery?: dayjs.Dayjs;
  invoice?: IInvoice | null;
  client?: IClient | null;
}

export class Shopping implements IShopping {
  constructor(
    public id?: number,
    public nameProducts?: string,
    public amount?: number,
    public directions?: string,
    public city?: string,
    public orderdate?: dayjs.Dayjs,
    public dateOfDelivery?: dayjs.Dayjs,
    public invoice?: IInvoice | null,
    public client?: IClient | null
  ) {}
}

export function getShoppingIdentifier(shopping: IShopping): number | undefined {
  return shopping.id;
}
