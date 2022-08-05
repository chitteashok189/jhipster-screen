import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IScouting } from '../scouting.model';
import { ScoutingService } from '../service/scouting.service';

@Component({
  templateUrl: './scouting-delete-dialog.component.html',
})
export class ScoutingDeleteDialogComponent {
  scouting?: IScouting;

  constructor(protected scoutingService: ScoutingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scoutingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
