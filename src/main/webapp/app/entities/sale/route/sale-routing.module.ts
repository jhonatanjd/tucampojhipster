import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SaleComponent } from '../list/sale.component';
import { SaleDetailComponent } from '../detail/sale-detail.component';
import { SaleUpdateComponent } from '../update/sale-update.component';
import { SaleRoutingResolveService } from './sale-routing-resolve.service';

const saleRoute: Routes = [
  {
    path: '',
    component: SaleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SaleDetailComponent,
    resolve: {
      sale: SaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SaleUpdateComponent,
    resolve: {
      sale: SaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SaleUpdateComponent,
    resolve: {
      sale: SaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(saleRoute)],
  exports: [RouterModule],
})
export class SaleRoutingModule {}
