import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyTypeAttributeComponent } from '../list/party-type-attribute.component';
import { PartyTypeAttributeDetailComponent } from '../detail/party-type-attribute-detail.component';
import { PartyTypeAttributeUpdateComponent } from '../update/party-type-attribute-update.component';
import { PartyTypeAttributeRoutingResolveService } from './party-type-attribute-routing-resolve.service';

const partyTypeAttributeRoute: Routes = [
  {
    path: '',
    component: PartyTypeAttributeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyTypeAttributeDetailComponent,
    resolve: {
      partyTypeAttribute: PartyTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyTypeAttributeUpdateComponent,
    resolve: {
      partyTypeAttribute: PartyTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyTypeAttributeUpdateComponent,
    resolve: {
      partyTypeAttribute: PartyTypeAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyTypeAttributeRoute)],
  exports: [RouterModule],
})
export class PartyTypeAttributeRoutingModule {}
