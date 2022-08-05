import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IParty, Party } from '../party.model';
import { PartyService } from '../service/party.service';
import { IPartyRole } from 'app/entities/party-role/party-role.model';
import { PartyRoleService } from 'app/entities/party-role/service/party-role.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { ApplicationUserService } from 'app/entities/application-user/service/application-user.service';
import { IPartyGroup } from 'app/entities/party-group/party-group.model';
import { PartyGroupService } from 'app/entities/party-group/service/party-group.service';
import { IPartyType } from 'app/entities/party-type/party-type.model';
import { PartyTypeService } from 'app/entities/party-type/service/party-type.service';

@Component({
  selector: 'jhi-party-update',
  templateUrl: './party-update.component.html',
})
export class PartyUpdateComponent implements OnInit {
  isSaving = false;

  partyRolesSharedCollection: IPartyRole[] = [];
  peopleSharedCollection: IPerson[] = [];
  applicationUsersSharedCollection: IApplicationUser[] = [];
  partyGroupsSharedCollection: IPartyGroup[] = [];
  partyTypesSharedCollection: IPartyType[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    partyName: [],
    statusID: [],
    description: [],
    externalID: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyGroupID: [],
    partyTypeID: [],
    applicationUserID: [],
    partyRoleID: [],
    personID: [],
  });

  constructor(
    protected partyService: PartyService,
    protected partyRoleService: PartyRoleService,
    protected personService: PersonService,
    protected applicationUserService: ApplicationUserService,
    protected partyGroupService: PartyGroupService,
    protected partyTypeService: PartyTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ party }) => {
      if (party.id === undefined) {
        const today = dayjs().startOf('day');
        party.createdOn = today;
        party.updatedOn = today;
      }

      this.updateForm(party);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const party = this.createFromForm();
    if (party.id !== undefined) {
      this.subscribeToSaveResponse(this.partyService.update(party));
    } else {
      this.subscribeToSaveResponse(this.partyService.create(party));
    }
  }

  trackPartyRoleById(_index: number, item: IPartyRole): number {
    return item.id!;
  }

  trackPersonById(_index: number, item: IPerson): number {
    return item.id!;
  }

  trackApplicationUserById(_index: number, item: IApplicationUser): number {
    return item.id!;
  }

  trackPartyGroupById(_index: number, item: IPartyGroup): number {
    return item.id!;
  }

  trackPartyTypeById(_index: number, item: IPartyType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParty>>): void {
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

  protected updateForm(party: IParty): void {
    this.editForm.patchValue({
      id: party.id,
      gUID: party.gUID,
      partyName: party.partyName,
      statusID: party.statusID,
      description: party.description,
      externalID: party.externalID,
      createdBy: party.createdBy,
      createdOn: party.createdOn ? party.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: party.updatedBy,
      updatedOn: party.updatedOn ? party.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyGroupID: party.partyGroupID,
      partyTypeID: party.partyTypeID,
      applicationUserID: party.applicationUserID,
      partyRoleID: party.partyRoleID,
      personID: party.personID,
    });

    this.partyRolesSharedCollection = this.partyRoleService.addPartyRoleToCollectionIfMissing(
      this.partyRolesSharedCollection,
      party.partyRoleID
    );
    this.peopleSharedCollection = this.personService.addPersonToCollectionIfMissing(this.peopleSharedCollection, party.personID);
    this.applicationUsersSharedCollection = this.applicationUserService.addApplicationUserToCollectionIfMissing(
      this.applicationUsersSharedCollection,
      party.applicationUserID
    );
    this.partyGroupsSharedCollection = this.partyGroupService.addPartyGroupToCollectionIfMissing(
      this.partyGroupsSharedCollection,
      party.partyGroupID
    );
    this.partyTypesSharedCollection = this.partyTypeService.addPartyTypeToCollectionIfMissing(
      this.partyTypesSharedCollection,
      party.partyTypeID
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

    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing(people, this.editForm.get('personID')!.value)))
      .subscribe((people: IPerson[]) => (this.peopleSharedCollection = people));

    this.applicationUserService
      .query()
      .pipe(map((res: HttpResponse<IApplicationUser[]>) => res.body ?? []))
      .pipe(
        map((applicationUsers: IApplicationUser[]) =>
          this.applicationUserService.addApplicationUserToCollectionIfMissing(
            applicationUsers,
            this.editForm.get('applicationUserID')!.value
          )
        )
      )
      .subscribe((applicationUsers: IApplicationUser[]) => (this.applicationUsersSharedCollection = applicationUsers));

    this.partyGroupService
      .query()
      .pipe(map((res: HttpResponse<IPartyGroup[]>) => res.body ?? []))
      .pipe(
        map((partyGroups: IPartyGroup[]) =>
          this.partyGroupService.addPartyGroupToCollectionIfMissing(partyGroups, this.editForm.get('partyGroupID')!.value)
        )
      )
      .subscribe((partyGroups: IPartyGroup[]) => (this.partyGroupsSharedCollection = partyGroups));

    this.partyTypeService
      .query()
      .pipe(map((res: HttpResponse<IPartyType[]>) => res.body ?? []))
      .pipe(
        map((partyTypes: IPartyType[]) =>
          this.partyTypeService.addPartyTypeToCollectionIfMissing(partyTypes, this.editForm.get('partyTypeID')!.value)
        )
      )
      .subscribe((partyTypes: IPartyType[]) => (this.partyTypesSharedCollection = partyTypes));
  }

  protected createFromForm(): IParty {
    return {
      ...new Party(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      partyName: this.editForm.get(['partyName'])!.value,
      statusID: this.editForm.get(['statusID'])!.value,
      description: this.editForm.get(['description'])!.value,
      externalID: this.editForm.get(['externalID'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyGroupID: this.editForm.get(['partyGroupID'])!.value,
      partyTypeID: this.editForm.get(['partyTypeID'])!.value,
      applicationUserID: this.editForm.get(['applicationUserID'])!.value,
      partyRoleID: this.editForm.get(['partyRoleID'])!.value,
      personID: this.editForm.get(['personID'])!.value,
    };
  }
}
