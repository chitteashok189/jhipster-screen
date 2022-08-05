import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInspectionRecord } from '../inspection-record.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-inspection-record-detail',
  templateUrl: './inspection-record-detail.component.html',
})
export class InspectionRecordDetailComponent implements OnInit {
  inspectionRecord: IInspectionRecord | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inspectionRecord }) => {
      this.inspectionRecord = inspectionRecord;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
