import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DisorderComponent } from '../list/disorder.component';
import { DisorderDetailComponent } from '../detail/disorder-detail.component';
import { DisorderUpdateComponent } from '../update/disorder-update.component';
import { DisorderRoutingResolveService } from './disorder-routing-resolve.service';

const disorderRoute: Routes = [
  {
    path: '',
    component: DisorderComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DisorderDetailComponent,
    resolve: {
      disorder: DisorderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DisorderUpdateComponent,
    resolve: {
      disorder: DisorderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DisorderUpdateComponent,
    resolve: {
      disorder: DisorderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(disorderRoute)],
  exports: [RouterModule],
})
export class DisorderRoutingModule {}
