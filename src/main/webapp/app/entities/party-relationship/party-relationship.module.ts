import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyRelationshipComponent } from './list/party-relationship.component';
import { PartyRelationshipDetailComponent } from './detail/party-relationship-detail.component';
import { PartyRelationshipUpdateComponent } from './update/party-relationship-update.component';
import { PartyRelationshipDeleteDialogComponent } from './delete/party-relationship-delete-dialog.component';
import { PartyRelationshipRoutingModule } from './route/party-relationship-routing.module';

@NgModule({
  imports: [SharedModule, PartyRelationshipRoutingModule],
  declarations: [
    PartyRelationshipComponent,
    PartyRelationshipDetailComponent,
    PartyRelationshipUpdateComponent,
    PartyRelationshipDeleteDialogComponent,
  ],
  entryComponents: [PartyRelationshipDeleteDialogComponent],
})
export class PartyRelationshipModule {}
