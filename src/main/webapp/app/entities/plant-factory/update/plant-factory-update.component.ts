import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPlantFactory, PlantFactory } from '../plant-factory.model';
import { PlantFactoryService } from '../service/plant-factory.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IFarm } from 'app/entities/farm/farm.model';
import { FarmService } from 'app/entities/farm/service/farm.service';
import { ProFarmType } from 'app/entities/enumerations/pro-farm-type.model';
import { ProSubType } from 'app/entities/enumerations/pro-sub-type.model';

@Component({
  selector: 'jhi-plant-factory-update',
  templateUrl: './plant-factory-update.component.html',
})
export class PlantFactoryUpdateComponent implements OnInit {
  isSaving = false;
  proFarmTypeValues = Object.keys(ProFarmType);
  proSubTypeValues = Object.keys(ProSubType);

  devicesSharedCollection: IDevice[] = [];
  farmsSharedCollection: IFarm[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    plantFactoryName: [],
    plantFactoryTypeID: [],
    plantFactorySubType: [],
    plantFactoryDescription: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    farmID: [],
    deviceID: [],
  });

  constructor(
    protected plantFactoryService: PlantFactoryService,
    protected deviceService: DeviceService,
    protected farmService: FarmService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantFactory }) => {
      if (plantFactory.id === undefined) {
        const today = dayjs().startOf('day');
        plantFactory.createdOn = today;
        plantFactory.updatedOn = today;
      }

      this.updateForm(plantFactory);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plantFactory = this.createFromForm();
    if (plantFactory.id !== undefined) {
      this.subscribeToSaveResponse(this.plantFactoryService.update(plantFactory));
    } else {
      this.subscribeToSaveResponse(this.plantFactoryService.create(plantFactory));
    }
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackFarmById(_index: number, item: IFarm): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantFactory>>): void {
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

  protected updateForm(plantFactory: IPlantFactory): void {
    this.editForm.patchValue({
      id: plantFactory.id,
      gUID: plantFactory.gUID,
      plantFactoryName: plantFactory.plantFactoryName,
      plantFactoryTypeID: plantFactory.plantFactoryTypeID,
      plantFactorySubType: plantFactory.plantFactorySubType,
      plantFactoryDescription: plantFactory.plantFactoryDescription,
      createdBy: plantFactory.createdBy,
      createdOn: plantFactory.createdOn ? plantFactory.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: plantFactory.updatedBy,
      updatedOn: plantFactory.updatedOn ? plantFactory.updatedOn.format(DATE_TIME_FORMAT) : null,
      farmID: plantFactory.farmID,
      deviceID: plantFactory.deviceID,
    });

    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, plantFactory.deviceID);
    this.farmsSharedCollection = this.farmService.addFarmToCollectionIfMissing(this.farmsSharedCollection, plantFactory.farmID);
  }

  protected loadRelationshipsOptions(): void {
    this.deviceService
      .query()
      .pipe(map((res: HttpResponse<IDevice[]>) => res.body ?? []))
      .pipe(map((devices: IDevice[]) => this.deviceService.addDeviceToCollectionIfMissing(devices, this.editForm.get('deviceID')!.value)))
      .subscribe((devices: IDevice[]) => (this.devicesSharedCollection = devices));

    this.farmService
      .query()
      .pipe(map((res: HttpResponse<IFarm[]>) => res.body ?? []))
      .pipe(map((farms: IFarm[]) => this.farmService.addFarmToCollectionIfMissing(farms, this.editForm.get('farmID')!.value)))
      .subscribe((farms: IFarm[]) => (this.farmsSharedCollection = farms));
  }

  protected createFromForm(): IPlantFactory {
    return {
      ...new PlantFactory(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      plantFactoryName: this.editForm.get(['plantFactoryName'])!.value,
      plantFactoryTypeID: this.editForm.get(['plantFactoryTypeID'])!.value,
      plantFactorySubType: this.editForm.get(['plantFactorySubType'])!.value,
      plantFactoryDescription: this.editForm.get(['plantFactoryDescription'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      farmID: this.editForm.get(['farmID'])!.value,
      deviceID: this.editForm.get(['deviceID'])!.value,
    };
  }
}
