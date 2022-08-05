import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyRole, PartyRole } from '../party-role.model';
import { PartyRoleService } from '../service/party-role.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';
import { IRoleType } from 'app/entities/role-type/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/service/role-type.service';

@Component({
  selector: 'jhi-party-role-update',
  templateUrl: './party-role-update.component.html',
})
export class PartyRoleUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];
  roleTypesSharedCollection: IRoleType[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    fromAgreement: [],
    toAgreement: [],
    fromCommunicationEvent: [],
    toCommunicationEvent: [],
    partyIdFrom: [],
    partyIdTO: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    roleTypeID: [],
    partyID: [],
  });

  constructor(
    protected partyRoleService: PartyRoleService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRole }) => {
      if (partyRole.id === undefined) {
        const today = dayjs().startOf('day');
        partyRole.createdOn = today;
        partyRole.updatedOn = today;
      }

      this.updateForm(partyRole);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyRole = this.createFromForm();
    if (partyRole.id !== undefined) {
      this.subscribeToSaveResponse(this.partyRoleService.update(partyRole));
    } else {
      this.subscribeToSaveResponse(this.partyRoleService.create(partyRole));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  trackRoleTypeById(_index: number, item: IRoleType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyRole>>): void {
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

  protected updateForm(partyRole: IPartyRole): void {
    this.editForm.patchValue({
      id: partyRole.id,
      gUID: partyRole.gUID,
      fromAgreement: partyRole.fromAgreement,
      toAgreement: partyRole.toAgreement,
      fromCommunicationEvent: partyRole.fromCommunicationEvent,
      toCommunicationEvent: partyRole.toCommunicationEvent,
      partyIdFrom: partyRole.partyIdFrom,
      partyIdTO: partyRole.partyIdTO,
      createdBy: partyRole.createdBy,
      createdOn: partyRole.createdOn ? partyRole.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyRole.updatedBy,
      updatedOn: partyRole.updatedOn ? partyRole.updatedOn.format(DATE_TIME_FORMAT) : null,
      roleTypeID: partyRole.roleTypeID,
      partyID: partyRole.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, partyRole.partyID);
    this.roleTypesSharedCollection = this.roleTypeService.addRoleTypeToCollectionIfMissing(
      this.roleTypesSharedCollection,
      partyRole.roleTypeID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));

    this.roleTypeService
      .query()
      .pipe(map((res: HttpResponse<IRoleType[]>) => res.body ?? []))
      .pipe(
        map((roleTypes: IRoleType[]) =>
          this.roleTypeService.addRoleTypeToCollectionIfMissing(roleTypes, this.editForm.get('roleTypeID')!.value)
        )
      )
      .subscribe((roleTypes: IRoleType[]) => (this.roleTypesSharedCollection = roleTypes));
  }

  protected createFromForm(): IPartyRole {
    return {
      ...new PartyRole(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      fromAgreement: this.editForm.get(['fromAgreement'])!.value,
      toAgreement: this.editForm.get(['toAgreement'])!.value,
      fromCommunicationEvent: this.editForm.get(['fromCommunicationEvent'])!.value,
      toCommunicationEvent: this.editForm.get(['toCommunicationEvent'])!.value,
      partyIdFrom: this.editForm.get(['partyIdFrom'])!.value,
      partyIdTO: this.editForm.get(['partyIdTO'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      roleTypeID: this.editForm.get(['roleTypeID'])!.value,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
