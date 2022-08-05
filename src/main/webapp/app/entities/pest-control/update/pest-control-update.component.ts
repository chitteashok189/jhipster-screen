import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPestControl, PestControl } from '../pest-control.model';
import { PestControlService } from '../service/pest-control.service';
import { IPest } from 'app/entities/pest/pest.model';
import { PestService } from 'app/entities/pest/service/pest.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ConType } from 'app/entities/enumerations/con-type.model';

@Component({
  selector: 'jhi-pest-control-update',
  templateUrl: './pest-control-update.component.html',
})
export class PestControlUpdateComponent implements OnInit {
  isSaving = false;
  conTypeValues = Object.keys(ConType);

  pestsSharedCollection: IPest[] = [];
  cropsSharedCollection: ICrop[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    natureOfDamage: [],
    controlType: [],
    controlMeasures: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    pestID: [],
    cropID: [],
  });

  constructor(
    protected pestControlService: PestControlService,
    protected pestService: PestService,
    protected cropService: CropService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pestControl }) => {
      if (pestControl.id === undefined) {
        const today = dayjs().startOf('day');
        pestControl.createdOn = today;
        pestControl.updatedOn = today;
      }

      this.updateForm(pestControl);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pestControl = this.createFromForm();
    if (pestControl.id !== undefined) {
      this.subscribeToSaveResponse(this.pestControlService.update(pestControl));
    } else {
      this.subscribeToSaveResponse(this.pestControlService.create(pestControl));
    }
  }

  trackPestById(_index: number, item: IPest): number {
    return item.id!;
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPestControl>>): void {
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

  protected updateForm(pestControl: IPestControl): void {
    this.editForm.patchValue({
      id: pestControl.id,
      gUID: pestControl.gUID,
      natureOfDamage: pestControl.natureOfDamage,
      controlType: pestControl.controlType,
      controlMeasures: pestControl.controlMeasures,
      createdBy: pestControl.createdBy,
      createdOn: pestControl.createdOn ? pestControl.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: pestControl.updatedBy,
      updatedOn: pestControl.updatedOn ? pestControl.updatedOn.format(DATE_TIME_FORMAT) : null,
      pestID: pestControl.pestID,
      cropID: pestControl.cropID,
    });

    this.pestsSharedCollection = this.pestService.addPestToCollectionIfMissing(this.pestsSharedCollection, pestControl.pestID);
    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, pestControl.cropID);
  }

  protected loadRelationshipsOptions(): void {
    this.pestService
      .query()
      .pipe(map((res: HttpResponse<IPest[]>) => res.body ?? []))
      .pipe(map((pests: IPest[]) => this.pestService.addPestToCollectionIfMissing(pests, this.editForm.get('pestID')!.value)))
      .subscribe((pests: IPest[]) => (this.pestsSharedCollection = pests));

    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));
  }

  protected createFromForm(): IPestControl {
    return {
      ...new PestControl(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      natureOfDamage: this.editForm.get(['natureOfDamage'])!.value,
      controlType: this.editForm.get(['controlType'])!.value,
      controlMeasures: this.editForm.get(['controlMeasures'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      pestID: this.editForm.get(['pestID'])!.value,
      cropID: this.editForm.get(['cropID'])!.value,
    };
  }
}
