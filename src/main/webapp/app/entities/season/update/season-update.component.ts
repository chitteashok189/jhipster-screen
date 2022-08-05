import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISeason, Season } from '../season.model';
import { SeasonService } from '../service/season.service';
import { SeaType } from 'app/entities/enumerations/sea-type.model';
import { SeaTime } from 'app/entities/enumerations/sea-time.model';

@Component({
  selector: 'jhi-season-update',
  templateUrl: './season-update.component.html',
})
export class SeasonUpdateComponent implements OnInit {
  isSaving = false;
  seaTypeValues = Object.keys(SeaType);
  seaTimeValues = Object.keys(SeaTime);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    seasonType: [],
    cropName: [],
    area: [],
    seasonTime: [],
    seasonstartDate: [],
    seasonEndDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected seasonService: SeasonService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ season }) => {
      if (season.id === undefined) {
        const today = dayjs().startOf('day');
        season.seasonstartDate = today;
        season.seasonEndDate = today;
        season.createdOn = today;
        season.updatedOn = today;
      }

      this.updateForm(season);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const season = this.createFromForm();
    if (season.id !== undefined) {
      this.subscribeToSaveResponse(this.seasonService.update(season));
    } else {
      this.subscribeToSaveResponse(this.seasonService.create(season));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeason>>): void {
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

  protected updateForm(season: ISeason): void {
    this.editForm.patchValue({
      id: season.id,
      gUID: season.gUID,
      seasonType: season.seasonType,
      cropName: season.cropName,
      area: season.area,
      seasonTime: season.seasonTime,
      seasonstartDate: season.seasonstartDate ? season.seasonstartDate.format(DATE_TIME_FORMAT) : null,
      seasonEndDate: season.seasonEndDate ? season.seasonEndDate.format(DATE_TIME_FORMAT) : null,
      createdBy: season.createdBy,
      createdOn: season.createdOn ? season.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: season.updatedBy,
      updatedOn: season.updatedOn ? season.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISeason {
    return {
      ...new Season(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      seasonType: this.editForm.get(['seasonType'])!.value,
      cropName: this.editForm.get(['cropName'])!.value,
      area: this.editForm.get(['area'])!.value,
      seasonTime: this.editForm.get(['seasonTime'])!.value,
      seasonstartDate: this.editForm.get(['seasonstartDate'])!.value
        ? dayjs(this.editForm.get(['seasonstartDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      seasonEndDate: this.editForm.get(['seasonEndDate'])!.value
        ? dayjs(this.editForm.get(['seasonEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
