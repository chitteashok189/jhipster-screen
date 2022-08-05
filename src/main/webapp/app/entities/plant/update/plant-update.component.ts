import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPlant, Plant } from '../plant.model';
import { PlantService } from '../service/plant.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { Seeding } from 'app/entities/enumerations/seeding.model';
import { TransplantMonth } from 'app/entities/enumerations/transplant-month.model';
import { HarvestMonth } from 'app/entities/enumerations/harvest-month.model';

@Component({
  selector: 'jhi-plant-update',
  templateUrl: './plant-update.component.html',
})
export class PlantUpdateComponent implements OnInit {
  isSaving = false;
  seedingValues = Object.keys(Seeding);
  transplantMonthValues = Object.keys(TransplantMonth);
  harvestMonthValues = Object.keys(HarvestMonth);

  cropsSharedCollection: ICrop[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    commonName: [],
    scientificName: [],
    familyName: [],
    plantSpacing: [],
    seedingMonth: [],
    transplantMonth: [],
    harvestMonth: [],
    originCountry: [],
    yearlyCrops: [],
    nativeTemperature: [],
    nativeHumidity: [],
    nativeDayDuration: [],
    nativeNightDuration: [],
    nativeSoilMoisture: [],
    plantingPeriod: [],
    yieldUnit: [],
    growthHeightMin: [],
    growthHeightMax: [],
    grownSpreadMin: [],
    grownSpreadMax: [],
    grownWeightMax: [],
    grownWeightMin: [],
    growingMedia: [],
    documents: [],
    notes: [],
    attachments: [],
    attachmentsContentType: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    cropID: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected plantService: PlantService,
    protected cropService: CropService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plant }) => {
      if (plant.id === undefined) {
        const today = dayjs().startOf('day');
        plant.createdOn = today;
        plant.updatedOn = today;
      }

      this.updateForm(plant);

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
    const plant = this.createFromForm();
    if (plant.id !== undefined) {
      this.subscribeToSaveResponse(this.plantService.update(plant));
    } else {
      this.subscribeToSaveResponse(this.plantService.create(plant));
    }
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlant>>): void {
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

  protected updateForm(plant: IPlant): void {
    this.editForm.patchValue({
      id: plant.id,
      gUID: plant.gUID,
      commonName: plant.commonName,
      scientificName: plant.scientificName,
      familyName: plant.familyName,
      plantSpacing: plant.plantSpacing,
      seedingMonth: plant.seedingMonth,
      transplantMonth: plant.transplantMonth,
      harvestMonth: plant.harvestMonth,
      originCountry: plant.originCountry,
      yearlyCrops: plant.yearlyCrops,
      nativeTemperature: plant.nativeTemperature,
      nativeHumidity: plant.nativeHumidity,
      nativeDayDuration: plant.nativeDayDuration,
      nativeNightDuration: plant.nativeNightDuration,
      nativeSoilMoisture: plant.nativeSoilMoisture,
      plantingPeriod: plant.plantingPeriod,
      yieldUnit: plant.yieldUnit,
      growthHeightMin: plant.growthHeightMin,
      growthHeightMax: plant.growthHeightMax,
      grownSpreadMin: plant.grownSpreadMin,
      grownSpreadMax: plant.grownSpreadMax,
      grownWeightMax: plant.grownWeightMax,
      grownWeightMin: plant.grownWeightMin,
      growingMedia: plant.growingMedia,
      documents: plant.documents,
      notes: plant.notes,
      attachments: plant.attachments,
      attachmentsContentType: plant.attachmentsContentType,
      createdBy: plant.createdBy,
      createdOn: plant.createdOn ? plant.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: plant.updatedBy,
      updatedOn: plant.updatedOn ? plant.updatedOn.format(DATE_TIME_FORMAT) : null,
      cropID: plant.cropID,
    });

    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, plant.cropID);
  }

  protected loadRelationshipsOptions(): void {
    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));
  }

  protected createFromForm(): IPlant {
    return {
      ...new Plant(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      commonName: this.editForm.get(['commonName'])!.value,
      scientificName: this.editForm.get(['scientificName'])!.value,
      familyName: this.editForm.get(['familyName'])!.value,
      plantSpacing: this.editForm.get(['plantSpacing'])!.value,
      seedingMonth: this.editForm.get(['seedingMonth'])!.value,
      transplantMonth: this.editForm.get(['transplantMonth'])!.value,
      harvestMonth: this.editForm.get(['harvestMonth'])!.value,
      originCountry: this.editForm.get(['originCountry'])!.value,
      yearlyCrops: this.editForm.get(['yearlyCrops'])!.value,
      nativeTemperature: this.editForm.get(['nativeTemperature'])!.value,
      nativeHumidity: this.editForm.get(['nativeHumidity'])!.value,
      nativeDayDuration: this.editForm.get(['nativeDayDuration'])!.value,
      nativeNightDuration: this.editForm.get(['nativeNightDuration'])!.value,
      nativeSoilMoisture: this.editForm.get(['nativeSoilMoisture'])!.value,
      plantingPeriod: this.editForm.get(['plantingPeriod'])!.value,
      yieldUnit: this.editForm.get(['yieldUnit'])!.value,
      growthHeightMin: this.editForm.get(['growthHeightMin'])!.value,
      growthHeightMax: this.editForm.get(['growthHeightMax'])!.value,
      grownSpreadMin: this.editForm.get(['grownSpreadMin'])!.value,
      grownSpreadMax: this.editForm.get(['grownSpreadMax'])!.value,
      grownWeightMax: this.editForm.get(['grownWeightMax'])!.value,
      grownWeightMin: this.editForm.get(['grownWeightMin'])!.value,
      growingMedia: this.editForm.get(['growingMedia'])!.value,
      documents: this.editForm.get(['documents'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      attachmentsContentType: this.editForm.get(['attachmentsContentType'])!.value,
      attachments: this.editForm.get(['attachments'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      cropID: this.editForm.get(['cropID'])!.value,
    };
  }
}
