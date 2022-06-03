import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderDetai, OrderDetai } from '../order-detai.model';
import { OrderDetaiService } from '../service/order-detai.service';

@Injectable({ providedIn: 'root' })
export class OrderDetaiRoutingResolveService implements Resolve<IOrderDetai> {
  constructor(protected service: OrderDetaiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderDetai> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orderDetai: HttpResponse<OrderDetai>) => {
          if (orderDetai.body) {
            return of(orderDetai.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderDetai());
  }
}
