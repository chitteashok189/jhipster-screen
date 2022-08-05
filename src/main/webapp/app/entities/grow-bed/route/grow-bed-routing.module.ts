import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GrowBedComponent } from '../list/grow-bed.component';
import { GrowBedDetailComponent } from '../detail/grow-bed-detail.component';
import { GrowBedUpdateComponent } from '../update/grow-bed-update.component';
import { GrowBedRoutingResolveService } from './grow-bed-routing-resolve.service';

const growBedRoute: Routes = [
  {
    path: '',
    component: GrowBedComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GrowBedDetailComponent,
    resolve: {
      growBed: GrowBedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GrowBedUpdateComponent,
    resolve: {
      growBed: GrowBedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GrowBedUpdateComponent,
    resolve: {
      growBed: GrowBedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(growBedRoute)],
  exports: [RouterModule],
})
export class GrowBedRoutingModule {}
