import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyRelationshipTypeComponent } from './list/party-relationship-type.component';
import { PartyRelationshipTypeDetailComponent } from './detail/party-relationship-type-detail.component';
import { PartyRelationshipTypeUpdateComponent } from './update/party-relationship-type-update.component';
import { PartyRelationshipTypeDeleteDialogComponent } from './delete/party-relationship-type-delete-dialog.component';
import { PartyRelationshipTypeRoutingModule } from './route/party-relationship-type-routing.module';

@NgModule({
  imports: [SharedModule, PartyRelationshipTypeRoutingModule],
  declarations: [
    PartyRelationshipTypeComponent,
    PartyRelationshipTypeDetailComponent,
    PartyRelationshipTypeUpdateComponent,
    PartyRelationshipTypeDeleteDialogComponent,
  ],
  entryComponents: [PartyRelationshipTypeDeleteDialogComponent],
})
export class PartyRelationshipTypeModule {}
