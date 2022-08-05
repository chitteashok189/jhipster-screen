import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDeviceModel, DeviceModel } from '../device-model.model';
import { DeviceModelService } from '../service/device-model.service';

@Component({
  selector: 'jhi-device-model-update',
  templateUrl: './device-model-update.component.html',
})
export class DeviceModelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    modelName: [],
    type: [],
    manufacturer: [],
    productCode: [],
    properties: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected deviceModelService: DeviceModelService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      if (deviceModel.id === undefined) {
        const today = dayjs().startOf('day');
        deviceModel.createdOn = today;
        deviceModel.updatedOn = today;
      }

      this.updateForm(deviceModel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceModel = this.createFromForm();
    if (deviceModel.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceModelService.update(deviceModel));
    } else {
      this.subscribeToSaveResponse(this.deviceModelService.create(deviceModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceModel>>): void {
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

  protected updateForm(deviceModel: IDeviceModel): void {
    this.editForm.patchValue({
      id: deviceModel.id,
      gUID: deviceModel.gUID,
      modelName: deviceModel.modelName,
      type: deviceModel.type,
      manufacturer: deviceModel.manufacturer,
      productCode: deviceModel.productCode,
      properties: deviceModel.properties,
      description: deviceModel.description,
      createdBy: deviceModel.createdBy,
      createdOn: deviceModel.createdOn ? deviceModel.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: deviceModel.updatedBy,
      updatedOn: deviceModel.updatedOn ? deviceModel.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDeviceModel {
    return {
      ...new DeviceModel(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      modelName: this.editForm.get(['modelName'])!.value,
      type: this.editForm.get(['type'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      productCode: this.editForm.get(['productCode'])!.value,
      properties: this.editForm.get(['properties'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
