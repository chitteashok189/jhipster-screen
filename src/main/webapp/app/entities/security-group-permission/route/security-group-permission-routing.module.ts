import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SecurityGroupPermissionComponent } from '../list/security-group-permission.component';
import { SecurityGroupPermissionDetailComponent } from '../detail/security-group-permission-detail.component';
import { SecurityGroupPermissionUpdateComponent } from '../update/security-group-permission-update.component';
import { SecurityGroupPermissionRoutingResolveService } from './security-group-permission-routing-resolve.service';

const securityGroupPermissionRoute: Routes = [
  {
    path: '',
    component: SecurityGroupPermissionComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityGroupPermissionDetailComponent,
    resolve: {
      securityGroupPermission: SecurityGroupPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityGroupPermissionUpdateComponent,
    resolve: {
      securityGroupPermission: SecurityGroupPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityGroupPermissionUpdateComponent,
    resolve: {
      securityGroupPermission: SecurityGroupPermissionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(securityGroupPermissionRoute)],
  exports: [RouterModule],
})
export class SecurityGroupPermissionRoutingModule {}
