import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyAttributeComponent } from './list/party-attribute.component';
import { PartyAttributeDetailComponent } from './detail/party-attribute-detail.component';
import { PartyAttributeUpdateComponent } from './update/party-attribute-update.component';
import { PartyAttributeDeleteDialogComponent } from './delete/party-attribute-delete-dialog.component';
import { PartyAttributeRoutingModule } from './route/party-attribute-routing.module';

@NgModule({
  imports: [SharedModule, PartyAttributeRoutingModule],
  declarations: [
    PartyAttributeComponent,
    PartyAttributeDetailComponent,
    PartyAttributeUpdateComponent,
    PartyAttributeDeleteDialogComponent,
  ],
  entryComponents: [PartyAttributeDeleteDialogComponent],
})
export class PartyAttributeModule {}
