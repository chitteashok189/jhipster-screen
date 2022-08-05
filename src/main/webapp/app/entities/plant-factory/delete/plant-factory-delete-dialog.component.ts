import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantFactory } from '../plant-factory.model';
import { PlantFactoryService } from '../service/plant-factory.service';

@Component({
  templateUrl: './plant-factory-delete-dialog.component.html',
})
export class PlantFactoryDeleteDialogComponent {
  plantFactory?: IPlantFactory;

  constructor(protected plantFactoryService: PlantFactoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plantFactoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
