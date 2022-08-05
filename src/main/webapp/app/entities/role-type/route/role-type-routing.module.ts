import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RoleTypeComponent } from '../list/role-type.component';
import { RoleTypeDetailComponent } from '../detail/role-type-detail.component';
import { RoleTypeUpdateComponent } from '../update/role-type-update.component';
import { RoleTypeRoutingResolveService } from './role-type-routing-resolve.service';

const roleTypeRoute: Routes = [
  {
    path: '',
    component: RoleTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleTypeDetailComponent,
    resolve: {
      roleType: RoleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleTypeUpdateComponent,
    resolve: {
      roleType: RoleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleTypeUpdateComponent,
    resolve: {
      roleType: RoleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(roleTypeRoute)],
  exports: [RouterModule],
})
export class RoleTypeRoutingModule {}
