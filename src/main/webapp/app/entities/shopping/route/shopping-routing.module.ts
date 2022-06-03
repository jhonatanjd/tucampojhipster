import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShoppingComponent } from '../list/shopping.component';
import { ShoppingDetailComponent } from '../detail/shopping-detail.component';
import { ShoppingUpdateComponent } from '../update/shopping-update.component';
import { ShoppingRoutingResolveService } from './shopping-routing-resolve.service';

const shoppingRoute: Routes = [
  {
    path: '',
    component: ShoppingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShoppingDetailComponent,
    resolve: {
      shopping: ShoppingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShoppingUpdateComponent,
    resolve: {
      shopping: ShoppingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShoppingUpdateComponent,
    resolve: {
      shopping: ShoppingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(shoppingRoute)],
  exports: [RouterModule],
})
export class ShoppingRoutingModule {}
