import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInspectionRecord } from '../inspection-record.model';
import { InspectionRecordService } from '../service/inspection-record.service';

@Component({
  templateUrl: './inspection-record-delete-dialog.component.html',
})
export class InspectionRecordDeleteDialogComponent {
  inspectionRecord?: IInspectionRecord;

  constructor(protected inspectionRecordService: InspectionRecordService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inspectionRecordService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
