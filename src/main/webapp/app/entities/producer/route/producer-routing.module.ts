import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProducerComponent } from '../list/producer.component';
import { ProducerDetailComponent } from '../detail/producer-detail.component';
import { ProducerUpdateComponent } from '../update/producer-update.component';
import { ProducerRoutingResolveService } from './producer-routing-resolve.service';

const producerRoute: Routes = [
  {
    path: '',
    component: ProducerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProducerDetailComponent,
    resolve: {
      producer: ProducerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProducerUpdateComponent,
    resolve: {
      producer: ProducerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProducerUpdateComponent,
    resolve: {
      producer: ProducerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(producerRoute)],
  exports: [RouterModule],
})
export class ProducerRoutingModule {}
