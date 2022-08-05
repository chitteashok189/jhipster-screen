import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInspectionRecord } from '../inspection-record.model';
import { InspectionRecordService } from '../service/inspection-record.service';
import { InspectionRecordDeleteDialogComponent } from '../delete/inspection-record-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-inspection-record',
  templateUrl: './inspection-record.component.html',
})
export class InspectionRecordComponent implements OnInit {
  inspectionRecords?: IInspectionRecord[];
  isLoading = false;

  constructor(
    protected inspectionRecordService: InspectionRecordService,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.isLoading = true;

    this.inspectionRecordService.query().subscribe({
      next: (res: HttpResponse<IInspectionRecord[]>) => {
        this.isLoading = false;
        this.inspectionRecords = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IInspectionRecord): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(inspectionRecord: IInspectionRecord): void {
    const modalRef = this.modalService.open(InspectionRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inspectionRecord = inspectionRecord;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
