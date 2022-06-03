import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumenTypeComponent } from '../list/documen-type.component';
import { DocumenTypeDetailComponent } from '../detail/documen-type-detail.component';
import { DocumenTypeUpdateComponent } from '../update/documen-type-update.component';
import { DocumenTypeRoutingResolveService } from './documen-type-routing-resolve.service';

const documenTypeRoute: Routes = [
  {
    path: '',
    component: DocumenTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumenTypeDetailComponent,
    resolve: {
      documenType: DocumenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumenTypeUpdateComponent,
    resolve: {
      documenType: DocumenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumenTypeUpdateComponent,
    resolve: {
      documenType: DocumenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documenTypeRoute)],
  exports: [RouterModule],
})
export class DocumenTypeRoutingModule {}
