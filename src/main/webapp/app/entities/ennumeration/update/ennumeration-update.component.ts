import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEnnumeration, Ennumeration } from '../ennumeration.model';
import { EnnumerationService } from '../service/ennumeration.service';

@Component({
  selector: 'jhi-ennumeration-update',
  templateUrl: './ennumeration-update.component.html',
})
export class EnnumerationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    enumCode: [],
    description: [],
  });

  constructor(protected ennumerationService: EnnumerationService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ennumeration }) => {
      this.updateForm(ennumeration);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ennumeration = this.createFromForm();
    if (ennumeration.id !== undefined) {
      this.subscribeToSaveResponse(this.ennumerationService.update(ennumeration));
    } else {
      this.subscribeToSaveResponse(this.ennumerationService.create(ennumeration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnnumeration>>): void {
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

  protected updateForm(ennumeration: IEnnumeration): void {
    this.editForm.patchValue({
      id: ennumeration.id,
      enumCode: ennumeration.enumCode,
      description: ennumeration.description,
    });
  }

  protected createFromForm(): IEnnumeration {
    return {
      ...new Ennumeration(),
      id: this.editForm.get(['id'])!.value,
      enumCode: this.editForm.get(['enumCode'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
