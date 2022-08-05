import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyStatusItemComponent } from '../list/party-status-item.component';
import { PartyStatusItemDetailComponent } from '../detail/party-status-item-detail.component';
import { PartyStatusItemUpdateComponent } from '../update/party-status-item-update.component';
import { PartyStatusItemRoutingResolveService } from './party-status-item-routing-resolve.service';

const partyStatusItemRoute: Routes = [
  {
    path: '',
    component: PartyStatusItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyStatusItemDetailComponent,
    resolve: {
      partyStatusItem: PartyStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyStatusItemUpdateComponent,
    resolve: {
      partyStatusItem: PartyStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyStatusItemUpdateComponent,
    resolve: {
      partyStatusItem: PartyStatusItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyStatusItemRoute)],
  exports: [RouterModule],
})
export class PartyStatusItemRoutingModule {}
