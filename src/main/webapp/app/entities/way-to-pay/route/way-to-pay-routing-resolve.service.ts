import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWayToPay, WayToPay } from '../way-to-pay.model';
import { WayToPayService } from '../service/way-to-pay.service';

@Injectable({ providedIn: 'root' })
export class WayToPayRoutingResolveService implements Resolve<IWayToPay> {
  constructor(protected service: WayToPayService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWayToPay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((wayToPay: HttpResponse<WayToPay>) => {
          if (wayToPay.body) {
            return of(wayToPay.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WayToPay());
  }
}
