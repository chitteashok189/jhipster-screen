import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartyNoteComponent } from '../list/party-note.component';
import { PartyNoteDetailComponent } from '../detail/party-note-detail.component';
import { PartyNoteUpdateComponent } from '../update/party-note-update.component';
import { PartyNoteRoutingResolveService } from './party-note-routing-resolve.service';

const partyNoteRoute: Routes = [
  {
    path: '',
    component: PartyNoteComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyNoteDetailComponent,
    resolve: {
      partyNote: PartyNoteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyNoteUpdateComponent,
    resolve: {
      partyNote: PartyNoteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyNoteUpdateComponent,
    resolve: {
      partyNote: PartyNoteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partyNoteRoute)],
  exports: [RouterModule],
})
export class PartyNoteRoutingModule {}
