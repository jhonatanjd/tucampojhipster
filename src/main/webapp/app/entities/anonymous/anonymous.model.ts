import { ISale } from 'app/entities/sale/sale.model';

export interface IAnonymous {
  id?: number;
  sale?: ISale | null;
}

export class Anonymous implements IAnonymous {
  constructor(public id?: number, public sale?: ISale | null) {}
}

export function getAnonymousIdentifier(anonymous: IAnonymous): number | undefined {
  return anonymous.id;
}
