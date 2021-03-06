import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISale, getSaleIdentifier } from '../sale.model';

export type EntityResponseType = HttpResponse<ISale>;
export type EntityArrayResponseType = HttpResponse<ISale[]>;

@Injectable({ providedIn: 'root' })
export class SaleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sale: ISale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sale);
    return this.http
      .post<ISale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sale: ISale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sale);
    return this.http
      .put<ISale>(`${this.resourceUrl}/${getSaleIdentifier(sale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sale: ISale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sale);
    return this.http
      .patch<ISale>(`${this.resourceUrl}/${getSaleIdentifier(sale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSaleToCollectionIfMissing(saleCollection: ISale[], ...salesToCheck: (ISale | null | undefined)[]): ISale[] {
    const sales: ISale[] = salesToCheck.filter(isPresent);
    if (sales.length > 0) {
      const saleCollectionIdentifiers = saleCollection.map(saleItem => getSaleIdentifier(saleItem)!);
      const salesToAdd = sales.filter(saleItem => {
        const saleIdentifier = getSaleIdentifier(saleItem);
        if (saleIdentifier == null || saleCollectionIdentifiers.includes(saleIdentifier)) {
          return false;
        }
        saleCollectionIdentifiers.push(saleIdentifier);
        return true;
      });
      return [...salesToAdd, ...saleCollection];
    }
    return saleCollection;
  }

  protected convertDateFromClient(sale: ISale): ISale {
    return Object.assign({}, sale, {
      availableDate: sale.availableDate?.isValid() ? sale.availableDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.availableDate = res.body.availableDate ? dayjs(res.body.availableDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sale: ISale) => {
        sale.availableDate = sale.availableDate ? dayjs(sale.availableDate) : undefined;
      });
    }
    return res;
  }
}
