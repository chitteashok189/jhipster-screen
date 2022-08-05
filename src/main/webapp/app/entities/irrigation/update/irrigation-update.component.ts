import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IIrrigation, Irrigation } from '../irrigation.model';
import { IrrigationService } from '../service/irrigation.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IrriSource } from 'app/entities/enumerations/irri-source.model';

@Component({
  selector: 'jhi-irrigation-update',
  templateUrl: './irrigation-update.component.html',
})
export class IrrigationUpdateComponent implements OnInit {
  isSaving = false;
  irriSourceValues = Object.keys(IrriSource);

  devicesSharedCollection: IDevice[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    source: [],
    nutrientLevel: [],
    solarRadiation: [],
    inletFlow: [],
    outletFlow: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    deviceID: [],
    plantFactoryID: [],
  });

  constructor(
    protected irrigationService: IrrigationService,
    protected deviceService: DeviceService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ irrigation }) => {
      if (irrigation.id === undefined) {
        const today = dayjs().startOf('day');
        irrigation.createdOn = today;
        irrigation.updatedOn = today;
      }

      this.updateForm(irrigation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const irrigation = this.createFromForm();
    if (irrigation.id !== undefined) {
      this.subscribeToSaveResponse(this.irrigationService.update(irrigation));
    } else {
      this.subscribeToSaveResponse(this.irrigationService.create(irrigation));
    }
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIrrigation>>): void {
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

  protected updateForm(irrigation: IIrrigation): void {
    this.editForm.patchValue({
      id: irrigation.id,
      gUID: irrigation.gUID,
      source: irrigation.source,
      nutrientLevel: irrigation.nutrientLevel,
      solarRadiation: irrigation.solarRadiation,
      inletFlow: irrigation.inletFlow,
      outletFlow: irrigation.outletFlow,
      createdBy: irrigation.createdBy,
      createdOn: irrigation.createdOn ? irrigation.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: irrigation.updatedBy,
      updatedOn: irrigation.updatedOn ? irrigation.updatedOn.format(DATE_TIME_FORMAT) : null,
      deviceID: irrigation.deviceID,
      plantFactoryID: irrigation.plantFactoryID,
    });

    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, irrigation.deviceID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      irrigation.plantFactoryID
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

  protected createFromForm(): IIrrigation {
    return {
      ...new Irrigation(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      source: this.editForm.get(['source'])!.value,
      nutrientLevel: this.editForm.get(['nutrientLevel'])!.value,
      solarRadiation: this.editForm.get(['solarRadiation'])!.value,
      inletFlow: this.editForm.get(['inletFlow'])!.value,
      outletFlow: this.editForm.get(['outletFlow'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      deviceID: this.editForm.get(['deviceID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
