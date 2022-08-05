import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrop } from '../crop.model';
import { CropService } from '../service/crop.service';

@Component({
  templateUrl: './crop-delete-dialog.component.html',
})
export class CropDeleteDialogComponent {
  crop?: ICrop;

  constructor(protected cropService: CropService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
