import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LightComponent } from '../list/light.component';
import { LightDetailComponent } from '../detail/light-detail.component';
import { LightUpdateComponent } from '../update/light-update.component';
import { LightRoutingResolveService } from './light-routing-resolve.service';

const lightRoute: Routes = [
  {
    path: '',
    component: LightComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LightDetailComponent,
    resolve: {
      light: LightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LightUpdateComponent,
    resolve: {
      light: LightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LightUpdateComponent,
    resolve: {
      light: LightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lightRoute)],
  exports: [RouterModule],
})
export class LightRoutingModule {}
