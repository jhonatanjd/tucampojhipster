import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShopping, Shopping } from '../shopping.model';
import { ShoppingService } from '../service/shopping.service';

@Injectable({ providedIn: 'root' })
export class ShoppingRoutingResolveService implements Resolve<IShopping> {
  constructor(protected service: ShoppingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShopping> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((shopping: HttpResponse<Shopping>) => {
          if (shopping.body) {
            return of(shopping.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Shopping());
  }
}
