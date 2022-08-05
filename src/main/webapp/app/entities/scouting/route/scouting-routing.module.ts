import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ScoutingComponent } from '../list/scouting.component';
import { ScoutingDetailComponent } from '../detail/scouting-detail.component';
import { ScoutingUpdateComponent } from '../update/scouting-update.component';
import { ScoutingRoutingResolveService } from './scouting-routing-resolve.service';

const scoutingRoute: Routes = [
  {
    path: '',
    component: ScoutingComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScoutingDetailComponent,
    resolve: {
      scouting: ScoutingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScoutingUpdateComponent,
    resolve: {
      scouting: ScoutingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScoutingUpdateComponent,
    resolve: {
      scouting: ScoutingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(scoutingRoute)],
  exports: [RouterModule],
})
export class ScoutingRoutingModule {}
