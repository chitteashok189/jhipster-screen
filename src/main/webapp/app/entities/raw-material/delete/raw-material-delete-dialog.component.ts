import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRawMaterial } from '../raw-material.model';
import { RawMaterialService } from '../service/raw-material.service';

@Component({
  templateUrl: './raw-material-delete-dialog.component.html',
})
export class RawMaterialDeleteDialogComponent {
  rawMaterial?: IRawMaterial;

  constructor(protected rawMaterialService: RawMaterialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rawMaterialService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
