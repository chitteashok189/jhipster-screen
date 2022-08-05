import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyAttributeComponent } from '../list/party-attribute.component';
import { PartyAttributeDetailComponent } from '../detail/party-attribute-detail.component';
import { PartyAttributeUpdateComponent } from '../update/party-attribute-update.component';
import { PartyAttributeRoutingResolveService } from './party-attribute-routing-resolve.service';

const partyAttributeRoute: Routes = [
  {
    path: '',
    component: PartyAttributeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyAttributeDetailComponent,
    resolve: {
      partyAttribute: PartyAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyAttributeUpdateComponent,
    resolve: {
      partyAttribute: PartyAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyAttributeUpdateComponent,
    resolve: {
      partyAttribute: PartyAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyAttributeRoute)],
  exports: [RouterModule],
})
export class PartyAttributeRoutingModule {}
