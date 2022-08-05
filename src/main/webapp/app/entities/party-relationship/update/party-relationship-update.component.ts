import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyRelationship, PartyRelationship } from '../party-relationship.model';
import { PartyRelationshipService } from '../service/party-relationship.service';
import { IPartyRelationshipType } from 'app/entities/party-relationship-type/party-relationship-type.model';
import { PartyRelationshipTypeService } from 'app/entities/party-relationship-type/service/party-relationship-type.service';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';

@Component({
  selector: 'jhi-party-relationship-update',
  templateUrl: './party-relationship-update.component.html',
})
export class PartyRelationshipUpdateComponent implements OnInit {
  isSaving = false;

  partyRelationshipTypesSharedCollection: IPartyRelationshipType[] = [];
  securityGroupsSharedCollection: ISecurityGroup[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    partyIdTo: [],
    partyIdFrom: [],
    roleTypeIdFrom: [],
    roleTypeIdTo: [],
    fromDate: [],
    thruDate: [],
    relationshipName: [],
    positionTitle: [],
    comments: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyRelationshipTypeID: [],
    securityGroupID: [],
  });

  constructor(
    protected partyRelationshipService: PartyRelationshipService,
    protected partyRelationshipTypeService: PartyRelationshipTypeService,
    protected securityGroupService: SecurityGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRelationship }) => {
      if (partyRelationship.id === undefined) {
        const today = dayjs().startOf('day');
        partyRelationship.fromDate = today;
        partyRelationship.thruDate = today;
        partyRelationship.createdOn = today;
        partyRelationship.updatedOn = today;
      }

      this.updateForm(partyRelationship);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyRelationship = this.createFromForm();
    if (partyRelationship.id !== undefined) {
      this.subscribeToSaveResponse(this.partyRelationshipService.update(partyRelationship));
    } else {
      this.subscribeToSaveResponse(this.partyRelationshipService.create(partyRelationship));
    }
  }

  trackPartyRelationshipTypeById(_index: number, item: IPartyRelationshipType): number {
    return item.id!;
  }

  trackSecurityGroupById(_index: number, item: ISecurityGroup): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyRelationship>>): void {
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

  protected updateForm(partyRelationship: IPartyRelationship): void {
    this.editForm.patchValue({
      id: partyRelationship.id,
      gUID: partyRelationship.gUID,
      partyIdTo: partyRelationship.partyIdTo,
      partyIdFrom: partyRelationship.partyIdFrom,
      roleTypeIdFrom: partyRelationship.roleTypeIdFrom,
      roleTypeIdTo: partyRelationship.roleTypeIdTo,
      fromDate: partyRelationship.fromDate ? partyRelationship.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: partyRelationship.thruDate ? partyRelationship.thruDate.format(DATE_TIME_FORMAT) : null,
      relationshipName: partyRelationship.relationshipName,
      positionTitle: partyRelationship.positionTitle,
      comments: partyRelationship.comments,
      createdBy: partyRelationship.createdBy,
      createdOn: partyRelationship.createdOn ? partyRelationship.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyRelationship.updatedBy,
      updatedOn: partyRelationship.updatedOn ? partyRelationship.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyRelationshipTypeID: partyRelationship.partyRelationshipTypeID,
      securityGroupID: partyRelationship.securityGroupID,
    });

    this.partyRelationshipTypesSharedCollection = this.partyRelationshipTypeService.addPartyRelationshipTypeToCollectionIfMissing(
      this.partyRelationshipTypesSharedCollection,
      partyRelationship.partyRelationshipTypeID
    );
    this.securityGroupsSharedCollection = this.securityGroupService.addSecurityGroupToCollectionIfMissing(
      this.securityGroupsSharedCollection,
      partyRelationship.securityGroupID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partyRelationshipTypeService
      .query()
      .pipe(map((res: HttpResponse<IPartyRelationshipType[]>) => res.body ?? []))
      .pipe(
        map((partyRelationshipTypes: IPartyRelationshipType[]) =>
          this.partyRelationshipTypeService.addPartyRelationshipTypeToCollectionIfMissing(
            partyRelationshipTypes,
            this.editForm.get('partyRelationshipTypeID')!.value
          )
        )
      )
      .subscribe(
        (partyRelationshipTypes: IPartyRelationshipType[]) => (this.partyRelationshipTypesSharedCollection = partyRelationshipTypes)
      );

    this.securityGroupService
      .query()
      .pipe(map((res: HttpResponse<ISecurityGroup[]>) => res.body ?? []))
      .pipe(
        map((securityGroups: ISecurityGroup[]) =>
          this.securityGroupService.addSecurityGroupToCollectionIfMissing(securityGroups, this.editForm.get('securityGroupID')!.value)
        )
      )
      .subscribe((securityGroups: ISecurityGroup[]) => (this.securityGroupsSharedCollection = securityGroups));
  }

  protected createFromForm(): IPartyRelationship {
    return {
      ...new PartyRelationship(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      partyIdTo: this.editForm.get(['partyIdTo'])!.value,
      partyIdFrom: this.editForm.get(['partyIdFrom'])!.value,
      roleTypeIdFrom: this.editForm.get(['roleTypeIdFrom'])!.value,
      roleTypeIdTo: this.editForm.get(['roleTypeIdTo'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? dayjs(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? dayjs(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      relationshipName: this.editForm.get(['relationshipName'])!.value,
      positionTitle: this.editForm.get(['positionTitle'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyRelationshipTypeID: this.editForm.get(['partyRelationshipTypeID'])!.value,
      securityGroupID: this.editForm.get(['securityGroupID'])!.value,
    };
  }
}
