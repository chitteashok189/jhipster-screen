import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRoleTypeAttribute, RoleTypeAttribute } from '../role-type-attribute.model';
import { RoleTypeAttributeService } from '../service/role-type-attribute.service';
import { IRoleType } from 'app/entities/role-type/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/service/role-type.service';

@Component({
  selector: 'jhi-role-type-attribute-update',
  templateUrl: './role-type-attribute-update.component.html',
})
export class RoleTypeAttributeUpdateComponent implements OnInit {
  isSaving = false;

  roleTypesSharedCollection: IRoleType[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    attributeName: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    roleTypeID: [],
  });

  constructor(
    protected roleTypeAttributeService: RoleTypeAttributeService,
    protected roleTypeService: RoleTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleTypeAttribute }) => {
      if (roleTypeAttribute.id === undefined) {
        const today = dayjs().startOf('day');
        roleTypeAttribute.createdOn = today;
        roleTypeAttribute.updatedOn = today;
      }

      this.updateForm(roleTypeAttribute);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roleTypeAttribute = this.createFromForm();
    if (roleTypeAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.roleTypeAttributeService.update(roleTypeAttribute));
    } else {
      this.subscribeToSaveResponse(this.roleTypeAttributeService.create(roleTypeAttribute));
    }
  }

  trackRoleTypeById(_index: number, item: IRoleType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleTypeAttribute>>): void {
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

  protected updateForm(roleTypeAttribute: IRoleTypeAttribute): void {
    this.editForm.patchValue({
      id: roleTypeAttribute.id,
      gUID: roleTypeAttribute.gUID,
      attributeName: roleTypeAttribute.attributeName,
      description: roleTypeAttribute.description,
      createdBy: roleTypeAttribute.createdBy,
      createdOn: roleTypeAttribute.createdOn ? roleTypeAttribute.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: roleTypeAttribute.updatedBy,
      updatedOn: roleTypeAttribute.updatedOn ? roleTypeAttribute.updatedOn.format(DATE_TIME_FORMAT) : null,
      roleTypeID: roleTypeAttribute.roleTypeID,
    });

    this.roleTypesSharedCollection = this.roleTypeService.addRoleTypeToCollectionIfMissing(
      this.roleTypesSharedCollection,
      roleTypeAttribute.roleTypeID
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IRoleTypeAttribute {
    return {
      ...new RoleTypeAttribute(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      attributeName: this.editForm.get(['attributeName'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      roleTypeID: this.editForm.get(['roleTypeID'])!.value,
    };
  }
}
