import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClimateComponent } from '../list/climate.component';
import { ClimateDetailComponent } from '../detail/climate-detail.component';
import { ClimateUpdateComponent } from '../update/climate-update.component';
import { ClimateRoutingResolveService } from './climate-routing-resolve.service';

const climateRoute: Routes = [
  {
    path: '',
    component: ClimateComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClimateDetailComponent,
    resolve: {
      climate: ClimateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClimateUpdateComponent,
    resolve: {
      climate: ClimateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClimateUpdateComponent,
    resolve: {
      climate: ClimateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(climateRoute)],
  exports: [RouterModule],
})
export class ClimateRoutingModule {}
