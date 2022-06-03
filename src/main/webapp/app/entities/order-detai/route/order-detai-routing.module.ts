import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderDetaiComponent } from '../list/order-detai.component';
import { OrderDetaiDetailComponent } from '../detail/order-detai-detail.component';
import { OrderDetaiUpdateComponent } from '../update/order-detai-update.component';
import { OrderDetaiRoutingResolveService } from './order-detai-routing-resolve.service';

const orderDetaiRoute: Routes = [
  {
    path: '',
    component: OrderDetaiComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderDetaiDetailComponent,
    resolve: {
      orderDetai: OrderDetaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderDetaiUpdateComponent,
    resolve: {
      orderDetai: OrderDetaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderDetaiUpdateComponent,
    resolve: {
      orderDetai: OrderDetaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderDetaiRoute)],
  exports: [RouterModule],
})
export class OrderDetaiRoutingModule {}
