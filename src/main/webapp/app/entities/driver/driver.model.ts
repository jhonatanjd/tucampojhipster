import { IDats } from 'app/entities/dats/dats.model';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';

export interface IDriver {
  id?: number;
  dats?: IDats[] | null;
  vehicle?: IVehicle | null;
}

export class Driver implements IDriver {
  constructor(public id?: number, public dats?: IDats[] | null, public vehicle?: IVehicle | null) {}
}

export function getDriverIdentifier(driver: IDriver): number | undefined {
  return driver.id;
}
