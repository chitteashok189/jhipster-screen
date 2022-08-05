import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyTypeAttribute } from '../party-type-attribute.model';
import { PartyTypeAttributeService } from '../service/party-type-attribute.service';

@Component({
  templateUrl: './party-type-attribute-delete-dialog.component.html',
})
export class PartyTypeAttributeDeleteDialogComponent {
  partyTypeAttribute?: IPartyTypeAttribute;

  constructor(protected partyTypeAttributeService: PartyTypeAttributeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyTypeAttributeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
