import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyRoleComponent } from './list/party-role.component';
import { PartyRoleDetailComponent } from './detail/party-role-detail.component';
import { PartyRoleUpdateComponent } from './update/party-role-update.component';
import { PartyRoleDeleteDialogComponent } from './delete/party-role-delete-dialog.component';
import { PartyRoleRoutingModule } from './route/party-role-routing.module';

@NgModule({
  imports: [SharedModule, PartyRoleRoutingModule],
  declarations: [PartyRoleComponent, PartyRoleDetailComponent, PartyRoleUpdateComponent, PartyRoleDeleteDialogComponent],
  entryComponents: [PartyRoleDeleteDialogComponent],
})
export class PartyRoleModule {}
