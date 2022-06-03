import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WayToPayComponent } from '../list/way-to-pay.component';
import { WayToPayDetailComponent } from '../detail/way-to-pay-detail.component';
import { WayToPayUpdateComponent } from '../update/way-to-pay-update.component';
import { WayToPayRoutingResolveService } from './way-to-pay-routing-resolve.service';

const wayToPayRoute: Routes = [
  {
    path: '',
    component: WayToPayComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WayToPayDetailComponent,
    resolve: {
      wayToPay: WayToPayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WayToPayUpdateComponent,
    resolve: {
      wayToPay: WayToPayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WayToPayUpdateComponent,
    resolve: {
      wayToPay: WayToPayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(wayToPayRoute)],
  exports: [RouterModule],
})
export class WayToPayRoutingModule {}
