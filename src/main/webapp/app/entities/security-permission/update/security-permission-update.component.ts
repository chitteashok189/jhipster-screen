import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISecurityPermission, SecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';
import { SecurityGroupPermissionService } from 'app/entities/security-group-permission/service/security-group-permission.service';

@Component({
  selector: 'jhi-security-permission-update',
  templateUrl: './security-permission-update.component.html',
})
export class SecurityPermissionUpdateComponent implements OnInit {
  isSaving = false;

  securityGroupPermissionsSharedCollection: ISecurityGroupPermission[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    securityGroupPermissionID: [],
  });

  constructor(
    protected securityPermissionService: SecurityPermissionService,
    protected securityGroupPermissionService: SecurityGroupPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => {
      if (securityPermission.id === undefined) {
        const today = dayjs().startOf('day');
        securityPermission.createdOn = today;
        securityPermission.updatedOn = today;
      }

      this.updateForm(securityPermission);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityPermission = this.createFromForm();
    if (securityPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.securityPermissionService.update(securityPermission));
    } else {
      this.subscribeToSaveResponse(this.securityPermissionService.create(securityPermission));
    }
  }

  trackSecurityGroupPermissionById(_index: number, item: ISecurityGroupPermission): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityPermission>>): void {
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

  protected updateForm(securityPermission: ISecurityPermission): void {
    this.editForm.patchValue({
      id: securityPermission.id,
      gUID: securityPermission.gUID,
      description: securityPermission.description,
      createdBy: securityPermission.createdBy,
      createdOn: securityPermission.createdOn ? securityPermission.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: securityPermission.updatedBy,
      updatedOn: securityPermission.updatedOn ? securityPermission.updatedOn.format(DATE_TIME_FORMAT) : null,
      securityGroupPermissionID: securityPermission.securityGroupPermissionID,
    });

    this.securityGroupPermissionsSharedCollection = this.securityGroupPermissionService.addSecurityGroupPermissionToCollectionIfMissing(
      this.securityGroupPermissionsSharedCollection,
      securityPermission.securityGroupPermissionID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityGroupPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityGroupPermission[]>) => res.body ?? []))
      .pipe(
        map((securityGroupPermissions: ISecurityGroupPermission[]) =>
          this.securityGroupPermissionService.addSecurityGroupPermissionToCollectionIfMissing(
            securityGroupPermissions,
            this.editForm.get('securityGroupPermissionID')!.value
          )
        )
      )
      .subscribe(
        (securityGroupPermissions: ISecurityGroupPermission[]) => (this.securityGroupPermissionsSharedCollection = securityGroupPermissions)
      );
  }

  protected createFromForm(): ISecurityPermission {
    return {
      ...new SecurityPermission(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      securityGroupPermissionID: this.editForm.get(['securityGroupPermissionID'])!.value,
    };
  }
}
