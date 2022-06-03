import { IInvoice } from 'app/entities/invoice/invoice.model';

export interface IWayToPay {
  id?: number;
  description?: string | null;
  invoices?: IInvoice[] | null;
}

export class WayToPay implements IWayToPay {
  constructor(public id?: number, public description?: string | null, public invoices?: IInvoice[] | null) {}
}

export function getWayToPayIdentifier(wayToPay: IWayToPay): number | undefined {
  return wayToPay.id;
}
