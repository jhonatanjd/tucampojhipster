import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUnits, getUnitsIdentifier } from '../units.model';

export type EntityResponseType = HttpResponse<IUnits>;
export type EntityArrayResponseType = HttpResponse<IUnits[]>;

@Injectable({ providedIn: 'root' })
export class UnitsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(units: IUnits): Observable<EntityResponseType> {
    return this.http.post<IUnits>(this.resourceUrl, units, { observe: 'response' });
  }

  update(units: IUnits): Observable<EntityResponseType> {
    return this.http.put<IUnits>(`${this.resourceUrl}/${getUnitsIdentifier(units) as number}`, units, { observe: 'response' });
  }

  partialUpdate(units: IUnits): Observable<EntityResponseType> {
    return this.http.patch<IUnits>(`${this.resourceUrl}/${getUnitsIdentifier(units) as number}`, units, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnits[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUnitsToCollectionIfMissing(unitsCollection: IUnits[], ...unitsToCheck: (IUnits | null | undefined)[]): IUnits[] {
    const units: IUnits[] = unitsToCheck.filter(isPresent);
    if (units.length > 0) {
      const unitsCollectionIdentifiers = unitsCollection.map(unitsItem => getUnitsIdentifier(unitsItem)!);
      const unitsToAdd = units.filter(unitsItem => {
        const unitsIdentifier = getUnitsIdentifier(unitsItem);
        if (unitsIdentifier == null || unitsCollectionIdentifiers.includes(unitsIdentifier)) {
          return false;
        }
        unitsCollectionIdentifiers.push(unitsIdentifier);
        return true;
      });
      return [...unitsToAdd, ...unitsCollection];
    }
    return unitsCollection;
  }
}
