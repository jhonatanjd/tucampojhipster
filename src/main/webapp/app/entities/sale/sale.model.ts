import dayjs from 'dayjs/esm';
import { IAnonymous } from 'app/entities/anonymous/anonymous.model';
import { IProducer } from 'app/entities/producer/producer.model';
import { IOrderDetai } from 'app/entities/order-detai/order-detai.model';
import { IProducts } from 'app/entities/products/products.model';
import { Transportations } from 'app/entities/enumerations/transportations.model';

export interface ISale {
  id?: number;
  nameProducts?: string;
  amountKilo?: number;
  priceKilo?: number;
  priceTotal?: number;
  city?: string;
  availableDate?: dayjs.Dayjs;
  stateTransportations?: Transportations | null;
  descriptions?: string | null;
  anonymous?: IAnonymous[] | null;
  producer?: IProducer | null;
  orderDetai?: IOrderDetai | null;
  products?: IProducts[] | null;
}

export class Sale implements ISale {
  constructor(
    public id?: number,
    public nameProducts?: string,
    public amountKilo?: number,
    public priceKilo?: number,
    public priceTotal?: number,
    public city?: string,
    public availableDate?: dayjs.Dayjs,
    public stateTransportations?: Transportations | null,
    public descriptions?: string | null,
    public anonymous?: IAnonymous[] | null,
    public producer?: IProducer | null,
    public orderDetai?: IOrderDetai | null,
    public products?: IProducts[] | null
  ) {}
}

export function getSaleIdentifier(sale: ISale): number | undefined {
  return sale.id;
}
