import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPlantFactoryController, PlantFactoryController } from '../plant-factory-controller.model';
import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';
import { PlantSource } from 'app/entities/enumerations/plant-source.model';

@Component({
  selector: 'jhi-plant-factory-controller-update',
  templateUrl: './plant-factory-controller-update.component.html',
})
export class PlantFactoryControllerUpdateComponent implements OnInit {
  isSaving = false;
  plantSourceValues = Object.keys(PlantSource);

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
    nutrientTankLevel: [],
    dewPoint: [],
    heatIndex: [],
    turbidity: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(
    protected plantFactoryControllerService: PlantFactoryControllerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantFactoryController }) => {
      if (plantFactoryController.id === undefined) {
        const today = dayjs().startOf('day');
        plantFactoryController.createdOn = today;
        plantFactoryController.updatedOn = today;
      }

      this.updateForm(plantFactoryController);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plantFactoryController = this.createFromForm();
    if (plantFactoryController.id !== undefined) {
      this.subscribeToSaveResponse(this.plantFactoryControllerService.update(plantFactoryController));
    } else {
      this.subscribeToSaveResponse(this.plantFactoryControllerService.create(plantFactoryController));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantFactoryController>>): void {
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

  protected updateForm(plantFactoryController: IPlantFactoryController): void {
    this.editForm.patchValue({
      id: plantFactoryController.id,
      gUID: plantFactoryController.gUID,
      source: plantFactoryController.source,
      airTemperature: plantFactoryController.airTemperature,
      relativeHumidity: plantFactoryController.relativeHumidity,
      vPD: plantFactoryController.vPD,
      evapotranspiration: plantFactoryController.evapotranspiration,
      barometricPressure: plantFactoryController.barometricPressure,
      seaLevelPressure: plantFactoryController.seaLevelPressure,
      co2Level: plantFactoryController.co2Level,
      nutrientTankLevel: plantFactoryController.nutrientTankLevel,
      dewPoint: plantFactoryController.dewPoint,
      heatIndex: plantFactoryController.heatIndex,
      turbidity: plantFactoryController.turbidity,
      createdBy: plantFactoryController.createdBy,
      createdOn: plantFactoryController.createdOn ? plantFactoryController.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: plantFactoryController.updatedBy,
      updatedOn: plantFactoryController.updatedOn ? plantFactoryController.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPlantFactoryController {
    return {
      ...new PlantFactoryController(),
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
      nutrientTankLevel: this.editForm.get(['nutrientTankLevel'])!.value,
      dewPoint: this.editForm.get(['dewPoint'])!.value,
      heatIndex: this.editForm.get(['heatIndex'])!.value,
      turbidity: this.editForm.get(['turbidity'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
