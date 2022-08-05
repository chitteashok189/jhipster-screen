import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityGroup } from '../security-group.model';
import { SecurityGroupService } from '../service/security-group.service';

@Component({
  templateUrl: './security-group-delete-dialog.component.html',
})
export class SecurityGroupDeleteDialogComponent {
  securityGroup?: ISecurityGroup;

  constructor(protected securityGroupService: SecurityGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityGroupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
