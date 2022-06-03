import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUnits, Units } from '../units.model';
import { UnitsService } from '../service/units.service';

@Injectable({ providedIn: 'root' })
export class UnitsRoutingResolveService implements Resolve<IUnits> {
  constructor(protected service: UnitsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnits> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((units: HttpResponse<Units>) => {
          if (units.body) {
            return of(units.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Units());
  }
}
