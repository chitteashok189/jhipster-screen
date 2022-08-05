import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRole } from '../party-role.model';
import { PartyRoleService } from '../service/party-role.service';

@Component({
  templateUrl: './party-role-delete-dialog.component.html',
})
export class PartyRoleDeleteDialogComponent {
  partyRole?: IPartyRole;

  constructor(protected partyRoleService: PartyRoleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyRoleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
