import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPest } from '../pest.model';
import { PestService } from '../service/pest.service';

@Component({
  templateUrl: './pest-delete-dialog.component.html',
})
export class PestDeleteDialogComponent {
  pest?: IPest;

  constructor(protected pestService: PestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pestService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
