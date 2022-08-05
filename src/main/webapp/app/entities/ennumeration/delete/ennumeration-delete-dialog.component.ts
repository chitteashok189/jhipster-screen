import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnnumeration } from '../ennumeration.model';
import { EnnumerationService } from '../service/ennumeration.service';

@Component({
  templateUrl: './ennumeration-delete-dialog.component.html',
})
export class EnnumerationDeleteDialogComponent {
  ennumeration?: IEnnumeration;

  constructor(protected ennumerationService: EnnumerationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ennumerationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
