import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISeed } from '../seed.model';
import { SeedService } from '../service/seed.service';

@Component({
  templateUrl: './seed-delete-dialog.component.html',
})
export class SeedDeleteDialogComponent {
  seed?: ISeed;

  constructor(protected seedService: SeedService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.seedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
