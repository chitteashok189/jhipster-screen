import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyRelationshipTypeComponent } from '../list/party-relationship-type.component';
import { PartyRelationshipTypeDetailComponent } from '../detail/party-relationship-type-detail.component';
import { PartyRelationshipTypeUpdateComponent } from '../update/party-relationship-type-update.component';
import { PartyRelationshipTypeRoutingResolveService } from './party-relationship-type-routing-resolve.service';

const partyRelationshipTypeRoute: Routes = [
  {
    path: '',
    component: PartyRelationshipTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyRelationshipTypeDetailComponent,
    resolve: {
      partyRelationshipType: PartyRelationshipTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyRelationshipTypeUpdateComponent,
    resolve: {
      partyRelationshipType: PartyRelationshipTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyRelationshipTypeUpdateComponent,
    resolve: {
      partyRelationshipType: PartyRelationshipTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyRelationshipTypeRoute)],
  exports: [RouterModule],
})
export class PartyRelationshipTypeRoutingModule {}
