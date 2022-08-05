import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IrrigationComponent } from '../list/irrigation.component';
import { IrrigationDetailComponent } from '../detail/irrigation-detail.component';
import { IrrigationUpdateComponent } from '../update/irrigation-update.component';
import { IrrigationRoutingResolveService } from './irrigation-routing-resolve.service';

const irrigationRoute: Routes = [
  {
    path: '',
    component: IrrigationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IrrigationDetailComponent,
    resolve: {
      irrigation: IrrigationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IrrigationUpdateComponent,
    resolve: {
      irrigation: IrrigationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IrrigationUpdateComponent,
    resolve: {
      irrigation: IrrigationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(irrigationRoute)],
  exports: [RouterModule],
})
export class IrrigationRoutingModule {}
