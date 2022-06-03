import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDats, Dats } from '../dats.model';
import { DatsService } from '../service/dats.service';

@Injectable({ providedIn: 'root' })
export class DatsRoutingResolveService implements Resolve<IDats> {
  constructor(protected service: DatsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDats> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dats: HttpResponse<Dats>) => {
          if (dats.body) {
            return of(dats.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dats());
  }
}
