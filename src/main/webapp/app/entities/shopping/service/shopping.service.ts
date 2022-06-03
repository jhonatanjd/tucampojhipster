import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShopping, getShoppingIdentifier } from '../shopping.model';

export type EntityResponseType = HttpResponse<IShopping>;
export type EntityArrayResponseType = HttpResponse<IShopping[]>;

@Injectable({ providedIn: 'root' })
export class ShoppingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shoppings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(shopping: IShopping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shopping);
    return this.http
      .post<IShopping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shopping: IShopping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shopping);
    return this.http
      .put<IShopping>(`${this.resourceUrl}/${getShoppingIdentifier(shopping) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(shopping: IShopping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shopping);
    return this.http
      .patch<IShopping>(`${this.resourceUrl}/${getShoppingIdentifier(shopping) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShopping>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShopping[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addShoppingToCollectionIfMissing(shoppingCollection: IShopping[], ...shoppingsToCheck: (IShopping | null | undefined)[]): IShopping[] {
    const shoppings: IShopping[] = shoppingsToCheck.filter(isPresent);
    if (shoppings.length > 0) {
      const shoppingCollectionIdentifiers = shoppingCollection.map(shoppingItem => getShoppingIdentifier(shoppingItem)!);
      const shoppingsToAdd = shoppings.filter(shoppingItem => {
        const shoppingIdentifier = getShoppingIdentifier(shoppingItem);
        if (shoppingIdentifier == null || shoppingCollectionIdentifiers.includes(shoppingIdentifier)) {
          return false;
        }
        shoppingCollectionIdentifiers.push(shoppingIdentifier);
        return true;
      });
      return [...shoppingsToAdd, ...shoppingCollection];
    }
    return shoppingCollection;
  }

  protected convertDateFromClient(shopping: IShopping): IShopping {
    return Object.assign({}, shopping, {
      orderdate: shopping.orderdate?.isValid() ? shopping.orderdate.format(DATE_FORMAT) : undefined,
      dateOfDelivery: shopping.dateOfDelivery?.isValid() ? shopping.dateOfDelivery.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderdate = res.body.orderdate ? dayjs(res.body.orderdate) : undefined;
      res.body.dateOfDelivery = res.body.dateOfDelivery ? dayjs(res.body.dateOfDelivery) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((shopping: IShopping) => {
        shopping.orderdate = shopping.orderdate ? dayjs(shopping.orderdate) : undefined;
        shopping.dateOfDelivery = shopping.dateOfDelivery ? dayjs(shopping.dateOfDelivery) : undefined;
      });
    }
    return res;
  }
}
