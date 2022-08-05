import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAlert, Alert } from '../alert.model';
import { AlertService } from '../service/alert.service';
import { AlertType } from 'app/entities/enumerations/alert-type.model';
import { PreType } from 'app/entities/enumerations/pre-type.model';
import { AlertStatus } from 'app/entities/enumerations/alert-status.model';
import { Remediation } from 'app/entities/enumerations/remediation.model';

@Component({
  selector: 'jhi-alert-update',
  templateUrl: './alert-update.component.html',
})
export class AlertUpdateComponent implements OnInit {
  isSaving = false;
  alertTypeValues = Object.keys(AlertType);
  preTypeValues = Object.keys(PreType);
  alertStatusValues = Object.keys(AlertStatus);
  remediationValues = Object.keys(Remediation);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    name: [],
    alertType: [],
    description: [],
    datePeriod: [],
    durationDays: [],
    minimumTemperature: [],
    maximumTemperature: [],
    minHumidity: [],
    maxHumidity: [],
    precipitationType: [],
    minPrecipitation: [],
    maxPrecipitation: [],
    status: [],
    remediation: [],
    farmAssigned: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected alertService: AlertService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alert }) => {
      if (alert.id === undefined) {
        const today = dayjs().startOf('day');
        alert.createdOn = today;
        alert.updatedOn = today;
      }

      this.updateForm(alert);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alert = this.createFromForm();
    if (alert.id !== undefined) {
      this.subscribeToSaveResponse(this.alertService.update(alert));
    } else {
      this.subscribeToSaveResponse(this.alertService.create(alert));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlert>>): void {
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

  protected updateForm(alert: IAlert): void {
    this.editForm.patchValue({
      id: alert.id,
      gUID: alert.gUID,
      name: alert.name,
      alertType: alert.alertType,
      description: alert.description,
      datePeriod: alert.datePeriod,
      durationDays: alert.durationDays,
      minimumTemperature: alert.minimumTemperature,
      maximumTemperature: alert.maximumTemperature,
      minHumidity: alert.minHumidity,
      maxHumidity: alert.maxHumidity,
      precipitationType: alert.precipitationType,
      minPrecipitation: alert.minPrecipitation,
      maxPrecipitation: alert.maxPrecipitation,
      status: alert.status,
      remediation: alert.remediation,
      farmAssigned: alert.farmAssigned,
      createdBy: alert.createdBy,
      createdOn: alert.createdOn ? alert.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: alert.updatedBy,
      updatedOn: alert.updatedOn ? alert.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IAlert {
    return {
      ...new Alert(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      name: this.editForm.get(['name'])!.value,
      alertType: this.editForm.get(['alertType'])!.value,
      description: this.editForm.get(['description'])!.value,
      datePeriod: this.editForm.get(['datePeriod'])!.value,
      durationDays: this.editForm.get(['durationDays'])!.value,
      minimumTemperature: this.editForm.get(['minimumTemperature'])!.value,
      maximumTemperature: this.editForm.get(['maximumTemperature'])!.value,
      minHumidity: this.editForm.get(['minHumidity'])!.value,
      maxHumidity: this.editForm.get(['maxHumidity'])!.value,
      precipitationType: this.editForm.get(['precipitationType'])!.value,
      minPrecipitation: this.editForm.get(['minPrecipitation'])!.value,
      maxPrecipitation: this.editForm.get(['maxPrecipitation'])!.value,
      status: this.editForm.get(['status'])!.value,
      remediation: this.editForm.get(['remediation'])!.value,
      farmAssigned: this.editForm.get(['farmAssigned'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
