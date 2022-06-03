import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumenType, DocumenType } from '../documen-type.model';
import { DocumenTypeService } from '../service/documen-type.service';

@Injectable({ providedIn: 'root' })
export class DocumenTypeRoutingResolveService implements Resolve<IDocumenType> {
  constructor(protected service: DocumenTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumenType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documenType: HttpResponse<DocumenType>) => {
          if (documenType.body) {
            return of(documenType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumenType());
  }
}
