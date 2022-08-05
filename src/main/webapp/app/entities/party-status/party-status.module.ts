import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyStatusComponent } from './list/party-status.component';
import { PartyStatusDetailComponent } from './detail/party-status-detail.component';
import { PartyStatusUpdateComponent } from './update/party-status-update.component';
import { PartyStatusDeleteDialogComponent } from './delete/party-status-delete-dialog.component';
import { PartyStatusRoutingModule } from './route/party-status-routing.module';

@NgModule({
  imports: [SharedModule, PartyStatusRoutingModule],
  declarations: [PartyStatusComponent, PartyStatusDetailComponent, PartyStatusUpdateComponent, PartyStatusDeleteDialogComponent],
  entryComponents: [PartyStatusDeleteDialogComponent],
})
export class PartyStatusModule {}
