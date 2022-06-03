import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDats, getDatsIdentifier } from '../dats.model';

export type EntityResponseType = HttpResponse<IDats>;
export type EntityArrayResponseType = HttpResponse<IDats[]>;

@Injectable({ providedIn: 'root' })
export class DatsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dats: IDats): Observable<EntityResponseType> {
    return this.http.post<IDats>(this.resourceUrl, dats, { observe: 'response' });
  }

  update(dats: IDats): Observable<EntityResponseType> {
    return this.http.put<IDats>(`${this.resourceUrl}/${getDatsIdentifier(dats) as number}`, dats, { observe: 'response' });
  }

  partialUpdate(dats: IDats): Observable<EntityResponseType> {
    return this.http.patch<IDats>(`${this.resourceUrl}/${getDatsIdentifier(dats) as number}`, dats, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDats>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDats[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDatsToCollectionIfMissing(datsCollection: IDats[], ...datsToCheck: (IDats | null | undefined)[]): IDats[] {
    const dats: IDats[] = datsToCheck.filter(isPresent);
    if (dats.length > 0) {
      const datsCollectionIdentifiers = datsCollection.map(datsItem => getDatsIdentifier(datsItem)!);
      const datsToAdd = dats.filter(datsItem => {
        const datsIdentifier = getDatsIdentifier(datsItem);
        if (datsIdentifier == null || datsCollectionIdentifiers.includes(datsIdentifier)) {
          return false;
        }
        datsCollectionIdentifiers.push(datsIdentifier);
        return true;
      });
      return [...datsToAdd, ...datsCollection];
    }
    return datsCollection;
  }
}
