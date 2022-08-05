import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyStatusComponent } from '../list/party-status.component';
import { PartyStatusDetailComponent } from '../detail/party-status-detail.component';
import { PartyStatusUpdateComponent } from '../update/party-status-update.component';
import { PartyStatusRoutingResolveService } from './party-status-routing-resolve.service';

const partyStatusRoute: Routes = [
  {
    path: '',
    component: PartyStatusComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyStatusDetailComponent,
    resolve: {
      partyStatus: PartyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyStatusUpdateComponent,
    resolve: {
      partyStatus: PartyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyStatusUpdateComponent,
    resolve: {
      partyStatus: PartyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyStatusRoute)],
  exports: [RouterModule],
})
export class PartyStatusRoutingModule {}
