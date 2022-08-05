import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INutrient } from '../nutrient.model';
import { NutrientService } from '../service/nutrient.service';

@Component({
  templateUrl: './nutrient-delete-dialog.component.html',
})
export class NutrientDeleteDialogComponent {
  nutrient?: INutrient;

  constructor(protected nutrientService: NutrientService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nutrientService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
