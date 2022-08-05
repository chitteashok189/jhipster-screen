import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInspectionRecord, InspectionRecord } from '../inspection-record.model';
import { InspectionRecordService } from '../service/inspection-record.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IInspection } from 'app/entities/inspection/inspection.model';
import { InspectionService } from 'app/entities/inspection/service/inspection.service';
import { ILot } from 'app/entities/lot/lot.model';
import { LotService } from 'app/entities/lot/service/lot.service';

@Component({
  selector: 'jhi-inspection-record-update',
  templateUrl: './inspection-record-update.component.html',
})
export class InspectionRecordUpdateComponent implements OnInit {
  isSaving = false;

  inspectionsSharedCollection: IInspection[] = [];
  lotsSharedCollection: ILot[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    rawMaterialBatchNo: [],
    productBatchNo: [],
    rawMaterialBatchCode: [],
    inputBatchNo: [],
    inputBatchCode: [],
    attachments: [],
    attachmentsContentType: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    inspectionID: [],
    lotID: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected inspectionRecordService: InspectionRecordService,
    protected inspectionService: InspectionService,
    protected lotService: LotService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inspectionRecord }) => {
      if (inspectionRecord.id === undefined) {
        const today = dayjs().startOf('day');
        inspectionRecord.createdOn = today;
        inspectionRecord.updatedOn = today;
      }

      this.updateForm(inspectionRecord);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('blogApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inspectionRecord = this.createFromForm();
    if (inspectionRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectionRecordService.update(inspectionRecord));
    } else {
      this.subscribeToSaveResponse(this.inspectionRecordService.create(inspectionRecord));
    }
  }

  trackInspectionById(_index: number, item: IInspection): number {
    return item.id!;
  }

  trackLotById(_index: number, item: ILot): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspectionRecord>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(inspectionRecord: IInspectionRecord): void {
    this.editForm.patchValue({
      id: inspectionRecord.id,
      gUID: inspectionRecord.gUID,
      rawMaterialBatchNo: inspectionRecord.rawMaterialBatchNo,
      productBatchNo: inspectionRecord.productBatchNo,
      rawMaterialBatchCode: inspectionRecord.rawMaterialBatchCode,
      inputBatchNo: inspectionRecord.inputBatchNo,
      inputBatchCode: inspectionRecord.inputBatchCode,
      attachments: inspectionRecord.attachments,
      attachmentsContentType: inspectionRecord.attachmentsContentType,
      createdBy: inspectionRecord.createdBy,
      createdOn: inspectionRecord.createdOn ? inspectionRecord.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: inspectionRecord.updatedBy,
      updatedOn: inspectionRecord.updatedOn ? inspectionRecord.updatedOn.format(DATE_TIME_FORMAT) : null,
      inspectionID: inspectionRecord.inspectionID,
      lotID: inspectionRecord.lotID,
    });

    this.inspectionsSharedCollection = this.inspectionService.addInspectionToCollectionIfMissing(
      this.inspectionsSharedCollection,
      inspectionRecord.inspectionID
    );
    this.lotsSharedCollection = this.lotService.addLotToCollectionIfMissing(this.lotsSharedCollection, inspectionRecord.lotID);
  }

  protected loadRelationshipsOptions(): void {
    this.inspectionService
      .query()
      .pipe(map((res: HttpResponse<IInspection[]>) => res.body ?? []))
      .pipe(
        map((inspections: IInspection[]) =>
          this.inspectionService.addInspectionToCollectionIfMissing(inspections, this.editForm.get('inspectionID')!.value)
        )
      )
      .subscribe((inspections: IInspection[]) => (this.inspectionsSharedCollection = inspections));

    this.lotService
      .query()
      .pipe(map((res: HttpResponse<ILot[]>) => res.body ?? []))
      .pipe(map((lots: ILot[]) => this.lotService.addLotToCollectionIfMissing(lots, this.editForm.get('lotID')!.value)))
      .subscribe((lots: ILot[]) => (this.lotsSharedCollection = lots));
  }

  protected createFromForm(): IInspectionRecord {
    return {
      ...new InspectionRecord(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      rawMaterialBatchNo: this.editForm.get(['rawMaterialBatchNo'])!.value,
      productBatchNo: this.editForm.get(['productBatchNo'])!.value,
      rawMaterialBatchCode: this.editForm.get(['rawMaterialBatchCode'])!.value,
      inputBatchNo: this.editForm.get(['inputBatchNo'])!.value,
      inputBatchCode: this.editForm.get(['inputBatchCode'])!.value,
      attachmentsContentType: this.editForm.get(['attachmentsContentType'])!.value,
      attachments: this.editForm.get(['attachments'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      inspectionID: this.editForm.get(['inspectionID'])!.value,
      lotID: this.editForm.get(['lotID'])!.value,
    };
  }
}
