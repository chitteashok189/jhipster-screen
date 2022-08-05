import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisorder } from '../disorder.model';
import { DisorderService } from '../service/disorder.service';

@Component({
  templateUrl: './disorder-delete-dialog.component.html',
})
export class DisorderDeleteDialogComponent {
  disorder?: IDisorder;

  constructor(protected disorderService: DisorderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disorderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
