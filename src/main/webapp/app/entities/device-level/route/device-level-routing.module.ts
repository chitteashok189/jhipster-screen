import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DeviceLevelComponent } from '../list/device-level.component';
import { DeviceLevelDetailComponent } from '../detail/device-level-detail.component';
import { DeviceLevelUpdateComponent } from '../update/device-level-update.component';
import { DeviceLevelRoutingResolveService } from './device-level-routing-resolve.service';

const deviceLevelRoute: Routes = [
  {
    path: '',
    component: DeviceLevelComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceLevelDetailComponent,
    resolve: {
      deviceLevel: DeviceLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceLevelUpdateComponent,
    resolve: {
      deviceLevel: DeviceLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceLevelUpdateComponent,
    resolve: {
      deviceLevel: DeviceLevelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(deviceLevelRoute)],
  exports: [RouterModule],
})
export class DeviceLevelRoutingModule {}
