import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDisease, Disease } from '../disease.model';
import { DiseaseService } from '../service/disease.service';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { DisThreatLevel } from 'app/entities/enumerations/dis-threat-level.model';

@Component({
  selector: 'jhi-disease-update',
  templateUrl: './disease-update.component.html',
})
export class DiseaseUpdateComponent implements OnInit {
  isSaving = false;
  disThreatLevelValues = Object.keys(DisThreatLevel);

  scoutingsSharedCollection: IScouting[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    threatLevel: [],
    name: [],
    causalOrganism: [],
    attachments: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    scoutingID: [],
    plantFactoryID: [],
  });

  constructor(
    protected diseaseService: DiseaseService,
    protected scoutingService: ScoutingService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disease }) => {
      if (disease.id === undefined) {
        const today = dayjs().startOf('day');
        disease.createdOn = today;
        disease.updatedOn = today;
      }

      this.updateForm(disease);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disease = this.createFromForm();
    if (disease.id !== undefined) {
      this.subscribeToSaveResponse(this.diseaseService.update(disease));
    } else {
      this.subscribeToSaveResponse(this.diseaseService.create(disease));
    }
  }

  trackScoutingById(_index: number, item: IScouting): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisease>>): void {
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

  protected updateForm(disease: IDisease): void {
    this.editForm.patchValue({
      id: disease.id,
      gUID: disease.gUID,
      threatLevel: disease.threatLevel,
      name: disease.name,
      causalOrganism: disease.causalOrganism,
      attachments: disease.attachments,
      createdBy: disease.createdBy,
      createdOn: disease.createdOn ? disease.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: disease.updatedBy,
      updatedOn: disease.updatedOn ? disease.updatedOn.format(DATE_TIME_FORMAT) : null,
      scoutingID: disease.scoutingID,
      plantFactoryID: disease.plantFactoryID,
    });

    this.scoutingsSharedCollection = this.scoutingService.addScoutingToCollectionIfMissing(
      this.scoutingsSharedCollection,
      disease.scoutingID
    );
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      disease.plantFactoryID
    );
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

    this.plantFactoryService
      .query()
      .pipe(map((res: HttpResponse<IPlantFactory[]>) => res.body ?? []))
      .pipe(
        map((plantFactories: IPlantFactory[]) =>
          this.plantFactoryService.addPlantFactoryToCollectionIfMissing(plantFactories, this.editForm.get('plantFactoryID')!.value)
        )
      )
      .subscribe((plantFactories: IPlantFactory[]) => (this.plantFactoriesSharedCollection = plantFactories));
  }

  protected createFromForm(): IDisease {
    return {
      ...new Disease(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      threatLevel: this.editForm.get(['threatLevel'])!.value,
      name: this.editForm.get(['name'])!.value,
      causalOrganism: this.editForm.get(['causalOrganism'])!.value,
      attachments: this.editForm.get(['attachments'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      scoutingID: this.editForm.get(['scoutingID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
