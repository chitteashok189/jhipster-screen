import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleTypeAttribute } from '../role-type-attribute.model';
import { RoleTypeAttributeService } from '../service/role-type-attribute.service';

@Component({
  templateUrl: './role-type-attribute-delete-dialog.component.html',
})
export class RoleTypeAttributeDeleteDialogComponent {
  roleTypeAttribute?: IRoleTypeAttribute;

  constructor(protected roleTypeAttributeService: RoleTypeAttributeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.roleTypeAttributeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
