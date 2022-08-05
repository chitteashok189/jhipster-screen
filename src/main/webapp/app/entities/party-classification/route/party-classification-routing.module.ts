import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyClassificationComponent } from '../list/party-classification.component';
import { PartyClassificationDetailComponent } from '../detail/party-classification-detail.component';
import { PartyClassificationUpdateComponent } from '../update/party-classification-update.component';
import { PartyClassificationRoutingResolveService } from './party-classification-routing-resolve.service';

const partyClassificationRoute: Routes = [
  {
    path: '',
    component: PartyClassificationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyClassificationDetailComponent,
    resolve: {
      partyClassification: PartyClassificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyClassificationUpdateComponent,
    resolve: {
      partyClassification: PartyClassificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyClassificationUpdateComponent,
    resolve: {
      partyClassification: PartyClassificationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyClassificationRoute)],
  exports: [RouterModule],
})
export class PartyClassificationRoutingModule {}
