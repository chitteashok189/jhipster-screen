import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIrrigation } from '../irrigation.model';
import { IrrigationService } from '../service/irrigation.service';

@Component({
  templateUrl: './irrigation-delete-dialog.component.html',
})
export class IrrigationDeleteDialogComponent {
  irrigation?: IIrrigation;

  constructor(protected irrigationService: IrrigationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.irrigationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
