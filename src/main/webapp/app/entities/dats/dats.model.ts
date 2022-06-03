import { IUser } from 'app/entities/user/user.model';
import { IClient } from 'app/entities/client/client.model';
import { IDocumenType } from 'app/entities/documen-type/documen-type.model';
import { IProducer } from 'app/entities/producer/producer.model';
import { IAdministrator } from 'app/entities/administrator/administrator.model';
import { IDriver } from 'app/entities/driver/driver.model';

export interface IDats {
  id?: number;
  names?: string;
  surnames?: string;
  directions?: string;
  telephone?: number | null;
  cellPhone?: number;
  mail?: string;
  city?: string;
  user?: IUser | null;
  client?: IClient | null;
  documenType?: IDocumenType | null;
  producer?: IProducer | null;
  administrator?: IAdministrator | null;
  driver?: IDriver | null;
}

export class Dats implements IDats {
  constructor(
    public id?: number,
    public names?: string,
    public surnames?: string,
    public directions?: string,
    public telephone?: number | null,
    public cellPhone?: number,
    public mail?: string,
    public city?: string,
    public user?: IUser | null,
    public client?: IClient | null,
    public documenType?: IDocumenType | null,
    public producer?: IProducer | null,
    public administrator?: IAdministrator | null,
    public driver?: IDriver | null
  ) {}
}

export function getDatsIdentifier(dats: IDats): number | undefined {
  return dats.id;
}
