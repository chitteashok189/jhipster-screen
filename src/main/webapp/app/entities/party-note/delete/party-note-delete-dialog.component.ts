import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyNote } from '../party-note.model';
import { PartyNoteService } from '../service/party-note.service';

@Component({
  templateUrl: './party-note-delete-dialog.component.html',
})
export class PartyNoteDeleteDialogComponent {
  partyNote?: IPartyNote;

  constructor(protected partyNoteService: PartyNoteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyNoteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
