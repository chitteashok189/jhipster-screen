import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartyNoteComponent } from './list/party-note.component';
import { PartyNoteDetailComponent } from './detail/party-note-detail.component';
import { PartyNoteUpdateComponent } from './update/party-note-update.component';
import { PartyNoteDeleteDialogComponent } from './delete/party-note-delete-dialog.component';
import { PartyNoteRoutingModule } from './route/party-note-routing.module';

@NgModule({
  imports: [SharedModule, PartyNoteRoutingModule],
  declarations: [PartyNoteComponent, PartyNoteDetailComponent, PartyNoteUpdateComponent, PartyNoteDeleteDialogComponent],
  entryComponents: [PartyNoteDeleteDialogComponent],
})
export class PartyNoteModule {}
