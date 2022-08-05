import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyAttribute } from '../party-attribute.model';
import { PartyAttributeService } from '../service/party-attribute.service';

@Component({
  templateUrl: './party-attribute-delete-dialog.component.html',
})
export class PartyAttributeDeleteDialogComponent {
  partyAttribute?: IPartyAttribute;

  constructor(protected partyAttributeService: PartyAttributeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyAttributeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
