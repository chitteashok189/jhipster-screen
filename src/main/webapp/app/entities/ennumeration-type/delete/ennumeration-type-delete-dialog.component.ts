import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnnumerationType } from '../ennumeration-type.model';
import { EnnumerationTypeService } from '../service/ennumeration-type.service';

@Component({
  templateUrl: './ennumeration-type-delete-dialog.component.html',
})
export class EnnumerationTypeDeleteDialogComponent {
  ennumerationType?: IEnnumerationType;

  constructor(protected ennumerationTypeService: EnnumerationTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ennumerationTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
