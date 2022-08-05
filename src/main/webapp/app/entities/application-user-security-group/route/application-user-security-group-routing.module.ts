import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApplicationUserSecurityGroupComponent } from '../list/application-user-security-group.component';
import { ApplicationUserSecurityGroupDetailComponent } from '../detail/application-user-security-group-detail.component';
import { ApplicationUserSecurityGroupUpdateComponent } from '../update/application-user-security-group-update.component';
import { ApplicationUserSecurityGroupRoutingResolveService } from './application-user-security-group-routing-resolve.service';

const applicationUserSecurityGroupRoute: Routes = [
  {
    path: '',
    component: ApplicationUserSecurityGroupComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicationUserSecurityGroupDetailComponent,
    resolve: {
      applicationUserSecurityGroup: ApplicationUserSecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicationUserSecurityGroupUpdateComponent,
    resolve: {
      applicationUserSecurityGroup: ApplicationUserSecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicationUserSecurityGroupUpdateComponent,
    resolve: {
      applicationUserSecurityGroup: ApplicationUserSecurityGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(applicationUserSecurityGroupRoute)],
  exports: [RouterModule],
})
export class ApplicationUserSecurityGroupRoutingModule {}
