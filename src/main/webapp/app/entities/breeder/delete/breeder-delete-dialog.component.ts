import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBreeder } from '../breeder.model';
import { BreederService } from '../service/breeder.service';

@Component({
  templateUrl: './breeder-delete-dialog.component.html',
})
export class BreederDeleteDialogComponent {
  breeder?: IBreeder;

  constructor(protected breederService: BreederService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.breederService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
