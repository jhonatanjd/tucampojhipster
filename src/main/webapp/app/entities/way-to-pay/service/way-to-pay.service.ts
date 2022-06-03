import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWayToPay, getWayToPayIdentifier } from '../way-to-pay.model';

export type EntityResponseType = HttpResponse<IWayToPay>;
export type EntityArrayResponseType = HttpResponse<IWayToPay[]>;

@Injectable({ providedIn: 'root' })
export class WayToPayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/way-to-pays');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wayToPay: IWayToPay): Observable<EntityResponseType> {
    return this.http.post<IWayToPay>(this.resourceUrl, wayToPay, { observe: 'response' });
  }

  update(wayToPay: IWayToPay): Observable<EntityResponseType> {
    return this.http.put<IWayToPay>(`${this.resourceUrl}/${getWayToPayIdentifier(wayToPay) as number}`, wayToPay, { observe: 'response' });
  }

  partialUpdate(wayToPay: IWayToPay): Observable<EntityResponseType> {
    return this.http.patch<IWayToPay>(`${this.resourceUrl}/${getWayToPayIdentifier(wayToPay) as number}`, wayToPay, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWayToPay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWayToPay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWayToPayToCollectionIfMissing(wayToPayCollection: IWayToPay[], ...wayToPaysToCheck: (IWayToPay | null | undefined)[]): IWayToPay[] {
    const wayToPays: IWayToPay[] = wayToPaysToCheck.filter(isPresent);
    if (wayToPays.length > 0) {
      const wayToPayCollectionIdentifiers = wayToPayCollection.map(wayToPayItem => getWayToPayIdentifier(wayToPayItem)!);
      const wayToPaysToAdd = wayToPays.filter(wayToPayItem => {
        const wayToPayIdentifier = getWayToPayIdentifier(wayToPayItem);
        if (wayToPayIdentifier == null || wayToPayCollectionIdentifiers.includes(wayToPayIdentifier)) {
          return false;
        }
        wayToPayCollectionIdentifiers.push(wayToPayIdentifier);
        return true;
      });
      return [...wayToPaysToAdd, ...wayToPayCollection];
    }
    return wayToPayCollection;
  }
}
