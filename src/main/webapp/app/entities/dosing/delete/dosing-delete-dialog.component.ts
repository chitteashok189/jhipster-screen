import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDosing } from '../dosing.model';
import { DosingService } from '../service/dosing.service';

@Component({
  templateUrl: './dosing-delete-dialog.component.html',
})
export class DosingDeleteDialogComponent {
  dosing?: IDosing;

  constructor(protected dosingService: DosingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dosingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
