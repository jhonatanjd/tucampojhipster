import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnonymous, getAnonymousIdentifier } from '../anonymous.model';

export type EntityResponseType = HttpResponse<IAnonymous>;
export type EntityArrayResponseType = HttpResponse<IAnonymous[]>;

@Injectable({ providedIn: 'root' })
export class AnonymousService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anonymous');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anonymous: IAnonymous): Observable<EntityResponseType> {
    return this.http.post<IAnonymous>(this.resourceUrl, anonymous, { observe: 'response' });
  }

  update(anonymous: IAnonymous): Observable<EntityResponseType> {
    return this.http.put<IAnonymous>(`${this.resourceUrl}/${getAnonymousIdentifier(anonymous) as number}`, anonymous, {
      observe: 'response',
    });
  }

  partialUpdate(anonymous: IAnonymous): Observable<EntityResponseType> {
    return this.http.patch<IAnonymous>(`${this.resourceUrl}/${getAnonymousIdentifier(anonymous) as number}`, anonymous, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnonymous>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnonymous[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnonymousToCollectionIfMissing(
    anonymousCollection: IAnonymous[],
    ...anonymousToCheck: (IAnonymous | null | undefined)[]
  ): IAnonymous[] {
    const anonymous: IAnonymous[] = anonymousToCheck.filter(isPresent);
    if (anonymous.length > 0) {
      const anonymousCollectionIdentifiers = anonymousCollection.map(anonymousItem => getAnonymousIdentifier(anonymousItem)!);
      const anonymousToAdd = anonymous.filter(anonymousItem => {
        const anonymousIdentifier = getAnonymousIdentifier(anonymousItem);
        if (anonymousIdentifier == null || anonymousCollectionIdentifiers.includes(anonymousIdentifier)) {
          return false;
        }
        anonymousCollectionIdentifiers.push(anonymousIdentifier);
        return true;
      });
      return [...anonymousToAdd, ...anonymousCollection];
    }
    return anonymousCollection;
  }
}
