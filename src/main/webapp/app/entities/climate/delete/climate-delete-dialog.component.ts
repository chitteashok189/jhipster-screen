import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClimate } from '../climate.model';
import { ClimateService } from '../service/climate.service';

@Component({
  templateUrl: './climate-delete-dialog.component.html',
})
export class ClimateDeleteDialogComponent {
  climate?: IClimate;

  constructor(protected climateService: ClimateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.climateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
