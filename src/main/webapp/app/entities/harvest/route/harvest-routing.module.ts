import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HarvestComponent } from '../list/harvest.component';
import { HarvestDetailComponent } from '../detail/harvest-detail.component';
import { HarvestUpdateComponent } from '../update/harvest-update.component';
import { HarvestRoutingResolveService } from './harvest-routing-resolve.service';

const harvestRoute: Routes = [
  {
    path: '',
    component: HarvestComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HarvestDetailComponent,
    resolve: {
      harvest: HarvestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HarvestUpdateComponent,
    resolve: {
      harvest: HarvestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HarvestUpdateComponent,
    resolve: {
      harvest: HarvestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(harvestRoute)],
  exports: [RouterModule],
})
export class HarvestRoutingModule {}
