import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceLevel } from '../device-level.model';
import { DeviceLevelService } from '../service/device-level.service';

@Component({
  templateUrl: './device-level-delete-dialog.component.html',
})
export class DeviceLevelDeleteDialogComponent {
  deviceLevel?: IDeviceLevel;

  constructor(protected deviceLevelService: DeviceLevelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceLevelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
