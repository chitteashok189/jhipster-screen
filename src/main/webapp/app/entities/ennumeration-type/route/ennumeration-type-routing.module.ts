import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnnumerationTypeComponent } from '../list/ennumeration-type.component';
import { EnnumerationTypeDetailComponent } from '../detail/ennumeration-type-detail.component';
import { EnnumerationTypeUpdateComponent } from '../update/ennumeration-type-update.component';
import { EnnumerationTypeRoutingResolveService } from './ennumeration-type-routing-resolve.service';

const ennumerationTypeRoute: Routes = [
  {
    path: '',
    component: EnnumerationTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnnumerationTypeDetailComponent,
    resolve: {
      ennumerationType: EnnumerationTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnnumerationTypeUpdateComponent,
    resolve: {
      ennumerationType: EnnumerationTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnnumerationTypeUpdateComponent,
    resolve: {
      ennumerationType: EnnumerationTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ennumerationTypeRoute)],
  exports: [RouterModule],
})
export class EnnumerationTypeRoutingModule {}
