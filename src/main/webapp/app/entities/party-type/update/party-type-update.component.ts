import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyType, PartyType } from '../party-type.model';
import { PartyTypeService } from '../service/party-type.service';
import { ParentType } from 'app/entities/enumerations/parent-type.model';

@Component({
  selector: 'jhi-party-type-update',
  templateUrl: './party-type-update.component.html',
})
export class PartyTypeUpdateComponent implements OnInit {
  isSaving = false;
  parentTypeValues = Object.keys(ParentType);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    parentTypeID: [],
    hasTable: [],
    description: [],
    partyTypeAttr: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected partyTypeService: PartyTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyType }) => {
      if (partyType.id === undefined) {
        const today = dayjs().startOf('day');
        partyType.createdOn = today;
        partyType.updatedOn = today;
      }

      this.updateForm(partyType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyType = this.createFromForm();
    if (partyType.id !== undefined) {
      this.subscribeToSaveResponse(this.partyTypeService.update(partyType));
    } else {
      this.subscribeToSaveResponse(this.partyTypeService.create(partyType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyType>>): void {
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

  protected updateForm(partyType: IPartyType): void {
    this.editForm.patchValue({
      id: partyType.id,
      gUID: partyType.gUID,
      parentTypeID: partyType.parentTypeID,
      hasTable: partyType.hasTable,
      description: partyType.description,
      partyTypeAttr: partyType.partyTypeAttr,
      createdBy: partyType.createdBy,
      createdOn: partyType.createdOn ? partyType.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyType.updatedBy,
      updatedOn: partyType.updatedOn ? partyType.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPartyType {
    return {
      ...new PartyType(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      parentTypeID: this.editForm.get(['parentTypeID'])!.value,
      hasTable: this.editForm.get(['hasTable'])!.value,
      description: this.editForm.get(['description'])!.value,
      partyTypeAttr: this.editForm.get(['partyTypeAttr'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
