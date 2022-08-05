import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHarvest, Harvest } from '../harvest.model';
import { HarvestService } from '../service/harvest.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ILot } from 'app/entities/lot/lot.model';
import { LotService } from 'app/entities/lot/service/lot.service';

@Component({
  selector: 'jhi-harvest-update',
  templateUrl: './harvest-update.component.html',
})
export class HarvestUpdateComponent implements OnInit {
  isSaving = false;

  cropsSharedCollection: ICrop[] = [];
  lotsSharedCollection: ILot[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    harvestingDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    cropID: [],
    lotID: [],
  });

  constructor(
    protected harvestService: HarvestService,
    protected cropService: CropService,
    protected lotService: LotService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ harvest }) => {
      if (harvest.id === undefined) {
        const today = dayjs().startOf('day');
        harvest.harvestingDate = today;
        harvest.createdOn = today;
        harvest.updatedOn = today;
      }

      this.updateForm(harvest);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const harvest = this.createFromForm();
    if (harvest.id !== undefined) {
      this.subscribeToSaveResponse(this.harvestService.update(harvest));
    } else {
      this.subscribeToSaveResponse(this.harvestService.create(harvest));
    }
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  trackLotById(_index: number, item: ILot): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHarvest>>): void {
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

  protected updateForm(harvest: IHarvest): void {
    this.editForm.patchValue({
      id: harvest.id,
      gUID: harvest.gUID,
      harvestingDate: harvest.harvestingDate ? harvest.harvestingDate.format(DATE_TIME_FORMAT) : null,
      createdBy: harvest.createdBy,
      createdOn: harvest.createdOn ? harvest.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: harvest.updatedBy,
      updatedOn: harvest.updatedOn ? harvest.updatedOn.format(DATE_TIME_FORMAT) : null,
      cropID: harvest.cropID,
      lotID: harvest.lotID,
    });

    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, harvest.cropID);
    this.lotsSharedCollection = this.lotService.addLotToCollectionIfMissing(this.lotsSharedCollection, harvest.lotID);
  }

  protected loadRelationshipsOptions(): void {
    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));

    this.lotService
      .query()
      .pipe(map((res: HttpResponse<ILot[]>) => res.body ?? []))
      .pipe(map((lots: ILot[]) => this.lotService.addLotToCollectionIfMissing(lots, this.editForm.get('lotID')!.value)))
      .subscribe((lots: ILot[]) => (this.lotsSharedCollection = lots));
  }

  protected createFromForm(): IHarvest {
    return {
      ...new Harvest(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      harvestingDate: this.editForm.get(['harvestingDate'])!.value
        ? dayjs(this.editForm.get(['harvestingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      cropID: this.editForm.get(['cropID'])!.value,
      lotID: this.editForm.get(['lotID'])!.value,
    };
  }
}
