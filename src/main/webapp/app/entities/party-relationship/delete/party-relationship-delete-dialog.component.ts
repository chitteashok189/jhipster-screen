import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRelationship } from '../party-relationship.model';
import { PartyRelationshipService } from '../service/party-relationship.service';

@Component({
  templateUrl: './party-relationship-delete-dialog.component.html',
})
export class PartyRelationshipDeleteDialogComponent {
  partyRelationship?: IPartyRelationship;

  constructor(protected partyRelationshipService: PartyRelationshipService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyRelationshipService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
