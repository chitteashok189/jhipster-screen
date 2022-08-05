import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SensorModelComponent } from '../list/sensor-model.component';
import { SensorModelDetailComponent } from '../detail/sensor-model-detail.component';
import { SensorModelUpdateComponent } from '../update/sensor-model-update.component';
import { SensorModelRoutingResolveService } from './sensor-model-routing-resolve.service';

const sensorModelRoute: Routes = [
  {
    path: '',
    component: SensorModelComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SensorModelDetailComponent,
    resolve: {
      sensorModel: SensorModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SensorModelUpdateComponent,
    resolve: {
      sensorModel: SensorModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SensorModelUpdateComponent,
    resolve: {
      sensorModel: SensorModelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sensorModelRoute)],
  exports: [RouterModule],
})
export class SensorModelRoutingModule {}
