import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGrowBed, GrowBed } from '../grow-bed.model';
import { GrowBedService } from '../service/grow-bed.service';
import { GrowBedType } from 'app/entities/enumerations/grow-bed-type.model';

@Component({
  selector: 'jhi-grow-bed-update',
  templateUrl: './grow-bed-update.component.html',
})
export class GrowBedUpdateComponent implements OnInit {
  isSaving = false;
  growBedTypeValues = Object.keys(GrowBedType);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    growBedType: [],
    growBedName: [],
    manufacturer: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected growBedService: GrowBedService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ growBed }) => {
      if (growBed.id === undefined) {
        const today = dayjs().startOf('day');
        growBed.createdOn = today;
        growBed.updatedOn = today;
      }

      this.updateForm(growBed);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const growBed = this.createFromForm();
    if (growBed.id !== undefined) {
      this.subscribeToSaveResponse(this.growBedService.update(growBed));
    } else {
      this.subscribeToSaveResponse(this.growBedService.create(growBed));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrowBed>>): void {
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

  protected updateForm(growBed: IGrowBed): void {
    this.editForm.patchValue({
      id: growBed.id,
      gUID: growBed.gUID,
      growBedType: growBed.growBedType,
      growBedName: growBed.growBedName,
      manufacturer: growBed.manufacturer,
      createdBy: growBed.createdBy,
      createdOn: growBed.createdOn ? growBed.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: growBed.updatedBy,
      updatedOn: growBed.updatedOn ? growBed.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IGrowBed {
    return {
      ...new GrowBed(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      growBedType: this.editForm.get(['growBedType'])!.value,
      growBedName: this.editForm.get(['growBedName'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
