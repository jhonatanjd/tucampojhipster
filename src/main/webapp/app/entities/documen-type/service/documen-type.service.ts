import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumenType, getDocumenTypeIdentifier } from '../documen-type.model';

export type EntityResponseType = HttpResponse<IDocumenType>;
export type EntityArrayResponseType = HttpResponse<IDocumenType[]>;

@Injectable({ providedIn: 'root' })
export class DocumenTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documen-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documenType: IDocumenType): Observable<EntityResponseType> {
    return this.http.post<IDocumenType>(this.resourceUrl, documenType, { observe: 'response' });
  }

  update(documenType: IDocumenType): Observable<EntityResponseType> {
    return this.http.put<IDocumenType>(`${this.resourceUrl}/${getDocumenTypeIdentifier(documenType) as number}`, documenType, {
      observe: 'response',
    });
  }

  partialUpdate(documenType: IDocumenType): Observable<EntityResponseType> {
    return this.http.patch<IDocumenType>(`${this.resourceUrl}/${getDocumenTypeIdentifier(documenType) as number}`, documenType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumenType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumenType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDocumenTypeToCollectionIfMissing(
    documenTypeCollection: IDocumenType[],
    ...documenTypesToCheck: (IDocumenType | null | undefined)[]
  ): IDocumenType[] {
    const documenTypes: IDocumenType[] = documenTypesToCheck.filter(isPresent);
    if (documenTypes.length > 0) {
      const documenTypeCollectionIdentifiers = documenTypeCollection.map(documenTypeItem => getDocumenTypeIdentifier(documenTypeItem)!);
      const documenTypesToAdd = documenTypes.filter(documenTypeItem => {
        const documenTypeIdentifier = getDocumenTypeIdentifier(documenTypeItem);
        if (documenTypeIdentifier == null || documenTypeCollectionIdentifiers.includes(documenTypeIdentifier)) {
          return false;
        }
        documenTypeCollectionIdentifiers.push(documenTypeIdentifier);
        return true;
      });
      return [...documenTypesToAdd, ...documenTypeCollection];
    }
    return documenTypeCollection;
  }
}
