import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleType } from '../role-type.model';
import { RoleTypeService } from '../service/role-type.service';

@Component({
  templateUrl: './role-type-delete-dialog.component.html',
})
export class RoleTypeDeleteDialogComponent {
  roleType?: IRoleType;

  constructor(protected roleTypeService: RoleTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
