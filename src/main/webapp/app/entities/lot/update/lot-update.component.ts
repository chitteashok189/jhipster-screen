import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILot, Lot } from '../lot.model';
import { LotService } from '../service/lot.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISeed } from 'app/entities/seed/seed.model';
import { SeedService } from 'app/entities/seed/service/seed.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { Unit } from 'app/entities/enumerations/unit.model';
import { Sowing } from 'app/entities/enumerations/sowing.model';
import { Transplantation } from 'app/entities/enumerations/transplantation.model';
import { HarTime } from 'app/entities/enumerations/har-time.model';

@Component({
  selector: 'jhi-lot-update',
  templateUrl: './lot-update.component.html',
})
export class LotUpdateComponent implements OnInit {
  isSaving = false;
  unitValues = Object.keys(Unit);
  sowingValues = Object.keys(Sowing);
  transplantationValues = Object.keys(Transplantation);
  harTimeValues = Object.keys(HarTime);

  seedsSharedCollection: ISeed[] = [];
  cropsSharedCollection: ICrop[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    lotCode: [],
    lotQRCode: [],
    lotQRCodeContentType: [],
    lotSize: [],
    unitType: [],
    seedlingsGerminated: [],
    seedlingsDied: [],
    plantsWasted: [],
    traysSown: [],
    sowingTime: [],
    traysTranplanted: [],
    transplantationTime: [],
    plantsHarvested: [],
    harvestTime: [],
    packingDate: [],
    tranportationDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    seedID: [],
    cropID: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected lotService: LotService,
    protected seedService: SeedService,
    protected cropService: CropService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lot }) => {
      if (lot.id === undefined) {
        const today = dayjs().startOf('day');
        lot.packingDate = today;
        lot.tranportationDate = today;
        lot.createdOn = today;
        lot.updatedOn = today;
      }

      this.updateForm(lot);

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
    const lot = this.createFromForm();
    if (lot.id !== undefined) {
      this.subscribeToSaveResponse(this.lotService.update(lot));
    } else {
      this.subscribeToSaveResponse(this.lotService.create(lot));
    }
  }

  trackSeedById(_index: number, item: ISeed): number {
    return item.id!;
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILot>>): void {
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

  protected updateForm(lot: ILot): void {
    this.editForm.patchValue({
      id: lot.id,
      gUID: lot.gUID,
      lotCode: lot.lotCode,
      lotQRCode: lot.lotQRCode,
      lotQRCodeContentType: lot.lotQRCodeContentType,
      lotSize: lot.lotSize,
      unitType: lot.unitType,
      seedlingsGerminated: lot.seedlingsGerminated,
      seedlingsDied: lot.seedlingsDied,
      plantsWasted: lot.plantsWasted,
      traysSown: lot.traysSown,
      sowingTime: lot.sowingTime,
      traysTranplanted: lot.traysTranplanted,
      transplantationTime: lot.transplantationTime,
      plantsHarvested: lot.plantsHarvested,
      harvestTime: lot.harvestTime,
      packingDate: lot.packingDate ? lot.packingDate.format(DATE_TIME_FORMAT) : null,
      tranportationDate: lot.tranportationDate ? lot.tranportationDate.format(DATE_TIME_FORMAT) : null,
      createdBy: lot.createdBy,
      createdOn: lot.createdOn ? lot.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: lot.updatedBy,
      updatedOn: lot.updatedOn ? lot.updatedOn.format(DATE_TIME_FORMAT) : null,
      seedID: lot.seedID,
      cropID: lot.cropID,
    });

    this.seedsSharedCollection = this.seedService.addSeedToCollectionIfMissing(this.seedsSharedCollection, lot.seedID);
    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, lot.cropID);
  }

  protected loadRelationshipsOptions(): void {
    this.seedService
      .query()
      .pipe(map((res: HttpResponse<ISeed[]>) => res.body ?? []))
      .pipe(map((seeds: ISeed[]) => this.seedService.addSeedToCollectionIfMissing(seeds, this.editForm.get('seedID')!.value)))
      .subscribe((seeds: ISeed[]) => (this.seedsSharedCollection = seeds));

    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));
  }

  protected createFromForm(): ILot {
    return {
      ...new Lot(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      lotCode: this.editForm.get(['lotCode'])!.value,
      lotQRCodeContentType: this.editForm.get(['lotQRCodeContentType'])!.value,
      lotQRCode: this.editForm.get(['lotQRCode'])!.value,
      lotSize: this.editForm.get(['lotSize'])!.value,
      unitType: this.editForm.get(['unitType'])!.value,
      seedlingsGerminated: this.editForm.get(['seedlingsGerminated'])!.value,
      seedlingsDied: this.editForm.get(['seedlingsDied'])!.value,
      plantsWasted: this.editForm.get(['plantsWasted'])!.value,
      traysSown: this.editForm.get(['traysSown'])!.value,
      sowingTime: this.editForm.get(['sowingTime'])!.value,
      traysTranplanted: this.editForm.get(['traysTranplanted'])!.value,
      transplantationTime: this.editForm.get(['transplantationTime'])!.value,
      plantsHarvested: this.editForm.get(['plantsHarvested'])!.value,
      harvestTime: this.editForm.get(['harvestTime'])!.value,
      packingDate: this.editForm.get(['packingDate'])!.value
        ? dayjs(this.editForm.get(['packingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      tranportationDate: this.editForm.get(['tranportationDate'])!.value
        ? dayjs(this.editForm.get(['tranportationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      seedID: this.editForm.get(['seedID'])!.value,
      cropID: this.editForm.get(['cropID'])!.value,
    };
  }
}
