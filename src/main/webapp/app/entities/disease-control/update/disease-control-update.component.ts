import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDiseaseControl, DiseaseControl } from '../disease-control.model';
import { DiseaseControlService } from '../service/disease-control.service';
import { IDisease } from 'app/entities/disease/disease.model';
import { DiseaseService } from 'app/entities/disease/service/disease.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { SymptomService } from 'app/entities/symptom/service/symptom.service';
import { DisConType } from 'app/entities/enumerations/dis-con-type.model';

@Component({
  selector: 'jhi-disease-control-update',
  templateUrl: './disease-control-update.component.html',
})
export class DiseaseControlUpdateComponent implements OnInit {
  isSaving = false;
  disConTypeValues = Object.keys(DisConType);

  diseasesSharedCollection: IDisease[] = [];
  cropsSharedCollection: ICrop[] = [];
  symptomsSharedCollection: ISymptom[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    controlType: [],
    treatment: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    diseaseID: [],
    cropID: [],
    symptomID: [],
  });

  constructor(
    protected diseaseControlService: DiseaseControlService,
    protected diseaseService: DiseaseService,
    protected cropService: CropService,
    protected symptomService: SymptomService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diseaseControl }) => {
      if (diseaseControl.id === undefined) {
        const today = dayjs().startOf('day');
        diseaseControl.createdOn = today;
        diseaseControl.updatedOn = today;
      }

      this.updateForm(diseaseControl);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diseaseControl = this.createFromForm();
    if (diseaseControl.id !== undefined) {
      this.subscribeToSaveResponse(this.diseaseControlService.update(diseaseControl));
    } else {
      this.subscribeToSaveResponse(this.diseaseControlService.create(diseaseControl));
    }
  }

  trackDiseaseById(_index: number, item: IDisease): number {
    return item.id!;
  }

  trackCropById(_index: number, item: ICrop): number {
    return item.id!;
  }

  trackSymptomById(_index: number, item: ISymptom): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiseaseControl>>): void {
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

  protected updateForm(diseaseControl: IDiseaseControl): void {
    this.editForm.patchValue({
      id: diseaseControl.id,
      gUID: diseaseControl.gUID,
      controlType: diseaseControl.controlType,
      treatment: diseaseControl.treatment,
      createdBy: diseaseControl.createdBy,
      createdOn: diseaseControl.createdOn ? diseaseControl.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: diseaseControl.updatedBy,
      updatedOn: diseaseControl.updatedOn ? diseaseControl.updatedOn.format(DATE_TIME_FORMAT) : null,
      diseaseID: diseaseControl.diseaseID,
      cropID: diseaseControl.cropID,
      symptomID: diseaseControl.symptomID,
    });

    this.diseasesSharedCollection = this.diseaseService.addDiseaseToCollectionIfMissing(
      this.diseasesSharedCollection,
      diseaseControl.diseaseID
    );
    this.cropsSharedCollection = this.cropService.addCropToCollectionIfMissing(this.cropsSharedCollection, diseaseControl.cropID);
    this.symptomsSharedCollection = this.symptomService.addSymptomToCollectionIfMissing(
      this.symptomsSharedCollection,
      diseaseControl.symptomID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.diseaseService
      .query()
      .pipe(map((res: HttpResponse<IDisease[]>) => res.body ?? []))
      .pipe(
        map((diseases: IDisease[]) => this.diseaseService.addDiseaseToCollectionIfMissing(diseases, this.editForm.get('diseaseID')!.value))
      )
      .subscribe((diseases: IDisease[]) => (this.diseasesSharedCollection = diseases));

    this.cropService
      .query()
      .pipe(map((res: HttpResponse<ICrop[]>) => res.body ?? []))
      .pipe(map((crops: ICrop[]) => this.cropService.addCropToCollectionIfMissing(crops, this.editForm.get('cropID')!.value)))
      .subscribe((crops: ICrop[]) => (this.cropsSharedCollection = crops));

    this.symptomService
      .query()
      .pipe(map((res: HttpResponse<ISymptom[]>) => res.body ?? []))
      .pipe(
        map((symptoms: ISymptom[]) => this.symptomService.addSymptomToCollectionIfMissing(symptoms, this.editForm.get('symptomID')!.value))
      )
      .subscribe((symptoms: ISymptom[]) => (this.symptomsSharedCollection = symptoms));
  }

  protected createFromForm(): IDiseaseControl {
    return {
      ...new DiseaseControl(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      controlType: this.editForm.get(['controlType'])!.value,
      treatment: this.editForm.get(['treatment'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      diseaseID: this.editForm.get(['diseaseID'])!.value,
      cropID: this.editForm.get(['cropID'])!.value,
      symptomID: this.editForm.get(['symptomID'])!.value,
    };
  }
}
