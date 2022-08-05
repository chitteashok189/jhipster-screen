import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyRoleComponent } from '../list/party-role.component';
import { PartyRoleDetailComponent } from '../detail/party-role-detail.component';
import { PartyRoleUpdateComponent } from '../update/party-role-update.component';
import { PartyRoleRoutingResolveService } from './party-role-routing-resolve.service';

const partyRoleRoute: Routes = [
  {
    path: '',
    component: PartyRoleComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyRoleDetailComponent,
    resolve: {
      partyRole: PartyRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyRoleUpdateComponent,
    resolve: {
      partyRole: PartyRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyRoleUpdateComponent,
    resolve: {
      partyRole: PartyRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyRoleRoute)],
  exports: [RouterModule],
})
export class PartyRoleRoutingModule {}
