import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';

@Component({
  templateUrl: './application-user-security-group-delete-dialog.component.html',
})
export class ApplicationUserSecurityGroupDeleteDialogComponent {
  applicationUserSecurityGroup?: IApplicationUserSecurityGroup;

  constructor(protected applicationUserSecurityGroupService: ApplicationUserSecurityGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicationUserSecurityGroupService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
