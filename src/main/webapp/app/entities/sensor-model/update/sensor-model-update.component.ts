import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISensorModel, SensorModel } from '../sensor-model.model';
import { SensorModelService } from '../service/sensor-model.service';

@Component({
  selector: 'jhi-sensor-model-update',
  templateUrl: './sensor-model-update.component.html',
})
export class SensorModelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    sensorType: [],
    manufacturer: [],
    productCode: [],
    sensorMeasure: [],
    modelName: [],
    properties: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected sensorModelService: SensorModelService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensorModel }) => {
      if (sensorModel.id === undefined) {
        const today = dayjs().startOf('day');
        sensorModel.createdOn = today;
        sensorModel.updatedOn = today;
      }

      this.updateForm(sensorModel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sensorModel = this.createFromForm();
    if (sensorModel.id !== undefined) {
      this.subscribeToSaveResponse(this.sensorModelService.update(sensorModel));
    } else {
      this.subscribeToSaveResponse(this.sensorModelService.create(sensorModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensorModel>>): void {
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

  protected updateForm(sensorModel: ISensorModel): void {
    this.editForm.patchValue({
      id: sensorModel.id,
      gUID: sensorModel.gUID,
      sensorType: sensorModel.sensorType,
      manufacturer: sensorModel.manufacturer,
      productCode: sensorModel.productCode,
      sensorMeasure: sensorModel.sensorMeasure,
      modelName: sensorModel.modelName,
      properties: sensorModel.properties,
      description: sensorModel.description,
      createdBy: sensorModel.createdBy,
      createdOn: sensorModel.createdOn ? sensorModel.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: sensorModel.updatedBy,
      updatedOn: sensorModel.updatedOn ? sensorModel.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISensorModel {
    return {
      ...new SensorModel(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      sensorType: this.editForm.get(['sensorType'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      productCode: this.editForm.get(['productCode'])!.value,
      sensorMeasure: this.editForm.get(['sensorMeasure'])!.value,
      modelName: this.editForm.get(['modelName'])!.value,
      properties: this.editForm.get(['properties'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
