import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PestControlComponent } from '../list/pest-control.component';
import { PestControlDetailComponent } from '../detail/pest-control-detail.component';
import { PestControlUpdateComponent } from '../update/pest-control-update.component';
import { PestControlRoutingResolveService } from './pest-control-routing-resolve.service';

const pestControlRoute: Routes = [
  {
    path: '',
    component: PestControlComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PestControlDetailComponent,
    resolve: {
      pestControl: PestControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PestControlUpdateComponent,
    resolve: {
      pestControl: PestControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PestControlUpdateComponent,
    resolve: {
      pestControl: PestControlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pestControlRoute)],
  exports: [RouterModule],
})
export class PestControlRoutingModule {}
