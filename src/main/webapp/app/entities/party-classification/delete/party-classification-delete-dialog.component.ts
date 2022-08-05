import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyClassification } from '../party-classification.model';
import { PartyClassificationService } from '../service/party-classification.service';

@Component({
  templateUrl: './party-classification-delete-dialog.component.html',
})
export class PartyClassificationDeleteDialogComponent {
  partyClassification?: IPartyClassification;

  constructor(protected partyClassificationService: PartyClassificationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyClassificationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
