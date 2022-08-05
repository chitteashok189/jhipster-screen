import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBreeder, Breeder } from '../breeder.model';
import { BreederService } from '../service/breeder.service';

@Component({
  selector: 'jhi-breeder-update',
  templateUrl: './breeder-update.component.html',
})
export class BreederUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    breederName: [],
    breederType: [],
    breederStatus: [],
    breederDescription: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected breederService: BreederService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ breeder }) => {
      if (breeder.id === undefined) {
        const today = dayjs().startOf('day');
        breeder.createdOn = today;
        breeder.updatedOn = today;
      }

      this.updateForm(breeder);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const breeder = this.createFromForm();
    if (breeder.id !== undefined) {
      this.subscribeToSaveResponse(this.breederService.update(breeder));
    } else {
      this.subscribeToSaveResponse(this.breederService.create(breeder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBreeder>>): void {
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

  protected updateForm(breeder: IBreeder): void {
    this.editForm.patchValue({
      id: breeder.id,
      gUID: breeder.gUID,
      breederName: breeder.breederName,
      breederType: breeder.breederType,
      breederStatus: breeder.breederStatus,
      breederDescription: breeder.breederDescription,
      createdBy: breeder.createdBy,
      createdOn: breeder.createdOn ? breeder.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: breeder.updatedBy,
      updatedOn: breeder.updatedOn ? breeder.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IBreeder {
    return {
      ...new Breeder(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      breederName: this.editForm.get(['breederName'])!.value,
      breederType: this.editForm.get(['breederType'])!.value,
      breederStatus: this.editForm.get(['breederStatus'])!.value,
      breederDescription: this.editForm.get(['breederDescription'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
