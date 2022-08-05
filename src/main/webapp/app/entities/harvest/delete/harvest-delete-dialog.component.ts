import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHarvest } from '../harvest.model';
import { HarvestService } from '../service/harvest.service';

@Component({
  templateUrl: './harvest-delete-dialog.component.html',
})
export class HarvestDeleteDialogComponent {
  harvest?: IHarvest;

  constructor(protected harvestService: HarvestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.harvestService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
