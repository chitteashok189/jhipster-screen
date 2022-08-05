import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDosing, Dosing } from '../dosing.model';
import { DosingService } from '../service/dosing.service';
import { IDevice } from 'app/entities/device/device.model';
import { DeviceService } from 'app/entities/device/service/device.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { DosingSource } from 'app/entities/enumerations/dosing-source.model';

@Component({
  selector: 'jhi-dosing-update',
  templateUrl: './dosing-update.component.html',
})
export class DosingUpdateComponent implements OnInit {
  isSaving = false;
  dosingSourceValues = Object.keys(DosingSource);

  devicesSharedCollection: IDevice[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    source: [],
    pH: [],
    eC: [],
    dO: [],
    nutrientTemperature: [],
    solarRadiation: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    deviceID: [],
    plantFactoryID: [],
  });

  constructor(
    protected dosingService: DosingService,
    protected deviceService: DeviceService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dosing }) => {
      if (dosing.id === undefined) {
        const today = dayjs().startOf('day');
        dosing.createdOn = today;
        dosing.updatedOn = today;
      }

      this.updateForm(dosing);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dosing = this.createFromForm();
    if (dosing.id !== undefined) {
      this.subscribeToSaveResponse(this.dosingService.update(dosing));
    } else {
      this.subscribeToSaveResponse(this.dosingService.create(dosing));
    }
  }

  trackDeviceById(_index: number, item: IDevice): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDosing>>): void {
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

  protected updateForm(dosing: IDosing): void {
    this.editForm.patchValue({
      id: dosing.id,
      gUID: dosing.gUID,
      source: dosing.source,
      pH: dosing.pH,
      eC: dosing.eC,
      dO: dosing.dO,
      nutrientTemperature: dosing.nutrientTemperature,
      solarRadiation: dosing.solarRadiation,
      createdBy: dosing.createdBy,
      createdOn: dosing.createdOn ? dosing.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: dosing.updatedBy,
      updatedOn: dosing.updatedOn ? dosing.updatedOn.format(DATE_TIME_FORMAT) : null,
      deviceID: dosing.deviceID,
      plantFactoryID: dosing.plantFactoryID,
    });

    this.devicesSharedCollection = this.deviceService.addDeviceToCollectionIfMissing(this.devicesSharedCollection, dosing.deviceID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      dosing.plantFactoryID
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

  protected createFromForm(): IDosing {
    return {
      ...new Dosing(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      source: this.editForm.get(['source'])!.value,
      pH: this.editForm.get(['pH'])!.value,
      eC: this.editForm.get(['eC'])!.value,
      dO: this.editForm.get(['dO'])!.value,
      nutrientTemperature: this.editForm.get(['nutrientTemperature'])!.value,
      solarRadiation: this.editForm.get(['solarRadiation'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      deviceID: this.editForm.get(['deviceID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
