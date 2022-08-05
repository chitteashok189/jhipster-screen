import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyStatusTypeComponent } from './list/party-status-type.component';
import { PartyStatusTypeDetailComponent } from './detail/party-status-type-detail.component';
import { PartyStatusTypeUpdateComponent } from './update/party-status-type-update.component';
import { PartyStatusTypeDeleteDialogComponent } from './delete/party-status-type-delete-dialog.component';
import { PartyStatusTypeRoutingModule } from './route/party-status-type-routing.module';

@NgModule({
  imports: [SharedModule, PartyStatusTypeRoutingModule],
  declarations: [
    PartyStatusTypeComponent,
    PartyStatusTypeDetailComponent,
    PartyStatusTypeUpdateComponent,
    PartyStatusTypeDeleteDialogComponent,
  ],
  entryComponents: [PartyStatusTypeDeleteDialogComponent],
})
export class PartyStatusTypeModule {}
