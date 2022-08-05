import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyStatusItem, PartyStatusItem } from '../party-status-item.model';
import { PartyStatusItemService } from '../service/party-status-item.service';

@Component({
  selector: 'jhi-party-status-item-update',
  templateUrl: './party-status-item-update.component.html',
})
export class PartyStatusItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    statusCode: [],
    sequenceID: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(
    protected partyStatusItemService: PartyStatusItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatusItem }) => {
      if (partyStatusItem.id === undefined) {
        const today = dayjs().startOf('day');
        partyStatusItem.createdOn = today;
        partyStatusItem.updatedOn = today;
      }

      this.updateForm(partyStatusItem);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyStatusItem = this.createFromForm();
    if (partyStatusItem.id !== undefined) {
      this.subscribeToSaveResponse(this.partyStatusItemService.update(partyStatusItem));
    } else {
      this.subscribeToSaveResponse(this.partyStatusItemService.create(partyStatusItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyStatusItem>>): void {
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

  protected updateForm(partyStatusItem: IPartyStatusItem): void {
    this.editForm.patchValue({
      id: partyStatusItem.id,
      gUID: partyStatusItem.gUID,
      statusCode: partyStatusItem.statusCode,
      sequenceID: partyStatusItem.sequenceID,
      description: partyStatusItem.description,
      createdBy: partyStatusItem.createdBy,
      createdOn: partyStatusItem.createdOn ? partyStatusItem.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyStatusItem.updatedBy,
      updatedOn: partyStatusItem.updatedOn ? partyStatusItem.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyStatusItem {
    return {
      ...new PartyStatusItem(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      statusCode: this.editForm.get(['statusCode'])!.value,
      sequenceID: this.editForm.get(['sequenceID'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
