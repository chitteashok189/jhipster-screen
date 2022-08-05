import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISecurityGroupPermission, SecurityGroupPermission } from '../security-group-permission.model';
import { SecurityGroupPermissionService } from '../service/security-group-permission.service';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';

@Component({
  selector: 'jhi-security-group-permission-update',
  templateUrl: './security-group-permission-update.component.html',
})
export class SecurityGroupPermissionUpdateComponent implements OnInit {
  isSaving = false;

  securityGroupsSharedCollection: ISecurityGroup[] = [];
  securityPermissionsSharedCollection: ISecurityPermission[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    securityGroupID: [],
    securityPermissionID: [],
  });

  constructor(
    protected securityGroupPermissionService: SecurityGroupPermissionService,
    protected securityGroupService: SecurityGroupService,
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityGroupPermission }) => {
      if (securityGroupPermission.id === undefined) {
        const today = dayjs().startOf('day');
        securityGroupPermission.createdOn = today;
        securityGroupPermission.updatedOn = today;
      }

      this.updateForm(securityGroupPermission);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityGroupPermission = this.createFromForm();
    if (securityGroupPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.securityGroupPermissionService.update(securityGroupPermission));
    } else {
      this.subscribeToSaveResponse(this.securityGroupPermissionService.create(securityGroupPermission));
    }
  }

  trackSecurityGroupById(_index: number, item: ISecurityGroup): number {
    return item.id!;
  }

  trackSecurityPermissionById(_index: number, item: ISecurityPermission): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityGroupPermission>>): void {
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

  protected updateForm(securityGroupPermission: ISecurityGroupPermission): void {
    this.editForm.patchValue({
      id: securityGroupPermission.id,
      gUID: securityGroupPermission.gUID,
      createdBy: securityGroupPermission.createdBy,
      createdOn: securityGroupPermission.createdOn ? securityGroupPermission.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: securityGroupPermission.updatedBy,
      updatedOn: securityGroupPermission.updatedOn ? securityGroupPermission.updatedOn.format(DATE_TIME_FORMAT) : null,
      securityGroupID: securityGroupPermission.securityGroupID,
      securityPermissionID: securityGroupPermission.securityPermissionID,
    });

    this.securityGroupsSharedCollection = this.securityGroupService.addSecurityGroupToCollectionIfMissing(
      this.securityGroupsSharedCollection,
      securityGroupPermission.securityGroupID
    );
    this.securityPermissionsSharedCollection = this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
      this.securityPermissionsSharedCollection,
      securityGroupPermission.securityPermissionID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityGroupService
      .query()
      .pipe(map((res: HttpResponse<ISecurityGroup[]>) => res.body ?? []))
      .pipe(
        map((securityGroups: ISecurityGroup[]) =>
          this.securityGroupService.addSecurityGroupToCollectionIfMissing(securityGroups, this.editForm.get('securityGroupID')!.value)
        )
      )
      .subscribe((securityGroups: ISecurityGroup[]) => (this.securityGroupsSharedCollection = securityGroups));

    this.securityPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityPermission[]>) => res.body ?? []))
      .pipe(
        map((securityPermissions: ISecurityPermission[]) =>
          this.securityPermissionService.addSecurityPermissionToCollectionIfMissing(
            securityPermissions,
            this.editForm.get('securityPermissionID')!.value
          )
        )
      )
      .subscribe((securityPermissions: ISecurityPermission[]) => (this.securityPermissionsSharedCollection = securityPermissions));
  }

  protected createFromForm(): ISecurityGroupPermission {
    return {
      ...new SecurityGroupPermission(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      securityGroupID: this.editForm.get(['securityGroupID'])!.value,
      securityPermissionID: this.editForm.get(['securityPermissionID'])!.value,
    };
  }
}
