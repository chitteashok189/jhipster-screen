import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrowBed } from '../grow-bed.model';
import { GrowBedService } from '../service/grow-bed.service';

@Component({
  templateUrl: './grow-bed-delete-dialog.component.html',
})
export class GrowBedDeleteDialogComponent {
  growBed?: IGrowBed;

  constructor(protected growBedService: GrowBedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.growBedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
