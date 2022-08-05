import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyStatusTypeComponent } from '../list/party-status-type.component';
import { PartyStatusTypeDetailComponent } from '../detail/party-status-type-detail.component';
import { PartyStatusTypeUpdateComponent } from '../update/party-status-type-update.component';
import { PartyStatusTypeRoutingResolveService } from './party-status-type-routing-resolve.service';

const partyStatusTypeRoute: Routes = [
  {
    path: '',
    component: PartyStatusTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyStatusTypeDetailComponent,
    resolve: {
      partyStatusType: PartyStatusTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyStatusTypeUpdateComponent,
    resolve: {
      partyStatusType: PartyStatusTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyStatusTypeUpdateComponent,
    resolve: {
      partyStatusType: PartyStatusTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyStatusTypeRoute)],
  exports: [RouterModule],
})
export class PartyStatusTypeRoutingModule {}
