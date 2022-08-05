import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SecurityGroupComponent } from '../list/security-group.component';
import { SecurityGroupDetailComponent } from '../detail/security-group-detail.component';
import { SecurityGroupUpdateComponent } from '../update/security-group-update.component';
import { SecurityGroupRoutingResolveService } from './security-group-routing-resolve.service';

const securityGroupRoute: Routes = [
  {
    path: '',
    component: SecurityGroupComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityGroupDetailComponent,
    resolve: {
      securityGroup: SecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityGroupUpdateComponent,
    resolve: {
      securityGroup: SecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityGroupUpdateComponent,
    resolve: {
      securityGroup: SecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(securityGroupRoute)],
  exports: [RouterModule],
})
export class SecurityGroupRoutingModule {}
