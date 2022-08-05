import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPestControl } from '../pest-control.model';
import { PestControlService } from '../service/pest-control.service';

@Component({
  templateUrl: './pest-control-delete-dialog.component.html',
})
export class PestControlDeleteDialogComponent {
  pestControl?: IPestControl;

  constructor(protected pestControlService: PestControlService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pestControlService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
