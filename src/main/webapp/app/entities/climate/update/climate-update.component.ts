import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IClimate, Climate } from '../climate.model';
import { ClimateService } from '../service/climate.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { CliSource } from 'app/entities/enumerations/cli-source.model';

@Component({
  selector: 'jhi-climate-update',
  templateUrl: './climate-update.component.html',
})
export class ClimateUpdateComponent implements OnInit {
  isSaving = false;
  cliSourceValues = Object.keys(CliSource);

  devicesSharedCollection: IDevice[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    source: [],
    airTemperature: [],
    relativeHumidity: [],
    vPD: [],
    evapotranspiration: [],
    barometricPressure: [],
    seaLevelPressure: [],
    co2Level: [],
    dewPoint: [],
    solarRadiation: [],
    heatIndex: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    deviceID: [],
    plantFactoryID: [],
  });

  constructor(
    protected climateService: ClimateService,
    protected deviceService: DeviceService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ climate }) => {
      if (climate.id === undefined) {
        const today = dayjs().startOf('day');
        climate.createdOn = today;
        climate.updatedOn = today;
      }

      this.updateForm(climate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const climate = this.createFromForm();
    if (climate.id !== undefined) {
      this.subscribeToSaveResponse(this.climateService.update(climate));
    } else {
      this.subscribeToSaveResponse(this.climateService.create(climate));
    }
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClimate>>): void {
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

  protected updateForm(climate: IClimate): void {
    this.editForm.patchValue({
      id: climate.id,
      gUID: climate.gUID,
      source: climate.source,
      airTemperature: climate.airTemperature,
      relativeHumidity: climate.relativeHumidity,
      vPD: climate.vPD,
      evapotranspiration: climate.evapotranspiration,
      barometricPressure: climate.barometricPressure,
      seaLevelPressure: climate.seaLevelPressure,
      co2Level: climate.co2Level,
      dewPoint: climate.dewPoint,
      solarRadiation: climate.solarRadiation,
      heatIndex: climate.heatIndex,
      createdBy: climate.createdBy,
      createdOn: climate.createdOn ? climate.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: climate.updatedBy,
      updatedOn: climate.updatedOn ? climate.updatedOn.format(DATE_TIME_FORMAT) : null,
      deviceID: climate.deviceID,
      plantFactoryID: climate.plantFactoryID,
    });

    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, climate.deviceID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      climate.plantFactoryID
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

  protected createFromForm(): IClimate {
    return {
      ...new Climate(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      source: this.editForm.get(['source'])!.value,
      airTemperature: this.editForm.get(['airTemperature'])!.value,
      relativeHumidity: this.editForm.get(['relativeHumidity'])!.value,
      vPD: this.editForm.get(['vPD'])!.value,
      evapotranspiration: this.editForm.get(['evapotranspiration'])!.value,
      barometricPressure: this.editForm.get(['barometricPressure'])!.value,
      seaLevelPressure: this.editForm.get(['seaLevelPressure'])!.value,
      co2Level: this.editForm.get(['co2Level'])!.value,
      dewPoint: this.editForm.get(['dewPoint'])!.value,
      solarRadiation: this.editForm.get(['solarRadiation'])!.value,
      heatIndex: this.editForm.get(['heatIndex'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      deviceID: this.editForm.get(['deviceID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
