import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RawMaterialComponent } from '../list/raw-material.component';
import { RawMaterialDetailComponent } from '../detail/raw-material-detail.component';
import { RawMaterialUpdateComponent } from '../update/raw-material-update.component';
import { RawMaterialRoutingResolveService } from './raw-material-routing-resolve.service';

const rawMaterialRoute: Routes = [
  {
    path: '',
    component: RawMaterialComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RawMaterialDetailComponent,
    resolve: {
      rawMaterial: RawMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RawMaterialUpdateComponent,
    resolve: {
      rawMaterial: RawMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RawMaterialUpdateComponent,
    resolve: {
      rawMaterial: RawMaterialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rawMaterialRoute)],
  exports: [RouterModule],
})
export class RawMaterialRoutingModule {}
