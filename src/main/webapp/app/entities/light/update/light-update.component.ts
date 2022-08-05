import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILight, Light } from '../light.model';
import { LightService } from '../service/light.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { LightSource } from 'app/entities/enumerations/light-source.model';

@Component({
  selector: 'jhi-light-update',
  templateUrl: './light-update.component.html',
})
export class LightUpdateComponent implements OnInit {
  isSaving = false;
  lightSourceValues = Object.keys(LightSource);

  devicesSharedCollection: IDevice[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    source: [],
    lightIntensity: [],
    dailyLightIntegral: [],
    pAR: [],
    pPFD: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    deviceID: [],
    plantFactoryID: [],
  });

  constructor(
    protected lightService: LightService,
    protected deviceService: DeviceService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ light }) => {
      if (light.id === undefined) {
        const today = dayjs().startOf('day');
        light.createdOn = today;
        light.updatedOn = today;
      }

      this.updateForm(light);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const light = this.createFromForm();
    if (light.id !== undefined) {
      this.subscribeToSaveResponse(this.lightService.update(light));
    } else {
      this.subscribeToSaveResponse(this.lightService.create(light));
    }
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILight>>): void {
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

  protected updateForm(light: ILight): void {
    this.editForm.patchValue({
      id: light.id,
      gUID: light.gUID,
      source: light.source,
      lightIntensity: light.lightIntensity,
      dailyLightIntegral: light.dailyLightIntegral,
      pAR: light.pAR,
      pPFD: light.pPFD,
      createdBy: light.createdBy,
      createdOn: light.createdOn ? light.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: light.updatedBy,
      updatedOn: light.updatedOn ? light.updatedOn.format(DATE_TIME_FORMAT) : null,
      deviceID: light.deviceID,
      plantFactoryID: light.plantFactoryID,
    });

    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, light.deviceID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      light.plantFactoryID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.deviceService
      .query()
      .pipe(map((res: HttpResponse<IDevice[]>) => res.body ?? []))
      .pipe(map((devices: IDevice[]) => this.deviceService.addDeviceToCollectionIfMissing(devices, this.editForm.get('deviceID')!.value)))
      .subscribe((devices: IDevice[]) => (this.devicesSharedCollection = devices));

    this.plantFactoryService
      .query()
      .pipe(map((res: HttpResponse<IPlantFactory[]>) => res.body ?? []))
      .pipe(
        map((plantFactories: IPlantFactory[]) =>
          this.plantFactoryService.addPlantFactoryToCollectionIfMissing(plantFactories, this.editForm.get('plantFactoryID')!.value)
        )
      )
      .subscribe((plantFactories: IPlantFactory[]) => (this.plantFactoriesSharedCollection = plantFactories));
  }

  protected createFromForm(): ILight {
    return {
      ...new Light(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      source: this.editForm.get(['source'])!.value,
      lightIntensity: this.editForm.get(['lightIntensity'])!.value,
      dailyLightIntegral: this.editForm.get(['dailyLightIntegral'])!.value,
      pAR: this.editForm.get(['pAR'])!.value,
      pPFD: this.editForm.get(['pPFD'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      deviceID: this.editForm.get(['deviceID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
