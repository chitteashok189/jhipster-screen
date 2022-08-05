import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyStatusType, PartyStatusType } from '../party-status-type.model';
import { PartyStatusTypeService } from '../service/party-status-type.service';

@Component({
  selector: 'jhi-party-status-type-update',
  templateUrl: './party-status-type-update.component.html',
})
export class PartyStatusTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    parentTypeID: [],
    hasTable: [],
    description: [],
    childStatusType: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(
    protected partyStatusTypeService: PartyStatusTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatusType }) => {
      if (partyStatusType.id === undefined) {
        const today = dayjs().startOf('day');
        partyStatusType.createdOn = today;
        partyStatusType.updatedOn = today;
      }

      this.updateForm(partyStatusType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyStatusType = this.createFromForm();
    if (partyStatusType.id !== undefined) {
      this.subscribeToSaveResponse(this.partyStatusTypeService.update(partyStatusType));
    } else {
      this.subscribeToSaveResponse(this.partyStatusTypeService.create(partyStatusType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyStatusType>>): void {
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

  protected updateForm(partyStatusType: IPartyStatusType): void {
    this.editForm.patchValue({
      id: partyStatusType.id,
      gUID: partyStatusType.gUID,
      parentTypeID: partyStatusType.parentTypeID,
      hasTable: partyStatusType.hasTable,
      description: partyStatusType.description,
      childStatusType: partyStatusType.childStatusType,
      createdBy: partyStatusType.createdBy,
      createdOn: partyStatusType.createdOn ? partyStatusType.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyStatusType.updatedBy,
      updatedOn: partyStatusType.updatedOn ? partyStatusType.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyStatusType {
    return {
      ...new PartyStatusType(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      parentTypeID: this.editForm.get(['parentTypeID'])!.value,
      hasTable: this.editForm.get(['hasTable'])!.value,
      description: this.editForm.get(['description'])!.value,
      childStatusType: this.editForm.get(['childStatusType'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
