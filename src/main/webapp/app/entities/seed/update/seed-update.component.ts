import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISeed, Seed } from '../seed.model';
import { SeedService } from '../service/seed.service';
import { SeedClass } from 'app/entities/enumerations/seed-class.model';
import { SeedRate } from 'app/entities/enumerations/seed-rate.model';
import { Treatment } from 'app/entities/enumerations/treatment.model';

@Component({
  selector: 'jhi-seed-update',
  templateUrl: './seed-update.component.html',
})
export class SeedUpdateComponent implements OnInit {
  isSaving = false;
  seedClassValues = Object.keys(SeedClass);
  seedRateValues = Object.keys(SeedRate);
  treatmentValues = Object.keys(Treatment);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    breederID: [],
    seedClass: [],
    variety: [],
    seedRate: [],
    germinationPercentage: [],
    treatment: [],
    origin: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected seedService: SeedService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seed }) => {
      if (seed.id === undefined) {
        const today = dayjs().startOf('day');
        seed.createdOn = today;
        seed.updatedOn = today;
      }

      this.updateForm(seed);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seed = this.createFromForm();
    if (seed.id !== undefined) {
      this.subscribeToSaveResponse(this.seedService.update(seed));
    } else {
      this.subscribeToSaveResponse(this.seedService.create(seed));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeed>>): void {
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

  protected updateForm(seed: ISeed): void {
    this.editForm.patchValue({
      id: seed.id,
      gUID: seed.gUID,
      breederID: seed.breederID,
      seedClass: seed.seedClass,
      variety: seed.variety,
      seedRate: seed.seedRate,
      germinationPercentage: seed.germinationPercentage,
      treatment: seed.treatment,
      origin: seed.origin,
      createdBy: seed.createdBy,
      createdOn: seed.createdOn ? seed.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: seed.updatedBy,
      updatedOn: seed.updatedOn ? seed.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISeed {
    return {
      ...new Seed(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      breederID: this.editForm.get(['breederID'])!.value,
      seedClass: this.editForm.get(['seedClass'])!.value,
      variety: this.editForm.get(['variety'])!.value,
      seedRate: this.editForm.get(['seedRate'])!.value,
      germinationPercentage: this.editForm.get(['germinationPercentage'])!.value,
      treatment: this.editForm.get(['treatment'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
