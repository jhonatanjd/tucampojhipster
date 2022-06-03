import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DatsComponent } from '../list/dats.component';
import { DatsDetailComponent } from '../detail/dats-detail.component';
import { DatsUpdateComponent } from '../update/dats-update.component';
import { DatsRoutingResolveService } from './dats-routing-resolve.service';

const datsRoute: Routes = [
  {
    path: '',
    component: DatsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatsDetailComponent,
    resolve: {
      dats: DatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatsUpdateComponent,
    resolve: {
      dats: DatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatsUpdateComponent,
    resolve: {
      dats: DatsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(datsRoute)],
  exports: [RouterModule],
})
export class DatsRoutingModule {}
