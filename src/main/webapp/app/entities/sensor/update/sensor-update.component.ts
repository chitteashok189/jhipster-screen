import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISensor, Sensor } from '../sensor.model';
import { SensorService } from '../service/sensor.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { ISensorModel } from 'app/entities/sensor-model/sensor-model.model';
import { SensorModelService } from 'app/entities/sensor-model/service/sensor-model.service';

@Component({
  selector: 'jhi-sensor-update',
  templateUrl: './sensor-update.component.html',
})
export class SensorUpdateComponent implements OnInit {
  isSaving = false;

  locationsSharedCollection: ILocation[] = [];
  devicesSharedCollection: IDevice[] = [];
  sensorModelsSharedCollection: ISensorModel[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    sensorName: [],
    sensorModelName: [],
    hardwareID: [],
    port: [],
    properties: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    locationID: [],
    deviceID: [],
    sensorModelID: [],
  });

  constructor(
    protected sensorService: SensorService,
    protected locationService: LocationService,
    protected deviceService: DeviceService,
    protected sensorModelService: SensorModelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensor }) => {
      if (sensor.id === undefined) {
        const today = dayjs().startOf('day');
        sensor.createdOn = today;
        sensor.updatedOn = today;
      }

      this.updateForm(sensor);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sensor = this.createFromForm();
    if (sensor.id !== undefined) {
      this.subscribeToSaveResponse(this.sensorService.update(sensor));
    } else {
      this.subscribeToSaveResponse(this.sensorService.create(sensor));
    }
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackSensorModelById(_index: number, item: ISensorModel): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensor>>): void {
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

  protected updateForm(sensor: ISensor): void {
    this.editForm.patchValue({
      id: sensor.id,
      gUID: sensor.gUID,
      sensorName: sensor.sensorName,
      sensorModelName: sensor.sensorModelName,
      hardwareID: sensor.hardwareID,
      port: sensor.port,
      properties: sensor.properties,
      description: sensor.description,
      createdBy: sensor.createdBy,
      createdOn: sensor.createdOn ? sensor.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: sensor.updatedBy,
      updatedOn: sensor.updatedOn ? sensor.updatedOn.format(DATE_TIME_FORMAT) : null,
      locationID: sensor.locationID,
      deviceID: sensor.deviceID,
      sensorModelID: sensor.sensorModelID,
    });

    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing(
      this.locationsSharedCollection,
      sensor.locationID
    );
    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, sensor.deviceID);
    this.sensorModelsSharedCollection = this.sensorModelService.addSensorModelToCollectionIfMissing(
      this.sensorModelsSharedCollection,
      sensor.sensorModelID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('locationID')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));

    this.deviceService
      .query()
      .pipe(map((res: HttpResponse<IDevice[]>) => res.body ?? []))
      .pipe(map((devices: IDevice[]) => this.deviceService.addDeviceToCollectionIfMissing(devices, this.editForm.get('deviceID')!.value)))
      .subscribe((devices: IDevice[]) => (this.devicesSharedCollection = devices));

    this.sensorModelService
      .query()
      .pipe(map((res: HttpResponse<ISensorModel[]>) => res.body ?? []))
      .pipe(
        map((sensorModels: ISensorModel[]) =>
          this.sensorModelService.addSensorModelToCollectionIfMissing(sensorModels, this.editForm.get('sensorModelID')!.value)
        )
      )
      .subscribe((sensorModels: ISensorModel[]) => (this.sensorModelsSharedCollection = sensorModels));
  }

  protected createFromForm(): ISensor {
    return {
      ...new Sensor(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      sensorName: this.editForm.get(['sensorName'])!.value,
      sensorModelName: this.editForm.get(['sensorModelName'])!.value,
      hardwareID: this.editForm.get(['hardwareID'])!.value,
      port: this.editForm.get(['port'])!.value,
      properties: this.editForm.get(['properties'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      locationID: this.editForm.get(['locationID'])!.value,
      deviceID: this.editForm.get(['deviceID'])!.value,
      sensorModelID: this.editForm.get(['sensorModelID'])!.value,
    };
  }
}
