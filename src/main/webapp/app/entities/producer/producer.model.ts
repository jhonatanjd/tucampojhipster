import { IDats } from 'app/entities/dats/dats.model';
import { ISale } from 'app/entities/sale/sale.model';

export interface IProducer {
  id?: number;
  nameProduct?: string;
  dats?: IDats[] | null;
  sales?: ISale[] | null;
}

export class Producer implements IProducer {
  constructor(public id?: number, public nameProduct?: string, public dats?: IDats[] | null, public sales?: ISale[] | null) {}
}

export function getProducerIdentifier(producer: IProducer): number | undefined {
  return producer.id;
}
