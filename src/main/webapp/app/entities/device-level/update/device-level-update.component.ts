import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDeviceLevel, DeviceLevel } from '../device-level.model';
import { DeviceLevelService } from '../service/device-level.service';

@Component({
  selector: 'jhi-device-level-update',
  templateUrl: './device-level-update.component.html',
})
export class DeviceLevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    levelName: [],
    deviceLevelType: [],
    manufacturer: [],
    productCode: [],
    ports: [],
    properties: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected deviceLevelService: DeviceLevelService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceLevel }) => {
      if (deviceLevel.id === undefined) {
        const today = dayjs().startOf('day');
        deviceLevel.createdOn = today;
        deviceLevel.updatedOn = today;
      }

      this.updateForm(deviceLevel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceLevel = this.createFromForm();
    if (deviceLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceLevelService.update(deviceLevel));
    } else {
      this.subscribeToSaveResponse(this.deviceLevelService.create(deviceLevel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceLevel>>): void {
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

  protected updateForm(deviceLevel: IDeviceLevel): void {
    this.editForm.patchValue({
      id: deviceLevel.id,
      gUID: deviceLevel.gUID,
      levelName: deviceLevel.levelName,
      deviceLevelType: deviceLevel.deviceLevelType,
      manufacturer: deviceLevel.manufacturer,
      productCode: deviceLevel.productCode,
      ports: deviceLevel.ports,
      properties: deviceLevel.properties,
      description: deviceLevel.description,
      createdBy: deviceLevel.createdBy,
      createdOn: deviceLevel.createdOn ? deviceLevel.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: deviceLevel.updatedBy,
      updatedOn: deviceLevel.updatedOn ? deviceLevel.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDeviceLevel {
    return {
      ...new DeviceLevel(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      levelName: this.editForm.get(['levelName'])!.value,
      deviceLevelType: this.editForm.get(['deviceLevelType'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      productCode: this.editForm.get(['productCode'])!.value,
      ports: this.editForm.get(['ports'])!.value,
      properties: this.editForm.get(['properties'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
