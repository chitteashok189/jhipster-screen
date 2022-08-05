import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPest, Pest } from '../pest.model';
import { PestService } from '../service/pest.service';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { ThreatLevel } from 'app/entities/enumerations/threat-level.model';

@Component({
  selector: 'jhi-pest-update',
  templateUrl: './pest-update.component.html',
})
export class PestUpdateComponent implements OnInit {
  isSaving = false;
  threatLevelValues = Object.keys(ThreatLevel);

  scoutingsSharedCollection: IScouting[] = [];
  plantFactoriesSharedCollection: IPlantFactory[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    threatLevel: [],
    name: [],
    scientificName: [],
    attachements: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    scoutingID: [],
    plantFactoryID: [],
  });

  constructor(
    protected pestService: PestService,
    protected scoutingService: ScoutingService,
    protected plantFactoryService: PlantFactoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pest }) => {
      if (pest.id === undefined) {
        const today = dayjs().startOf('day');
        pest.createdOn = today;
        pest.updatedOn = today;
      }

      this.updateForm(pest);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pest = this.createFromForm();
    if (pest.id !== undefined) {
      this.subscribeToSaveResponse(this.pestService.update(pest));
    } else {
      this.subscribeToSaveResponse(this.pestService.create(pest));
    }
  }

  trackScoutingById(_index: number, item: IScouting): number {
    return item.id!;
  }

  trackPlantFactoryById(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPest>>): void {
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

  protected updateForm(pest: IPest): void {
    this.editForm.patchValue({
      id: pest.id,
      gUID: pest.gUID,
      threatLevel: pest.threatLevel,
      name: pest.name,
      scientificName: pest.scientificName,
      attachements: pest.attachements,
      createdBy: pest.createdBy,
      createdOn: pest.createdOn ? pest.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: pest.updatedBy,
      updatedOn: pest.updatedOn ? pest.updatedOn.format(DATE_TIME_FORMAT) : null,
      scoutingID: pest.scoutingID,
      plantFactoryID: pest.plantFactoryID,
    });

    this.scoutingsSharedCollection = this.scoutingService.addScoutingToCollectionIfMissing(this.scoutingsSharedCollection, pest.scoutingID);
    this.plantFactoriesSharedCollection = this.plantFactoryService.addPlantFactoryToCollectionIfMissing(
      this.plantFactoriesSharedCollection,
      pest.plantFactoryID
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

  protected createFromForm(): IPest {
    return {
      ...new Pest(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      threatLevel: this.editForm.get(['threatLevel'])!.value,
      name: this.editForm.get(['name'])!.value,
      scientificName: this.editForm.get(['scientificName'])!.value,
      attachements: this.editForm.get(['attachements'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      scoutingID: this.editForm.get(['scoutingID'])!.value,
      plantFactoryID: this.editForm.get(['plantFactoryID'])!.value,
    };
  }
}
