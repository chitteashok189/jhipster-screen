import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISensorModel } from '../sensor-model.model';
import { SensorModelService } from '../service/sensor-model.service';

@Component({
  templateUrl: './sensor-model-delete-dialog.component.html',
})
export class SensorModelDeleteDialogComponent {
  sensorModel?: ISensorModel;

  constructor(protected sensorModelService: SensorModelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sensorModelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
