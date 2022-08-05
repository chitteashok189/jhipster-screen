import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyStatusItemComponent } from './list/party-status-item.component';
import { PartyStatusItemDetailComponent } from './detail/party-status-item-detail.component';
import { PartyStatusItemUpdateComponent } from './update/party-status-item-update.component';
import { PartyStatusItemDeleteDialogComponent } from './delete/party-status-item-delete-dialog.component';
import { PartyStatusItemRoutingModule } from './route/party-status-item-routing.module';

@NgModule({
  imports: [SharedModule, PartyStatusItemRoutingModule],
  declarations: [
    PartyStatusItemComponent,
    PartyStatusItemDetailComponent,
    PartyStatusItemUpdateComponent,
    PartyStatusItemDeleteDialogComponent,
  ],
  entryComponents: [PartyStatusItemDeleteDialogComponent],
})
export class PartyStatusItemModule {}
