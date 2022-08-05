import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyTypeAttributeComponent } from './list/party-type-attribute.component';
import { PartyTypeAttributeDetailComponent } from './detail/party-type-attribute-detail.component';
import { PartyTypeAttributeUpdateComponent } from './update/party-type-attribute-update.component';
import { PartyTypeAttributeDeleteDialogComponent } from './delete/party-type-attribute-delete-dialog.component';
import { PartyTypeAttributeRoutingModule } from './route/party-type-attribute-routing.module';

@NgModule({
  imports: [SharedModule, PartyTypeAttributeRoutingModule],
  declarations: [
    PartyTypeAttributeComponent,
    PartyTypeAttributeDetailComponent,
    PartyTypeAttributeUpdateComponent,
    PartyTypeAttributeDeleteDialogComponent,
  ],
  entryComponents: [PartyTypeAttributeDeleteDialogComponent],
})
export class PartyTypeAttributeModule {}
