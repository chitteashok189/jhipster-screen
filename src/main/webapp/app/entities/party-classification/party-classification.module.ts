import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyClassificationComponent } from './list/party-classification.component';
import { PartyClassificationDetailComponent } from './detail/party-classification-detail.component';
import { PartyClassificationUpdateComponent } from './update/party-classification-update.component';
import { PartyClassificationDeleteDialogComponent } from './delete/party-classification-delete-dialog.component';
import { PartyClassificationRoutingModule } from './route/party-classification-routing.module';

@NgModule({
  imports: [SharedModule, PartyClassificationRoutingModule],
  declarations: [
    PartyClassificationComponent,
    PartyClassificationDetailComponent,
    PartyClassificationUpdateComponent,
    PartyClassificationDeleteDialogComponent,
  ],
  entryComponents: [PartyClassificationDeleteDialogComponent],
})
export class PartyClassificationModule {}
