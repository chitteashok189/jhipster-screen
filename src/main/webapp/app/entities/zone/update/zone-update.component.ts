import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IZone, Zone } from '../zone.model';
import { ZoneService } from '../service/zone.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IGrowBed } from 'app/entities/grow-bed/grow-bed.model';
import { GrowBedService } from 'app/entities/grow-bed/service/grow-bed.service';
import { ZoneType } from 'app/entities/enumerations/zone-type.model';

@Component({
  selector: 'jhi-zone-update',
  templateUrl: './zone-update.component.html',
})
export class ZoneUpdateComponent implements OnInit {
  isSaving = false;
  zoneTypeValues = Object.keys(ZoneType);

  plantFactoriesSharedCollection: IPlantFactory[] = [];
  growBedsSharedCollection: IGrowBed[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    zoneType: [],
    zoneName: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantFactoryID: [],
    growBedID: [],
  });

  constructor(
    protected zoneService: ZoneService,
    protected plantFactoryService: PlantFactoryService,
    protected growBedService: GrowBedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zone }) => {
      if (zone.id === undefined) {
        const today = dayjs().startOf('day');
        zone.createdOn = today;
        zone.updatedOn = today;
      }

      this.updateForm(zone);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zone = this.createFromForm();
    if (zone.id !== undefined) {
      this.subscribeToSaveResponse(this.zoneService.update(zone));
    } else {
      this.subscribeToSaveResponse(this.zoneService.create(zone));
    }
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  trackGrowBedById(_index: number, item: IGrowBed): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>): void {
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

  protected updateForm(zone: IZone): void {
    this.editForm.patchValue({
      id: zone.id,
      gUID: zone.gUID,
      zoneType: zone.zoneType,
      zoneName: zone.zoneName,
      createdBy: zone.createdBy,
      createdOn: zone.createdOn ? zone.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: zone.updatedBy,
      updatedOn: zone.updatedOn ? zone.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantFactoryID: zone.plantFactoryID,
      growBedID: zone.growBedID,
    });

    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      zone.plantFactoryID
    );
    this.growBedsSharedCollection = this.growBedService.addGrowBedToCollectionIfMissing(this.growBedsSharedCollection, zone.growBedID);
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

    this.growBedService
      .query()
      .pipe(map((res: HttpResponse<IGrowBed[]>) => res.body ?? []))
      .pipe(
        map((growBeds: IGrowBed[]) => this.growBedService.addGrowBedToCollectionIfMissing(growBeds, this.editForm.get('growBedID')!.value))
      )
      .subscribe((growBeds: IGrowBed[]) => (this.growBedsSharedCollection = growBeds));
  }

  protected createFromForm(): IZone {
    return {
      ...new Zone(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      zoneType: this.editForm.get(['zoneType'])!.value,
      zoneName: this.editForm.get(['zoneName'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
      growBedID: this.editForm.get(['growBedID'])!.value,
    };
  }
}
