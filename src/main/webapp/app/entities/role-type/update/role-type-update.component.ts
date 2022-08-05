import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRoleType, RoleType } from '../role-type.model';
import { RoleTypeService } from '../service/role-type.service';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { PartyRoleService } from 'app/entities/party-role/service/party-role.service';
import { IRoleTypeAttribute } from 'app/entities/role-type-attribute/role-type-attribute.model';
import { RoleTypeAttributeService } from 'app/entities/role-type-attribute/service/role-type-attribute.service';

@Component({
  selector: 'jhi-role-type-update',
  templateUrl: './role-type-update.component.html',
})
export class RoleTypeUpdateComponent implements OnInit {
  isSaving = false;

  partyRolesSharedCollection: IPartyRole[] = [];
  roleTypeAttributesSharedCollection: IRoleTypeAttribute[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    hasTable: [],
    description: [],
    childRoleType: [],
    validPartyRelationshipType: [],
    validFromPartyRelationshipType: [],
    partyInvitationRoleAssociation: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyRoleID: [],
    roleTypeAttributeID: [],
  });

  constructor(
    protected roleTypeService: RoleTypeService,
    protected partyRoleService: PartyRoleService,
    protected roleTypeAttributeService: RoleTypeAttributeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleType }) => {
      if (roleType.id === undefined) {
        const today = dayjs().startOf('day');
        roleType.createdOn = today;
        roleType.updatedOn = today;
      }

      this.updateForm(roleType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleType = this.createFromForm();
    if (roleType.id !== undefined) {
      this.subscribeToSaveResponse(this.roleTypeService.update(roleType));
    } else {
      this.subscribeToSaveResponse(this.roleTypeService.create(roleType));
    }
  }

  trackPartyRoleById(_index: number, item: IPartyRole): number {
    return item.id!;
  }

  trackRoleTypeAttributeById(_index: number, item: IRoleTypeAttribute): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleType>>): void {
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

  protected updateForm(roleType: IRoleType): void {
    this.editForm.patchValue({
      id: roleType.id,
      gUID: roleType.gUID,
      hasTable: roleType.hasTable,
      description: roleType.description,
      childRoleType: roleType.childRoleType,
      validPartyRelationshipType: roleType.validPartyRelationshipType,
      validFromPartyRelationshipType: roleType.validFromPartyRelationshipType,
      partyInvitationRoleAssociation: roleType.partyInvitationRoleAssociation,
      createdBy: roleType.createdBy,
      createdOn: roleType.createdOn ? roleType.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: roleType.updatedBy,
      updatedOn: roleType.updatedOn ? roleType.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyRoleID: roleType.partyRoleID,
      roleTypeAttributeID: roleType.roleTypeAttributeID,
    });

    this.partyRolesSharedCollection = this.partyRoleService.addPartyRoleToCollectionIfMissing(
      this.partyRolesSharedCollection,
      roleType.partyRoleID
    );
    this.roleTypeAttributesSharedCollection = this.roleTypeAttributeService.addRoleTypeAttributeToCollectionIfMissing(
      this.roleTypeAttributesSharedCollection,
      roleType.roleTypeAttributeID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partyRoleService
      .query()
      .pipe(map((res: HttpResponse<IPartyRole[]>) => res.body ?? []))
      .pipe(
        map((partyRoles: IPartyRole[]) =>
          this.partyRoleService.addPartyRoleToCollectionIfMissing(partyRoles, this.editForm.get('partyRoleID')!.value)
        )
      )
      .subscribe((partyRoles: IPartyRole[]) => (this.partyRolesSharedCollection = partyRoles));

    this.roleTypeAttributeService
      .query()
      .pipe(map((res: HttpResponse<IRoleTypeAttribute[]>) => res.body ?? []))
      .pipe(
        map((roleTypeAttributes: IRoleTypeAttribute[]) =>
          this.roleTypeAttributeService.addRoleTypeAttributeToCollectionIfMissing(
            roleTypeAttributes,
            this.editForm.get('roleTypeAttributeID')!.value
          )
        )
      )
      .subscribe((roleTypeAttributes: IRoleTypeAttribute[]) => (this.roleTypeAttributesSharedCollection = roleTypeAttributes));
  }

  protected createFromForm(): IRoleType {
    return {
      ...new RoleType(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      hasTable: this.editForm.get(['hasTable'])!.value,
      description: this.editForm.get(['description'])!.value,
      childRoleType: this.editForm.get(['childRoleType'])!.value,
      validPartyRelationshipType: this.editForm.get(['validPartyRelationshipType'])!.value,
      validFromPartyRelationshipType: this.editForm.get(['validFromPartyRelationshipType'])!.value,
      partyInvitationRoleAssociation: this.editForm.get(['partyInvitationRoleAssociation'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyRoleID: this.editForm.get(['partyRoleID'])!.value,
      roleTypeAttributeID: this.editForm.get(['roleTypeAttributeID'])!.value,
    };
  }
}
