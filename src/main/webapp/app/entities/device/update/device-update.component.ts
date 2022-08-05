import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { ISensor } from '../../sensor/sensor.model';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDevice, Device } from '../device.model';
import { DeviceService } from '../service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IDeviceLevel } from 'app/entities/device-level/device-level.model';
import { DeviceLevelService } from 'app/entities/device-level/service/device-level.service';
import { IDeviceModel } from 'app/entities/device-model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/service/device-model.service';
import { DeviceType } from 'app/entities/enumerations/device-type.model';
import { SensorService } from '../../sensor/service/sensor.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html',
})
export class DeviceUpdateComponent implements OnInit {
  isSaving = false;
  deviceTypeValues = Object.keys(DeviceType);

  sensors?: ISensor[];
  isLoading = false;


  plantFactoriesSharedCollection: IPlantFactory[] = [];
  deviceLevelsSharedCollection: IDeviceLevel[] = [];
  deviceModelsSharedCollection: IDeviceModel[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    deviceModel: [],
    deviceAsset: [],
    deviceType: [],
    hardwareID: [],
    reportingIntervalLocation: [],
    parentDevice: [],
    properties: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    plantFactoryID: [],
    deviceLevelID: [],
    deviceModelID: [],
  });

  constructor(
    protected deviceService: DeviceService,
    protected plantFactoryService: PlantFactoryService,
    protected deviceLevelService: DeviceLevelService,
    protected deviceModelService: DeviceModelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected sensorService: SensorService, 
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.isLoading = true;

    this.sensorService.query().subscribe({
      next: (res: HttpResponse<ISensor[]>) => {
        this.isLoading = false;
        this.sensors = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ device }) => {
      if (device.id === undefined) {
        const today = dayjs().startOf('day');
        device.createdOn = today;
        device.updatedOn = today;
      }

      this.updateForm(device);

      this.loadRelationshipsOptions();
      this.loadAll();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const device = this.createFromForm();
    if (device.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceService.update(device));
    } else {
      this.subscribeToSaveResponse(this.deviceService.create(device));
    }
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  trackDeviceLevelById(_index: number, item: IDeviceLevel): number {
    return item.id!;
  }

  trackDeviceModelById(_index: number, item: IDeviceModel): number {
    return item.id!;
  }
  trackId(_index: number, item: ISensor): number {
    return item.id!;
  }


  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevice>>): void {
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

  protected updateForm(device: IDevice): void {
    this.editForm.patchValue({
      id: device.id,
      gUID: device.gUID,
      deviceModel: device.deviceModel,
      deviceAsset: device.deviceAsset,
      deviceType: device.deviceType,
      hardwareID: device.hardwareID,
      reportingIntervalLocation: device.reportingIntervalLocation,
      parentDevice: device.parentDevice,
      properties: device.properties,
      description: device.description,
      createdBy: device.createdBy,
      createdOn: device.createdOn ? device.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: device.updatedBy,
      updatedOn: device.updatedOn ? device.updatedOn.format(DATE_TIME_FORMAT) : null,
      plantFactoryID: device.plantFactoryID,
      deviceLevelID: device.deviceLevelID,
      deviceModelID: device.deviceModelID,
    });

    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      device.plantFactoryID
    );
    this.deviceLevelsSharedCollection = this.deviceLevelService.addDeviceLevelToCollectionIfMissing(
      this.deviceLevelsSharedCollection,
      device.deviceLevelID
    );
    this.deviceModelsSharedCollection = this.deviceModelService.addDeviceModelToCollectionIfMissing(
      this.deviceModelsSharedCollection,
      device.deviceModelID
    );
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

    this.deviceLevelService
      .query()
      .pipe(map((res: HttpResponse<IDeviceLevel[]>) => res.body ?? []))
      .pipe(
        map((deviceLevels: IDeviceLevel[]) =>
          this.deviceLevelService.addDeviceLevelToCollectionIfMissing(deviceLevels, this.editForm.get('deviceLevelID')!.value)
        )
      )
      .subscribe((deviceLevels: IDeviceLevel[]) => (this.deviceLevelsSharedCollection = deviceLevels));

    this.deviceModelService
      .query()
      .pipe(map((res: HttpResponse<IDeviceModel[]>) => res.body ?? []))
      .pipe(
        map((deviceModels: IDeviceModel[]) =>
          this.deviceModelService.addDeviceModelToCollectionIfMissing(deviceModels, this.editForm.get('deviceModelID')!.value)
        )
      )
      .subscribe((deviceModels: IDeviceModel[]) => (this.deviceModelsSharedCollection = deviceModels));
  }

  protected createFromForm(): IDevice {
    return {
      ...new Device(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      deviceModel: this.editForm.get(['deviceModel'])!.value,
      deviceAsset: this.editForm.get(['deviceAsset'])!.value,
      deviceType: this.editForm.get(['deviceType'])!.value,
      hardwareID: this.editForm.get(['hardwareID'])!.value,
      reportingIntervalLocation: this.editForm.get(['reportingIntervalLocation'])!.value,
      parentDevice: this.editForm.get(['parentDevice'])!.value,
      properties: this.editForm.get(['properties'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
      deviceLevelID: this.editForm.get(['deviceLevelID'])!.value,
      deviceModelID: this.editForm.get(['deviceModelID'])!.value,
    };
  }
}
