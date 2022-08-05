import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IScouting, Scouting } from '../scouting.model';
import { ScoutingService } from '../service/scouting.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ScoutingType } from 'app/entities/enumerations/scouting-type.model';
import { CropState } from 'app/entities/enumerations/crop-state.model';

@Component({
  selector: 'jhi-scouting-update',
  templateUrl: './scouting-update.component.html',
})
export class ScoutingUpdateComponent implements OnInit {
  isSaving = false;
  scoutingTypeValues = Object.keys(ScoutingType);
  cropStateValues = Object.keys(CropState);

  plantFactoriesSharedCollection: IPlantFactory[] = [];
  cropsSharedCollection: ICrop[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    scoutingDate: [],
    scout: [],
    scoutingType: [],
    scoutingCoordinates: [],
    scoutingArea: [],
    cropState: [],
    comments: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantFactoryID: [],
    cropID: [],
  });

  constructor(
    protected scoutingService: ScoutingService,
    protected plantFactoryService: PlantFactoryService,
    protected cropService: CropService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scouting }) => {
      if (scouting.id === undefined) {
        const today = dayjs().startOf('day');
        scouting.scoutingDate = today;
        scouting.createdOn = today;
        scouting.updatedOn = today;
      }

      this.updateForm(scouting);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scouting = this.createFromForm();
    if (scouting.id !== undefined) {
      this.subscribeToSaveResponse(this.scoutingService.update(scouting));
    } else {
      this.subscribeToSaveResponse(this.scoutingService.create(scouting));
    }
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScouting>>): void {
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

  protected updateForm(scouting: IScouting): void {
    this.editForm.patchValue({
      id: scouting.id,
      gUID: scouting.gUID,
      scoutingDate: scouting.scoutingDate ? scouting.scoutingDate.format(DATE_TIME_FORMAT) : null,
      scout: scouting.scout,
      scoutingType: scouting.scoutingType,
      scoutingCoordinates: scouting.scoutingCoordinates,
      scoutingArea: scouting.scoutingArea,
      cropState: scouting.cropState,
      comments: scouting.comments,
      createdBy: scouting.createdBy,
      createdOn: scouting.createdOn ? scouting.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: scouting.updatedBy,
      updatedOn: scouting.updatedOn ? scouting.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantFactoryID: scouting.plantFactoryID,
      cropID: scouting.cropID,
    });

    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      scouting.plantFactoryID
    );
    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, scouting.cropID);
  }

  protected loadRelationshipsOptions(): void {
    this.plantFactoryService
      .query()
      .pipe(map((res: HttpResponse<IPlantFactory[]>) => res.body ?? []))
      .pipe(
        map((plantFactories: IPlantFactory[]) =>
          this.plantFactoryService.addPlantFactoryToCollectionIfMissing(plantFactories, this.editForm.get('plantFactoryID')!.value)
        )
      )
      .subscribe((plantFactories: IPlantFactory[]) => (this.plantFactoriesSharedCollection = plantFactories));

    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));
  }

  protected createFromForm(): IScouting {
    return {
      ...new Scouting(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      scoutingDate: this.editForm.get(['scoutingDate'])!.value
        ? dayjs(this.editForm.get(['scoutingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      scout: this.editForm.get(['scout'])!.value,
      scoutingType: this.editForm.get(['scoutingType'])!.value,
      scoutingCoordinates: this.editForm.get(['scoutingCoordinates'])!.value,
      scoutingArea: this.editForm.get(['scoutingArea'])!.value,
      cropState: this.editForm.get(['cropState'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
      cropID: this.editForm.get(['cropID'])!.value,
    };
  }
}
