import { IDriver } from 'app/entities/driver/driver.model';

export interface IVehicle {
  id?: number;
  bodyworkType?: string;
  ability?: number;
  brand?: string;
  model?: string;
  licenseLate?: string;
  color?: string | null;
  driver?: IDriver | null;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public bodyworkType?: string,
    public ability?: number,
    public brand?: string,
    public model?: string,
    public licenseLate?: string,
    public color?: string | null,
    public driver?: IDriver | null
  ) {}
}

export function getVehicleIdentifier(vehicle: IVehicle): number | undefined {
  return vehicle.id;
}
