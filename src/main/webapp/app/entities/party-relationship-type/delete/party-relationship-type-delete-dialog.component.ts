import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRelationshipType } from '../party-relationship-type.model';
import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';

@Component({
  templateUrl: './party-relationship-type-delete-dialog.component.html',
})
export class PartyRelationshipTypeDeleteDialogComponent {
  partyRelationshipType?: IPartyRelationshipType;

  constructor(protected partyRelationshipTypeService: PartyRelationshipTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyRelationshipTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
