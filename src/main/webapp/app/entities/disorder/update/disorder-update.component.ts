import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDisorder, Disorder } from '../disorder.model';
import { DisorderService } from '../service/disorder.service';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';

@Component({
  selector: 'jhi-disorder-update',
  templateUrl: './disorder-update.component.html',
})
export class DisorderUpdateComponent implements OnInit {
  isSaving = false;

  scoutingsSharedCollection: IScouting[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    physiologicalDisorder: [],
    cause: [],
    controlMeasure: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    scoutingID: [],
  });

  constructor(
    protected disorderService: DisorderService,
    protected scoutingService: ScoutingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disorder }) => {
      if (disorder.id === undefined) {
        const today = dayjs().startOf('day');
        disorder.createdOn = today;
        disorder.updatedOn = today;
      }

      this.updateForm(disorder);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disorder = this.createFromForm();
    if (disorder.id !== undefined) {
      this.subscribeToSaveResponse(this.disorderService.update(disorder));
    } else {
      this.subscribeToSaveResponse(this.disorderService.create(disorder));
    }
  }

  trackScoutingById(_index: number, item: IScouting): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisorder>>): void {
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

  protected updateForm(disorder: IDisorder): void {
    this.editForm.patchValue({
      id: disorder.id,
      gUID: disorder.gUID,
      physiologicalDisorder: disorder.physiologicalDisorder,
      cause: disorder.cause,
      controlMeasure: disorder.controlMeasure,
      createdBy: disorder.createdBy,
      createdOn: disorder.createdOn ? disorder.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: disorder.updatedBy,
      updatedOn: disorder.updatedOn ? disorder.updatedOn.format(DATE_TIME_FORMAT) : null,
      scoutingID: disorder.scoutingID,
    });

    this.scoutingsSharedCollection = this.scoutingService.addScoutingToCollectionIfMissing(
      this.scoutingsSharedCollection,
      disorder.scoutingID
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
  }

  protected createFromForm(): IDisorder {
    return {
      ...new Disorder(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      physiologicalDisorder: this.editForm.get(['physiologicalDisorder'])!.value,
      cause: this.editForm.get(['cause'])!.value,
      controlMeasure: this.editForm.get(['controlMeasure'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      scoutingID: this.editForm.get(['scoutingID'])!.value,
    };
  }
}
