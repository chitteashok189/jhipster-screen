import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatusType } from '../party-status-type.model';
import { PartyStatusTypeService } from '../service/party-status-type.service';

@Component({
  templateUrl: './party-status-type-delete-dialog.component.html',
})
export class PartyStatusTypeDeleteDialogComponent {
  partyStatusType?: IPartyStatusType;

  constructor(protected partyStatusTypeService: PartyStatusTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyStatusTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
