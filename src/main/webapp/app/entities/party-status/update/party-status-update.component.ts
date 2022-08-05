import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyStatus, PartyStatus } from '../party-status.model';
import { PartyStatusService } from '../service/party-status.service';

@Component({
  selector: 'jhi-party-status-update',
  templateUrl: './party-status-update.component.html',
})
export class PartyStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    statusDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected partyStatusService: PartyStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatus }) => {
      if (partyStatus.id === undefined) {
        const today = dayjs().startOf('day');
        partyStatus.statusDate = today;
        partyStatus.createdOn = today;
        partyStatus.updatedOn = today;
      }

      this.updateForm(partyStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyStatus = this.createFromForm();
    if (partyStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.partyStatusService.update(partyStatus));
    } else {
      this.subscribeToSaveResponse(this.partyStatusService.create(partyStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyStatus>>): void {
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

  protected updateForm(partyStatus: IPartyStatus): void {
    this.editForm.patchValue({
      id: partyStatus.id,
      gUID: partyStatus.gUID,
      statusDate: partyStatus.statusDate ? partyStatus.statusDate.format(DATE_TIME_FORMAT) : null,
      createdBy: partyStatus.createdBy,
      createdOn: partyStatus.createdOn ? partyStatus.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyStatus.updatedBy,
      updatedOn: partyStatus.updatedOn ? partyStatus.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyStatus {
    return {
      ...new PartyStatus(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      statusDate: this.editForm.get(['statusDate'])!.value ? dayjs(this.editForm.get(['statusDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
