import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UnitsComponent } from '../list/units.component';
import { UnitsDetailComponent } from '../detail/units-detail.component';
import { UnitsUpdateComponent } from '../update/units-update.component';
import { UnitsRoutingResolveService } from './units-routing-resolve.service';

const unitsRoute: Routes = [
  {
    path: '',
    component: UnitsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UnitsDetailComponent,
    resolve: {
      units: UnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UnitsUpdateComponent,
    resolve: {
      units: UnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UnitsUpdateComponent,
    resolve: {
      units: UnitsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(unitsRoute)],
  exports: [RouterModule],
})
export class UnitsRoutingModule {}
