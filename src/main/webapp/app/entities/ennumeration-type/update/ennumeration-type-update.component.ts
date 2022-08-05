import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEnnumerationType, EnnumerationType } from '../ennumeration-type.model';
import { EnnumerationTypeService } from '../service/ennumeration-type.service';

@Component({
  selector: 'jhi-ennumeration-type-update',
  templateUrl: './ennumeration-type-update.component.html',
})
export class EnnumerationTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ennumerationType: [],
    hasTable: [],
    description: [],
    ennumeration: [],
  });

  constructor(
    protected ennumerationTypeService: EnnumerationTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ennumerationType }) => {
      this.updateForm(ennumerationType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ennumerationType = this.createFromForm();
    if (ennumerationType.id !== undefined) {
      this.subscribeToSaveResponse(this.ennumerationTypeService.update(ennumerationType));
    } else {
      this.subscribeToSaveResponse(this.ennumerationTypeService.create(ennumerationType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnnumerationType>>): void {
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

  protected updateForm(ennumerationType: IEnnumerationType): void {
    this.editForm.patchValue({
      id: ennumerationType.id,
      ennumerationType: ennumerationType.ennumerationType,
      hasTable: ennumerationType.hasTable,
      description: ennumerationType.description,
      ennumeration: ennumerationType.ennumeration,
    });
  }

  protected createFromForm(): IEnnumerationType {
    return {
      ...new EnnumerationType(),
      id: this.editForm.get(['id'])!.value,
      ennumerationType: this.editForm.get(['ennumerationType'])!.value,
      hasTable: this.editForm.get(['hasTable'])!.value,
      description: this.editForm.get(['description'])!.value,
      ennumeration: this.editForm.get(['ennumeration'])!.value,
    };
  }
}
