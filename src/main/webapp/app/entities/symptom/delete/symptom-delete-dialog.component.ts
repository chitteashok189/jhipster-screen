import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';

@Component({
  templateUrl: './symptom-delete-dialog.component.html',
})
export class SymptomDeleteDialogComponent {
  symptom?: ISymptom;

  constructor(protected symptomService: SymptomService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.symptomService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
