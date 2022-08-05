import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRawMaterial, RawMaterial } from '../raw-material.model';
import { RawMaterialService } from '../service/raw-material.service';
import { MaterialType } from 'app/entities/enumerations/material-type.model';

@Component({
  selector: 'jhi-raw-material-update',
  templateUrl: './raw-material-update.component.html',
})
export class RawMaterialUpdateComponent implements OnInit {
  isSaving = false;
  materialTypeValues = Object.keys(MaterialType);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    quantity: [],
    uOM: [],
    materialType: [],
    price: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected rawMaterialService: RawMaterialService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterial }) => {
      if (rawMaterial.id === undefined) {
        const today = dayjs().startOf('day');
        rawMaterial.createdOn = today;
        rawMaterial.updatedOn = today;
      }

      this.updateForm(rawMaterial);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rawMaterial = this.createFromForm();
    if (rawMaterial.id !== undefined) {
      this.subscribeToSaveResponse(this.rawMaterialService.update(rawMaterial));
    } else {
      this.subscribeToSaveResponse(this.rawMaterialService.create(rawMaterial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRawMaterial>>): void {
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

  protected updateForm(rawMaterial: IRawMaterial): void {
    this.editForm.patchValue({
      id: rawMaterial.id,
      gUID: rawMaterial.gUID,
      quantity: rawMaterial.quantity,
      uOM: rawMaterial.uOM,
      materialType: rawMaterial.materialType,
      price: rawMaterial.price,
      description: rawMaterial.description,
      createdBy: rawMaterial.createdBy,
      createdOn: rawMaterial.createdOn ? rawMaterial.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: rawMaterial.updatedBy,
      updatedOn: rawMaterial.updatedOn ? rawMaterial.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IRawMaterial {
    return {
      ...new RawMaterial(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      uOM: this.editForm.get(['uOM'])!.value,
      materialType: this.editForm.get(['materialType'])!.value,
      price: this.editForm.get(['price'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
