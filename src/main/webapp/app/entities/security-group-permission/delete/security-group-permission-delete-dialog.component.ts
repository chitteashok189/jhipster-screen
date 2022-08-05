import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityGroupPermission } from '../security-group-permission.model';
import { SecurityGroupPermissionService } from '../service/security-group-permission.service';

@Component({
  templateUrl: './security-group-permission-delete-dialog.component.html',
})
export class SecurityGroupPermissionDeleteDialogComponent {
  securityGroupPermission?: ISecurityGroupPermission;

  constructor(protected securityGroupPermissionService: SecurityGroupPermissionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityGroupPermissionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
