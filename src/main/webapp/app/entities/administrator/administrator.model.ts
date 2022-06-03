import { IDats } from 'app/entities/dats/dats.model';

export interface IAdministrator {
  id?: number;
  dats?: IDats[] | null;
}

export class Administrator implements IAdministrator {
  constructor(public id?: number, public dats?: IDats[] | null) {}
}

export function getAdministratorIdentifier(administrator: IAdministrator): number | undefined {
  return administrator.id;
}
