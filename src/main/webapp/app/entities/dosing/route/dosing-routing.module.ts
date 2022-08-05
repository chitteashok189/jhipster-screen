import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DosingComponent } from '../list/dosing.component';
import { DosingDetailComponent } from '../detail/dosing-detail.component';
import { DosingUpdateComponent } from '../update/dosing-update.component';
import { DosingRoutingResolveService } from './dosing-routing-resolve.service';

const dosingRoute: Routes = [
  {
    path: '',
    component: DosingComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DosingDetailComponent,
    resolve: {
      dosing: DosingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DosingUpdateComponent,
    resolve: {
      dosing: DosingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DosingUpdateComponent,
    resolve: {
      dosing: DosingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dosingRoute)],
  exports: [RouterModule],
})
export class DosingRoutingModule {}
