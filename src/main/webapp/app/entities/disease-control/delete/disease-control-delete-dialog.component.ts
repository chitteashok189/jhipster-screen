import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiseaseControl } from '../disease-control.model';
import { DiseaseControlService } from '../service/disease-control.service';

@Component({
  templateUrl: './disease-control-delete-dialog.component.html',
})
export class DiseaseControlDeleteDialogComponent {
  diseaseControl?: IDiseaseControl;

  constructor(protected diseaseControlService: DiseaseControlService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diseaseControlService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
