import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISecurityGroup, SecurityGroup } from '../security-group.model';
import { SecurityGroupService } from '../service/security-group.service';
import { IApplicationUserSecurityGroup } from 'app/entities/application-user-security-group/application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from 'app/entities/application-user-security-group/service/application-user-security-group.service';
import { ISecurityGroupPermission } from 'app/entities/security-group-permission/security-group-permission.model';
import { SecurityGroupPermissionService } from 'app/entities/security-group-permission/service/security-group-permission.service';

@Component({
  selector: 'jhi-security-group-update',
  templateUrl: './security-group-update.component.html',
})
export class SecurityGroupUpdateComponent implements OnInit {
  isSaving = false;

  applicationUserSecurityGroupsSharedCollection: IApplicationUserSecurityGroup[] = [];
  securityGroupPermissionsSharedCollection: ISecurityGroupPermission[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    applicationUserSecurityGroupID: [],
    securityGroupPermissionID: [],
  });

  constructor(
    protected securityGroupService: SecurityGroupService,
    protected applicationUserSecurityGroupService: ApplicationUserSecurityGroupService,
    protected securityGroupPermissionService: SecurityGroupPermissionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityGroup }) => {
      if (securityGroup.id === undefined) {
        const today = dayjs().startOf('day');
        securityGroup.createdOn = today;
        securityGroup.updatedOn = today;
      }

      this.updateForm(securityGroup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityGroup = this.createFromForm();
    if (securityGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.securityGroupService.update(securityGroup));
    } else {
      this.subscribeToSaveResponse(this.securityGroupService.create(securityGroup));
    }
  }

  trackApplicationUserSecurityGroupById(_index: number, item: IApplicationUserSecurityGroup): number {
    return item.id!;
  }

  trackSecurityGroupPermissionById(_index: number, item: ISecurityGroupPermission): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityGroup>>): void {
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

  protected updateForm(securityGroup: ISecurityGroup): void {
    this.editForm.patchValue({
      id: securityGroup.id,
      gUID: securityGroup.gUID,
      description: securityGroup.description,
      createdBy: securityGroup.createdBy,
      createdOn: securityGroup.createdOn ? securityGroup.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: securityGroup.updatedBy,
      updatedOn: securityGroup.updatedOn ? securityGroup.updatedOn.format(DATE_TIME_FORMAT) : null,
      applicationUserSecurityGroupID: securityGroup.applicationUserSecurityGroupID,
      securityGroupPermissionID: securityGroup.securityGroupPermissionID,
    });

    this.applicationUserSecurityGroupsSharedCollection =
      this.applicationUserSecurityGroupService.addApplicationUserSecurityGroupToCollectionIfMissing(
        this.applicationUserSecurityGroupsSharedCollection,
        securityGroup.applicationUserSecurityGroupID
      );
    this.securityGroupPermissionsSharedCollection = this.securityGroupPermissionService.addSecurityGroupPermissionToCollectionIfMissing(
      this.securityGroupPermissionsSharedCollection,
      securityGroup.securityGroupPermissionID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.applicationUserSecurityGroupService
      .query()
      .pipe(map((res: HttpResponse<IApplicationUserSecurityGroup[]>) => res.body ?? []))
      .pipe(
        map((applicationUserSecurityGroups: IApplicationUserSecurityGroup[]) =>
          this.applicationUserSecurityGroupService.addApplicationUserSecurityGroupToCollectionIfMissing(
            applicationUserSecurityGroups,
            this.editForm.get('applicationUserSecurityGroupID')!.value
          )
        )
      )
      .subscribe(
        (applicationUserSecurityGroups: IApplicationUserSecurityGroup[]) =>
          (this.applicationUserSecurityGroupsSharedCollection = applicationUserSecurityGroups)
      );

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

  protected createFromForm(): ISecurityGroup {
    return {
      ...new SecurityGroup(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      applicationUserSecurityGroupID: this.editForm.get(['applicationUserSecurityGroupID'])!.value,
      securityGroupPermissionID: this.editForm.get(['securityGroupPermissionID'])!.value,
    };
  }
}
