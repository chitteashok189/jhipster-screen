import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyTypeAttribute, PartyTypeAttribute } from '../party-type-attribute.model';
import { PartyTypeAttributeService } from '../service/party-type-attribute.service';

@Component({
  selector: 'jhi-party-type-attribute-update',
  templateUrl: './party-type-attribute-update.component.html',
})
export class PartyTypeAttributeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    attributeName: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(
    protected partyTypeAttributeService: PartyTypeAttributeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyTypeAttribute }) => {
      if (partyTypeAttribute.id === undefined) {
        const today = dayjs().startOf('day');
        partyTypeAttribute.createdOn = today;
        partyTypeAttribute.updatedOn = today;
      }

      this.updateForm(partyTypeAttribute);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyTypeAttribute = this.createFromForm();
    if (partyTypeAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.partyTypeAttributeService.update(partyTypeAttribute));
    } else {
      this.subscribeToSaveResponse(this.partyTypeAttributeService.create(partyTypeAttribute));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyTypeAttribute>>): void {
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

  protected updateForm(partyTypeAttribute: IPartyTypeAttribute): void {
    this.editForm.patchValue({
      id: partyTypeAttribute.id,
      gUID: partyTypeAttribute.gUID,
      attributeName: partyTypeAttribute.attributeName,
      createdBy: partyTypeAttribute.createdBy,
      createdOn: partyTypeAttribute.createdOn ? partyTypeAttribute.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyTypeAttribute.updatedBy,
      updatedOn: partyTypeAttribute.updatedOn ? partyTypeAttribute.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyTypeAttribute {
    return {
      ...new PartyTypeAttribute(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      attributeName: this.editForm.get(['attributeName'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
