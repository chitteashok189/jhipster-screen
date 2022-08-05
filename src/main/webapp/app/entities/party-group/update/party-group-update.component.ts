import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyGroup, PartyGroup } from '../party-group.model';
import { PartyGroupService } from '../service/party-group.service';

@Component({
  selector: 'jhi-party-group-update',
  templateUrl: './party-group-update.component.html',
})
export class PartyGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gUID: [],
    groupName: [],
    localGroupName: [],
    officeSiteName: [],
    comments: [],
    logoImageUrl: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected partyGroupService: PartyGroupService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyGroup }) => {
      if (partyGroup.id === undefined) {
        const today = dayjs().startOf('day');
        partyGroup.createdOn = today;
        partyGroup.updatedOn = today;
      }

      this.updateForm(partyGroup);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyGroup = this.createFromForm();
    if (partyGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.partyGroupService.update(partyGroup));
    } else {
      this.subscribeToSaveResponse(this.partyGroupService.create(partyGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyGroup>>): void {
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

  protected updateForm(partyGroup: IPartyGroup): void {
    this.editForm.patchValue({
      id: partyGroup.id,
      gUID: partyGroup.gUID,
      groupName: partyGroup.groupName,
      localGroupName: partyGroup.localGroupName,
      officeSiteName: partyGroup.officeSiteName,
      comments: partyGroup.comments,
      logoImageUrl: partyGroup.logoImageUrl,
      createdBy: partyGroup.createdBy,
      createdOn: partyGroup.createdOn ? partyGroup.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyGroup.updatedBy,
      updatedOn: partyGroup.updatedOn ? partyGroup.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyGroup {
    return {
      ...new PartyGroup(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      groupName: this.editForm.get(['groupName'])!.value,
      localGroupName: this.editForm.get(['localGroupName'])!.value,
      officeSiteName: this.editForm.get(['officeSiteName'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      logoImageUrl: this.editForm.get(['logoImageUrl'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
