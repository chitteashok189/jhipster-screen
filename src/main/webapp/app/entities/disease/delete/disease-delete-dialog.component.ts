import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisease } from '../disease.model';
import { DiseaseService } from '../service/disease.service';

@Component({
  templateUrl: './disease-delete-dialog.component.html',
})
export class DiseaseDeleteDialogComponent {
  disease?: IDisease;

  constructor(protected diseaseService: DiseaseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diseaseService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
