import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderDetai, getOrderDetaiIdentifier } from '../order-detai.model';

export type EntityResponseType = HttpResponse<IOrderDetai>;
export type EntityArrayResponseType = HttpResponse<IOrderDetai[]>;

@Injectable({ providedIn: 'root' })
export class OrderDetaiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-detais');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderDetai: IOrderDetai): Observable<EntityResponseType> {
    return this.http.post<IOrderDetai>(this.resourceUrl, orderDetai, { observe: 'response' });
  }

  update(orderDetai: IOrderDetai): Observable<EntityResponseType> {
    return this.http.put<IOrderDetai>(`${this.resourceUrl}/${getOrderDetaiIdentifier(orderDetai) as number}`, orderDetai, {
      observe: 'response',
    });
  }

  partialUpdate(orderDetai: IOrderDetai): Observable<EntityResponseType> {
    return this.http.patch<IOrderDetai>(`${this.resourceUrl}/${getOrderDetaiIdentifier(orderDetai) as number}`, orderDetai, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderDetai>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderDetai[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrderDetaiToCollectionIfMissing(
    orderDetaiCollection: IOrderDetai[],
    ...orderDetaisToCheck: (IOrderDetai | null | undefined)[]
  ): IOrderDetai[] {
    const orderDetais: IOrderDetai[] = orderDetaisToCheck.filter(isPresent);
    if (orderDetais.length > 0) {
      const orderDetaiCollectionIdentifiers = orderDetaiCollection.map(orderDetaiItem => getOrderDetaiIdentifier(orderDetaiItem)!);
      const orderDetaisToAdd = orderDetais.filter(orderDetaiItem => {
        const orderDetaiIdentifier = getOrderDetaiIdentifier(orderDetaiItem);
        if (orderDetaiIdentifier == null || orderDetaiCollectionIdentifiers.includes(orderDetaiIdentifier)) {
          return false;
        }
        orderDetaiCollectionIdentifiers.push(orderDetaiIdentifier);
        return true;
      });
      return [...orderDetaisToAdd, ...orderDetaiCollection];
    }
    return orderDetaiCollection;
  }
}
