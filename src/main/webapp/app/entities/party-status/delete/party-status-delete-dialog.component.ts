import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatus } from '../party-status.model';
import { PartyStatusService } from '../service/party-status.service';

@Component({
  templateUrl: './party-status-delete-dialog.component.html',
})
export class PartyStatusDeleteDialogComponent {
  partyStatus?: IPartyStatus;

  constructor(protected partyStatusService: PartyStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
