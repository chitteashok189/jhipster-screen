import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatusItem } from '../party-status-item.model';
import { PartyStatusItemService } from '../service/party-status-item.service';

@Component({
  templateUrl: './party-status-item-delete-dialog.component.html',
})
export class PartyStatusItemDeleteDialogComponent {
  partyStatusItem?: IPartyStatusItem;

  constructor(protected partyStatusItemService: PartyStatusItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyStatusItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
