import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IApplicationUser, ApplicationUser } from '../application-user.model';
import { ApplicationUserService } from '../service/application-user.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

@Component({
  selector: 'jhi-application-user-update',
  templateUrl: './application-user-update.component.html',
})
export class ApplicationUserUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    currentPassword: [],
    passwordHint: [],
    isSystemEnables: [],
    hasLoggedOut: [],
    requirePasswordChange: [],
    lastCurrencyUom: [],
    lastLocale: [],
    lastTimeZone: [],
    disabledDateTime: [],
    successiveFailedLogins: [],
    applicationUserSecurityGroup: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
  });

  constructor(
    protected applicationUserService: ApplicationUserService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationUser }) => {
      if (applicationUser.id === undefined) {
        const today = dayjs().startOf('day');
        applicationUser.disabledDateTime = today;
        applicationUser.createdOn = today;
        applicationUser.updatedOn = today;
      }

      this.updateForm(applicationUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationUser = this.createFromForm();
    if (applicationUser.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationUserService.update(applicationUser));
    } else {
      this.subscribeToSaveResponse(this.applicationUserService.create(applicationUser));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationUser>>): void {
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

  protected updateForm(applicationUser: IApplicationUser): void {
    this.editForm.patchValue({
      id: applicationUser.id,
      gUID: applicationUser.gUID,
      currentPassword: applicationUser.currentPassword,
      passwordHint: applicationUser.passwordHint,
      isSystemEnables: applicationUser.isSystemEnables,
      hasLoggedOut: applicationUser.hasLoggedOut,
      requirePasswordChange: applicationUser.requirePasswordChange,
      lastCurrencyUom: applicationUser.lastCurrencyUom,
      lastLocale: applicationUser.lastLocale,
      lastTimeZone: applicationUser.lastTimeZone,
      disabledDateTime: applicationUser.disabledDateTime ? applicationUser.disabledDateTime.format(DATE_TIME_FORMAT) : null,
      successiveFailedLogins: applicationUser.successiveFailedLogins,
      applicationUserSecurityGroup: applicationUser.applicationUserSecurityGroup,
      createdBy: applicationUser.createdBy,
      createdOn: applicationUser.createdOn ? applicationUser.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: applicationUser.updatedBy,
      updatedOn: applicationUser.updatedOn ? applicationUser.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: applicationUser.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, applicationUser.partyID);
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));
  }

  protected createFromForm(): IApplicationUser {
    return {
      ...new ApplicationUser(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      currentPassword: this.editForm.get(['currentPassword'])!.value,
      passwordHint: this.editForm.get(['passwordHint'])!.value,
      isSystemEnables: this.editForm.get(['isSystemEnables'])!.value,
      hasLoggedOut: this.editForm.get(['hasLoggedOut'])!.value,
      requirePasswordChange: this.editForm.get(['requirePasswordChange'])!.value,
      lastCurrencyUom: this.editForm.get(['lastCurrencyUom'])!.value,
      lastLocale: this.editForm.get(['lastLocale'])!.value,
      lastTimeZone: this.editForm.get(['lastTimeZone'])!.value,
      disabledDateTime: this.editForm.get(['disabledDateTime'])!.value
        ? dayjs(this.editForm.get(['disabledDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      successiveFailedLogins: this.editForm.get(['successiveFailedLogins'])!.value,
      applicationUserSecurityGroup: this.editForm.get(['applicationUserSecurityGroup'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
