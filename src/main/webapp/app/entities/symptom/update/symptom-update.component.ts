import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISymptom, Symptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IDisease } from 'app/entities/disease/disease.model';
import { DiseaseService } from 'app/entities/disease/service/disease.service';
import { IPest } from 'app/entities/pest/pest.model';
import { PestService } from 'app/entities/pest/service/pest.service';

@Component({
  selector: 'jhi-symptom-update',
  templateUrl: './symptom-update.component.html',
})
export class SymptomUpdateComponent implements OnInit {
  isSaving = false;

  scoutingsSharedCollection: IScouting[] = [];
  diseasesSharedCollection: IDisease[] = [];
  pestsSharedCollection: IPest[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    observation: [],
    symptomImage: [],
    symptomImageContentType: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    scoutingID: [],
    diseaseID: [],
    pestID: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected symptomService: SymptomService,
    protected scoutingService: ScoutingService,
    protected diseaseService: DiseaseService,
    protected pestService: PestService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptom }) => {
      if (symptom.id === undefined) {
        const today = dayjs().startOf('day');
        symptom.createdOn = today;
        symptom.updatedOn = today;
      }

      this.updateForm(symptom);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('blogApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptom = this.createFromForm();
    if (symptom.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomService.update(symptom));
    } else {
      this.subscribeToSaveResponse(this.symptomService.create(symptom));
    }
  }

  trackScoutingById(_index: number, item: IScouting): number {
    return item.id!;
  }

  trackDiseaseById(_index: number, item: IDisease): number {
    return item.id!;
  }

  trackPestById(_index: number, item: IPest): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptom>>): void {
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

  protected updateForm(symptom: ISymptom): void {
    this.editForm.patchValue({
      id: symptom.id,
      gUID: symptom.gUID,
      observation: symptom.observation,
      symptomImage: symptom.symptomImage,
      symptomImageContentType: symptom.symptomImageContentType,
      createdBy: symptom.createdBy,
      createdOn: symptom.createdOn ? symptom.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: symptom.updatedBy,
      updatedOn: symptom.updatedOn ? symptom.updatedOn.format(DATE_TIME_FORMAT) : null,
      scoutingID: symptom.scoutingID,
      diseaseID: symptom.diseaseID,
      pestID: symptom.pestID,
    });

    this.scoutingsSharedCollection = this.scoutingService.addScoutingToCollectionIfMissing(
      this.scoutingsSharedCollection,
      symptom.scoutingID
    );
    this.diseasesSharedCollection = this.diseaseService.addDiseaseToCollectionIfMissing(this.diseasesSharedCollection, symptom.diseaseID);
    this.pestsSharedCollection = this.pestService.addPestToCollectionIfMissing(this.pestsSharedCollection, symptom.pestID);
  }

  protected loadRelationshipsOptions(): void {
    this.scoutingService
      .query()
      .pipe(map((res: HttpResponse<IScouting[]>) => res.body ?? []))
      .pipe(
        map((scoutings: IScouting[]) =>
          this.scoutingService.addScoutingToCollectionIfMissing(scoutings, this.editForm.get('scoutingID')!.value)
        )
      )
      .subscribe((scoutings: IScouting[]) => (this.scoutingsSharedCollection = scoutings));

    this.diseaseService
      .query()
      .pipe(map((res: HttpResponse<IDisease[]>) => res.body ?? []))
      .pipe(
        map((diseases: IDisease[]) => this.diseaseService.addDiseaseToCollectionIfMissing(diseases, this.editForm.get('diseaseID')!.value))
      )
      .subscribe((diseases: IDisease[]) => (this.diseasesSharedCollection = diseases));

    this.pestService
      .query()
      .pipe(map((res: HttpResponse<IPest[]>) => res.body ?? []))
      .pipe(map((pests: IPest[]) => this.pestService.addPestToCollectionIfMissing(pests, this.editForm.get('pestID')!.value)))
      .subscribe((pests: IPest[]) => (this.pestsSharedCollection = pests));
  }

  protected createFromForm(): ISymptom {
    return {
      ...new Symptom(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      symptomImageContentType: this.editForm.get(['symptomImageContentType'])!.value,
      symptomImage: this.editForm.get(['symptomImage'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      scoutingID: this.editForm.get(['scoutingID'])!.value,
      diseaseID: this.editForm.get(['diseaseID'])!.value,
      pestID: this.editForm.get(['pestID'])!.value,
    };
  }
}
