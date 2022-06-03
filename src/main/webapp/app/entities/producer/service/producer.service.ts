import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProducer, getProducerIdentifier } from '../producer.model';

export type EntityResponseType = HttpResponse<IProducer>;
export type EntityArrayResponseType = HttpResponse<IProducer[]>;

@Injectable({ providedIn: 'root' })
export class ProducerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/producers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(producer: IProducer): Observable<EntityResponseType> {
    return this.http.post<IProducer>(this.resourceUrl, producer, { observe: 'response' });
  }

  update(producer: IProducer): Observable<EntityResponseType> {
    return this.http.put<IProducer>(`${this.resourceUrl}/${getProducerIdentifier(producer) as number}`, producer, { observe: 'response' });
  }

  partialUpdate(producer: IProducer): Observable<EntityResponseType> {
    return this.http.patch<IProducer>(`${this.resourceUrl}/${getProducerIdentifier(producer) as number}`, producer, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProducer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProducer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProducerToCollectionIfMissing(producerCollection: IProducer[], ...producersToCheck: (IProducer | null | undefined)[]): IProducer[] {
    const producers: IProducer[] = producersToCheck.filter(isPresent);
    if (producers.length > 0) {
      const producerCollectionIdentifiers = producerCollection.map(producerItem => getProducerIdentifier(producerItem)!);
      const producersToAdd = producers.filter(producerItem => {
        const producerIdentifier = getProducerIdentifier(producerItem);
        if (producerIdentifier == null || producerCollectionIdentifiers.includes(producerIdentifier)) {
          return false;
        }
        producerCollectionIdentifiers.push(producerIdentifier);
        return true;
      });
      return [...producersToAdd, ...producerCollection];
    }
    return producerCollection;
  }
}
