import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProducer, Producer } from '../producer.model';
import { ProducerService } from '../service/producer.service';

@Injectable({ providedIn: 'root' })
export class ProducerRoutingResolveService implements Resolve<IProducer> {
  constructor(protected service: ProducerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProducer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((producer: HttpResponse<Producer>) => {
          if (producer.body) {
            return of(producer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Producer());
  }
}
