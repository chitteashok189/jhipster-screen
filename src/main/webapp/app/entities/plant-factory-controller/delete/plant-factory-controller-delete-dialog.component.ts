import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantFactoryController } from '../plant-factory-controller.model';
import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';

@Component({
  templateUrl: './plant-factory-controller-delete-dialog.component.html',
})
export class PlantFactoryControllerDeleteDialogComponent {
  plantFactoryController?: IPlantFactoryController;

  constructor(protected plantFactoryControllerService: PlantFactoryControllerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plantFactoryControllerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
