import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyAttribute, PartyAttribute } from '../party-attribute.model';
import { PartyAttributeService } from '../service/party-attribute.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

@Component({
  selector: 'jhi-party-attribute-update',
  templateUrl: './party-attribute-update.component.html',
})
export class PartyAttributeUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    attributeName: [],
    attributeValue: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
  });

  constructor(
    protected partyAttributeService: PartyAttributeService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyAttribute }) => {
      if (partyAttribute.id === undefined) {
        const today = dayjs().startOf('day');
        partyAttribute.createdOn = today;
        partyAttribute.updatedOn = today;
      }

      this.updateForm(partyAttribute);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyAttribute = this.createFromForm();
    if (partyAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.partyAttributeService.update(partyAttribute));
    } else {
      this.subscribeToSaveResponse(this.partyAttributeService.create(partyAttribute));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyAttribute>>): void {
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

  protected updateForm(partyAttribute: IPartyAttribute): void {
    this.editForm.patchValue({
      id: partyAttribute.id,
      gUID: partyAttribute.gUID,
      attributeName: partyAttribute.attributeName,
      attributeValue: partyAttribute.attributeValue,
      createdBy: partyAttribute.createdBy,
      createdOn: partyAttribute.createdOn ? partyAttribute.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyAttribute.updatedBy,
      updatedOn: partyAttribute.updatedOn ? partyAttribute.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: partyAttribute.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, partyAttribute.partyID);
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));
  }

  protected createFromForm(): IPartyAttribute {
    return {
      ...new PartyAttribute(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      attributeName: this.editForm.get(['attributeName'])!.value,
      attributeValue: this.editForm.get(['attributeValue'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
