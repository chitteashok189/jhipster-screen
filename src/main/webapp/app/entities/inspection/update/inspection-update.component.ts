import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInspection, Inspection } from '../inspection.model';
import { InspectionService } from '../service/inspection.service';
import { IHarvest } from 'app/entities/harvest/harvest.model';
import { HarvestService } from 'app/entities/harvest/service/harvest.service';
import { Defect } from 'app/entities/enumerations/defect.model';
import { Texture } from 'app/entities/enumerations/texture.model';
import { Aroma } from 'app/entities/enumerations/aroma.model';
import { Flavour } from 'app/entities/enumerations/flavour.model';
import { NutritionalValueType } from 'app/entities/enumerations/nutritional-value-type.model';

@Component({
  selector: 'jhi-inspection-update',
  templateUrl: './inspection-update.component.html',
})
export class InspectionUpdateComponent implements OnInit {
  isSaving = false;
  defectValues = Object.keys(Defect);
  textureValues = Object.keys(Texture);
  aromaValues = Object.keys(Aroma);
  flavourValues = Object.keys(Flavour);
  nutritionalValueTypeValues = Object.keys(NutritionalValueType);

  harvestsSharedCollection: IHarvest[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    inspectionSize: [],
    shape: [],
    wholeness: [],
    gloss: [],
    consistency: [],
    defects: [],
    colour: [],
    texture: [],
    aroma: [],
    flavour: [],
    nutritionalValue: [],
    nutritionalValueType: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    harvestID: [],
  });

  constructor(
    protected inspectionService: InspectionService,
    protected harvestService: HarvestService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inspection }) => {
      if (inspection.id === undefined) {
        const today = dayjs().startOf('day');
        inspection.createdOn = today;
        inspection.updatedOn = today;
      }

      this.updateForm(inspection);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inspection = this.createFromForm();
    if (inspection.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectionService.update(inspection));
    } else {
      this.subscribeToSaveResponse(this.inspectionService.create(inspection));
    }
  }

  trackHarvestById(_index: number, item: IHarvest): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspection>>): void {
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

  protected updateForm(inspection: IInspection): void {
    this.editForm.patchValue({
      id: inspection.id,
      gUID: inspection.gUID,
      inspectionSize: inspection.inspectionSize,
      shape: inspection.shape,
      wholeness: inspection.wholeness,
      gloss: inspection.gloss,
      consistency: inspection.consistency,
      defects: inspection.defects,
      colour: inspection.colour,
      texture: inspection.texture,
      aroma: inspection.aroma,
      flavour: inspection.flavour,
      nutritionalValue: inspection.nutritionalValue,
      nutritionalValueType: inspection.nutritionalValueType,
      createdBy: inspection.createdBy,
      createdOn: inspection.createdOn ? inspection.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: inspection.updatedBy,
      updatedOn: inspection.updatedOn ? inspection.updatedOn.format(DATE_TIME_FORMAT) : null,
      harvestID: inspection.harvestID,
    });

    this.harvestsSharedCollection = this.harvestService.addHarvestToCollectionIfMissing(
      this.harvestsSharedCollection,
      inspection.harvestID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.harvestService
      .query()
      .pipe(map((res: HttpResponse<IHarvest[]>) => res.body ?? []))
      .pipe(
        map((harvests: IHarvest[]) => this.harvestService.addHarvestToCollectionIfMissing(harvests, this.editForm.get('harvestID')!.value))
      )
      .subscribe((harvests: IHarvest[]) => (this.harvestsSharedCollection = harvests));
  }

  protected createFromForm(): IInspection {
    return {
      ...new Inspection(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      inspectionSize: this.editForm.get(['inspectionSize'])!.value,
      shape: this.editForm.get(['shape'])!.value,
      wholeness: this.editForm.get(['wholeness'])!.value,
      gloss: this.editForm.get(['gloss'])!.value,
      consistency: this.editForm.get(['consistency'])!.value,
      defects: this.editForm.get(['defects'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      texture: this.editForm.get(['texture'])!.value,
      aroma: this.editForm.get(['aroma'])!.value,
      flavour: this.editForm.get(['flavour'])!.value,
      nutritionalValue: this.editForm.get(['nutritionalValue'])!.value,
      nutritionalValueType: this.editForm.get(['nutritionalValueType'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      harvestID: this.editForm.get(['harvestID'])!.value,
    };
  }
}
