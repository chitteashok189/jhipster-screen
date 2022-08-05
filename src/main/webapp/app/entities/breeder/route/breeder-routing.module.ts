import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BreederComponent } from '../list/breeder.component';
import { BreederDetailComponent } from '../detail/breeder-detail.component';
import { BreederUpdateComponent } from '../update/breeder-update.component';
import { BreederRoutingResolveService } from './breeder-routing-resolve.service';

const breederRoute: Routes = [
  {
    path: '',
    component: BreederComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BreederDetailComponent,
    resolve: {
      breeder: BreederRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BreederUpdateComponent,
    resolve: {
      breeder: BreederRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BreederUpdateComponent,
    resolve: {
      breeder: BreederRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(breederRoute)],
  exports: [RouterModule],
})
export class BreederRoutingModule {}
