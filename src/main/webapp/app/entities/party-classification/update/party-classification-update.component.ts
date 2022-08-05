import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyClassification, PartyClassification } from '../party-classification.model';
import { PartyClassificationService } from '../service/party-classification.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

@Component({
  selector: 'jhi-party-classification-update',
  templateUrl: './party-classification-update.component.html',
})
export class PartyClassificationUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    fromDate: [],
    thruDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
  });

  constructor(
    protected partyClassificationService: PartyClassificationService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassification }) => {
      if (partyClassification.id === undefined) {
        const today = dayjs().startOf('day');
        partyClassification.fromDate = today;
        partyClassification.thruDate = today;
        partyClassification.createdOn = today;
        partyClassification.updatedOn = today;
      }

      this.updateForm(partyClassification);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyClassification = this.createFromForm();
    if (partyClassification.id !== undefined) {
      this.subscribeToSaveResponse(this.partyClassificationService.update(partyClassification));
    } else {
      this.subscribeToSaveResponse(this.partyClassificationService.create(partyClassification));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyClassification>>): void {
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

  protected updateForm(partyClassification: IPartyClassification): void {
    this.editForm.patchValue({
      id: partyClassification.id,
      gUID: partyClassification.gUID,
      fromDate: partyClassification.fromDate ? partyClassification.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: partyClassification.thruDate ? partyClassification.thruDate.format(DATE_TIME_FORMAT) : null,
      createdBy: partyClassification.createdBy,
      createdOn: partyClassification.createdOn ? partyClassification.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyClassification.updatedBy,
      updatedOn: partyClassification.updatedOn ? partyClassification.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: partyClassification.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(
      this.partiesSharedCollection,
      partyClassification.partyID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));
  }

  protected createFromForm(): IPartyClassification {
    return {
      ...new PartyClassification(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? dayjs(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? dayjs(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
