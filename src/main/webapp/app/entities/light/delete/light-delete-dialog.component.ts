import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILight } from '../light.model';
import { LightService } from '../service/light.service';

@Component({
  templateUrl: './light-delete-dialog.component.html',
})
export class LightDeleteDialogComponent {
  light?: ILight;

  constructor(protected lightService: LightService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lightService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
