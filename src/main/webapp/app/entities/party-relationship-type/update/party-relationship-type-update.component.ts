import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyRelationshipType, PartyRelationshipType } from '../party-relationship-type.model';
import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';
import { IPartyRelationship } from 'app/entities/party-relationship/party-relationship.model';
import { PartyRelationshipService } from 'app/entities/party-relationship/service/party-relationship.service';

@Component({
  selector: 'jhi-party-relationship-type-update',
  templateUrl: './party-relationship-type-update.component.html',
})
export class PartyRelationshipTypeUpdateComponent implements OnInit {
  isSaving = false;

  partyRelationshipsSharedCollection: IPartyRelationship[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    hasTable: [],
    partyRelationshipName: [],
    description: [],
    roleTypeIdValidFrom: [],
    roleTypeIdValidTo: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyRelationshipID: [],
  });

  constructor(
    protected partyRelationshipTypeService: PartyRelationshipTypeService,
    protected partyRelationshipService: PartyRelationshipService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRelationshipType }) => {
      if (partyRelationshipType.id === undefined) {
        const today = dayjs().startOf('day');
        partyRelationshipType.createdOn = today;
        partyRelationshipType.updatedOn = today;
      }

      this.updateForm(partyRelationshipType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyRelationshipType = this.createFromForm();
    if (partyRelationshipType.id !== undefined) {
      this.subscribeToSaveResponse(this.partyRelationshipTypeService.update(partyRelationshipType));
    } else {
      this.subscribeToSaveResponse(this.partyRelationshipTypeService.create(partyRelationshipType));
    }
  }

  trackPartyRelationshipById(_index: number, item: IPartyRelationship): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyRelationshipType>>): void {
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

  protected updateForm(partyRelationshipType: IPartyRelationshipType): void {
    this.editForm.patchValue({
      id: partyRelationshipType.id,
      gUID: partyRelationshipType.gUID,
      hasTable: partyRelationshipType.hasTable,
      partyRelationshipName: partyRelationshipType.partyRelationshipName,
      description: partyRelationshipType.description,
      roleTypeIdValidFrom: partyRelationshipType.roleTypeIdValidFrom,
      roleTypeIdValidTo: partyRelationshipType.roleTypeIdValidTo,
      createdBy: partyRelationshipType.createdBy,
      createdOn: partyRelationshipType.createdOn ? partyRelationshipType.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyRelationshipType.updatedBy,
      updatedOn: partyRelationshipType.updatedOn ? partyRelationshipType.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyRelationshipID: partyRelationshipType.partyRelationshipID,
    });

    this.partyRelationshipsSharedCollection = this.partyRelationshipService.addPartyRelationshipToCollectionIfMissing(
      this.partyRelationshipsSharedCollection,
      partyRelationshipType.partyRelationshipID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partyRelationshipService
      .query()
      .pipe(map((res: HttpResponse<IPartyRelationship[]>) => res.body ?? []))
      .pipe(
        map((partyRelationships: IPartyRelationship[]) =>
          this.partyRelationshipService.addPartyRelationshipToCollectionIfMissing(
            partyRelationships,
            this.editForm.get('partyRelationshipID')!.value
          )
        )
      )
      .subscribe((partyRelationships: IPartyRelationship[]) => (this.partyRelationshipsSharedCollection = partyRelationships));
  }

  protected createFromForm(): IPartyRelationshipType {
    return {
      ...new PartyRelationshipType(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      hasTable: this.editForm.get(['hasTable'])!.value,
      partyRelationshipName: this.editForm.get(['partyRelationshipName'])!.value,
      description: this.editForm.get(['description'])!.value,
      roleTypeIdValidFrom: this.editForm.get(['roleTypeIdValidFrom'])!.value,
      roleTypeIdValidTo: this.editForm.get(['roleTypeIdValidTo'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyRelationshipID: this.editForm.get(['partyRelationshipID'])!.value,
    };
  }
}
