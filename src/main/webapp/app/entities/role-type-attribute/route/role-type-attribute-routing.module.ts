import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RoleTypeAttributeComponent } from '../list/role-type-attribute.component';
import { RoleTypeAttributeDetailComponent } from '../detail/role-type-attribute-detail.component';
import { RoleTypeAttributeUpdateComponent } from '../update/role-type-attribute-update.component';
import { RoleTypeAttributeRoutingResolveService } from './role-type-attribute-routing-resolve.service';

const roleTypeAttributeRoute: Routes = [
  {
    path: '',
    component: RoleTypeAttributeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RoleTypeAttributeDetailComponent,
    resolve: {
      roleTypeAttribute: RoleTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RoleTypeAttributeUpdateComponent,
    resolve: {
      roleTypeAttribute: RoleTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RoleTypeAttributeUpdateComponent,
    resolve: {
      roleTypeAttribute: RoleTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(roleTypeAttributeRoute)],
  exports: [RouterModule],
})
export class RoleTypeAttributeRoutingModule {}
