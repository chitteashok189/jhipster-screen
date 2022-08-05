import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnnumerationComponent } from '../list/ennumeration.component';
import { EnnumerationDetailComponent } from '../detail/ennumeration-detail.component';
import { EnnumerationUpdateComponent } from '../update/ennumeration-update.component';
import { EnnumerationRoutingResolveService } from './ennumeration-routing-resolve.service';

const ennumerationRoute: Routes = [
  {
    path: '',
    component: EnnumerationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnnumerationDetailComponent,
    resolve: {
      ennumeration: EnnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnnumerationUpdateComponent,
    resolve: {
      ennumeration: EnnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnnumerationUpdateComponent,
    resolve: {
      ennumeration: EnnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ennumerationRoute)],
  exports: [RouterModule],
})
export class EnnumerationRoutingModule {}
