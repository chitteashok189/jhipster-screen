import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INutrient, Nutrient } from '../nutrient.model';
import { NutrientService } from '../service/nutrient.service';
import { NutrientType } from 'app/entities/enumerations/nutrient-type.model';
import { NutForms } from 'app/entities/enumerations/nut-forms.model';
import { NutTypeID } from 'app/entities/enumerations/nut-type-id.model';

@Component({
  selector: 'jhi-nutrient-update',
  templateUrl: './nutrient-update.component.html',
})
export class NutrientUpdateComponent implements OnInit {
  isSaving = false;
  nutrientTypeValues = Object.keys(NutrientType);
  nutFormsValues = Object.keys(NutForms);
  nutTypeIDValues = Object.keys(NutTypeID);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    nutrientName: [],
    type: [],
    brandName: [],
    nutrientLabel: [],
    nutrientForms: [],
    nutrientTypeID: [],
    price: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected nutrientService: NutrientService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nutrient }) => {
      if (nutrient.id === undefined) {
        const today = dayjs().startOf('day');
        nutrient.createdOn = today;
        nutrient.updatedOn = today;
      }

      this.updateForm(nutrient);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nutrient = this.createFromForm();
    if (nutrient.id !== undefined) {
      this.subscribeToSaveResponse(this.nutrientService.update(nutrient));
    } else {
      this.subscribeToSaveResponse(this.nutrientService.create(nutrient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INutrient>>): void {
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

  protected updateForm(nutrient: INutrient): void {
    this.editForm.patchValue({
      id: nutrient.id,
      gUID: nutrient.gUID,
      nutrientName: nutrient.nutrientName,
      type: nutrient.type,
      brandName: nutrient.brandName,
      nutrientLabel: nutrient.nutrientLabel,
      nutrientForms: nutrient.nutrientForms,
      nutrientTypeID: nutrient.nutrientTypeID,
      price: nutrient.price,
      createdBy: nutrient.createdBy,
      createdOn: nutrient.createdOn ? nutrient.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: nutrient.updatedBy,
      updatedOn: nutrient.updatedOn ? nutrient.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): INutrient {
    return {
      ...new Nutrient(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      nutrientName: this.editForm.get(['nutrientName'])!.value,
      type: this.editForm.get(['type'])!.value,
      brandName: this.editForm.get(['brandName'])!.value,
      nutrientLabel: this.editForm.get(['nutrientLabel'])!.value,
      nutrientForms: this.editForm.get(['nutrientForms'])!.value,
      nutrientTypeID: this.editForm.get(['nutrientTypeID'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
