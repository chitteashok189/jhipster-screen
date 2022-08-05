import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyRelationshipComponent } from '../list/party-relationship.component';
import { PartyRelationshipDetailComponent } from '../detail/party-relationship-detail.component';
import { PartyRelationshipUpdateComponent } from '../update/party-relationship-update.component';
import { PartyRelationshipRoutingResolveService } from './party-relationship-routing-resolve.service';

const partyRelationshipRoute: Routes = [
  {
    path: '',
    component: PartyRelationshipComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyRelationshipDetailComponent,
    resolve: {
      partyRelationship: PartyRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyRelationshipUpdateComponent,
    resolve: {
      partyRelationship: PartyRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyRelationshipUpdateComponent,
    resolve: {
      partyRelationship: PartyRelationshipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyRelationshipRoute)],
  exports: [RouterModule],
})
export class PartyRelationshipRoutingModule {}
