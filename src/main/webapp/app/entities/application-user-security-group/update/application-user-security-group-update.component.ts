import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IApplicationUserSecurityGroup, ApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';
import { ISecurityGroup } from 'app/entities/security-group/security-group.model';
import { SecurityGroupService } from 'app/entities/security-group/service/security-group.service';

@Component({
  selector: 'jhi-application-user-security-group-update',
  templateUrl: './application-user-security-group-update.component.html',
})
export class ApplicationUserSecurityGroupUpdateComponent implements OnInit {
  isSaving = false;

  securityGroupsSharedCollection: ISecurityGroup[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    fromDate: [],
    thruDate: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    securityGroupID: [],
  });

  constructor(
    protected applicationUserSecurityGroupService: ApplicationUserSecurityGroupService,
    protected securityGroupService: SecurityGroupService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationUserSecurityGroup }) => {
      if (applicationUserSecurityGroup.id === undefined) {
        const today = dayjs().startOf('day');
        applicationUserSecurityGroup.fromDate = today;
        applicationUserSecurityGroup.thruDate = today;
        applicationUserSecurityGroup.createdOn = today;
        applicationUserSecurityGroup.updatedOn = today;
      }

      this.updateForm(applicationUserSecurityGroup);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationUserSecurityGroup = this.createFromForm();
    if (applicationUserSecurityGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationUserSecurityGroupService.update(applicationUserSecurityGroup));
    } else {
      this.subscribeToSaveResponse(this.applicationUserSecurityGroupService.create(applicationUserSecurityGroup));
    }
  }

  trackSecurityGroupById(_index: number, item: ISecurityGroup): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationUserSecurityGroup>>): void {
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

  protected updateForm(applicationUserSecurityGroup: IApplicationUserSecurityGroup): void {
    this.editForm.patchValue({
      id: applicationUserSecurityGroup.id,
      gUID: applicationUserSecurityGroup.gUID,
      fromDate: applicationUserSecurityGroup.fromDate ? applicationUserSecurityGroup.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: applicationUserSecurityGroup.thruDate ? applicationUserSecurityGroup.thruDate.format(DATE_TIME_FORMAT) : null,
      createdBy: applicationUserSecurityGroup.createdBy,
      createdOn: applicationUserSecurityGroup.createdOn ? applicationUserSecurityGroup.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: applicationUserSecurityGroup.updatedBy,
      updatedOn: applicationUserSecurityGroup.updatedOn ? applicationUserSecurityGroup.updatedOn.format(DATE_TIME_FORMAT) : null,
      securityGroupID: applicationUserSecurityGroup.securityGroupID,
    });

    this.securityGroupsSharedCollection = this.securityGroupService.addSecurityGroupToCollectionIfMissing(
      this.securityGroupsSharedCollection,
      applicationUserSecurityGroup.securityGroupID
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
  }

  protected createFromForm(): IApplicationUserSecurityGroup {
    return {
      ...new ApplicationUserSecurityGroup(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? dayjs(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? dayjs(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      securityGroupID: this.editForm.get(['securityGroupID'])!.value,
    };
  }
}
