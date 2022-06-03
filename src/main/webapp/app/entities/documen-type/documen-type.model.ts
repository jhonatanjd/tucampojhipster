import { IDats } from 'app/entities/dats/dats.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IDocumenType {
  id?: number;
  initials?: string;
  typedocument?: string;
  stateDocument?: State | null;
  dats?: IDats | null;
}

export class DocumenType implements IDocumenType {
  constructor(
    public id?: number,
    public initials?: string,
    public typedocument?: string,
    public stateDocument?: State | null,
    public dats?: IDats | null
  ) {}
}

export function getDocumenTypeIdentifier(documenType: IDocumenType): number | undefined {
  return documenType.id;
}
