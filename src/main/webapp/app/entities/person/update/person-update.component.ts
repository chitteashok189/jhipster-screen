import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPerson, Person } from '../person.model';
import { PersonService } from '../service/person.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html',
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    salutation: [],
    firstName: [],
    middleName: [],
    lastName: [],
    personalTitle: [],
    suffix: [],
    nickName: [],
    firstNameLocal: [],
    middleNameLocal: [],
    lastNameLocal: [],
    otherLocal: [],
    gender: [],
    birthDate: [],
    heigth: [],
    weight: [],
    mothersMaidenName: [],
    maritialStatus: [],
    socialSecurityNo: [],
    passportNo: [],
    passportExpiryDate: [],
    totalYearsWorkExperience: [],
    comments: [],
    occupation: [],
    yearswithEmployer: [],
    monthsWithEmployer: [],
    existingCustomer: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
  });

  constructor(
    protected personService: PersonService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      if (person.id === undefined) {
        const today = dayjs().startOf('day');
        person.birthDate = today;
        person.createdOn = today;
        person.updatedOn = today;
      }

      this.updateForm(person);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  protected updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      gUID: person.gUID,
      salutation: person.salutation,
      firstName: person.firstName,
      middleName: person.middleName,
      lastName: person.lastName,
      personalTitle: person.personalTitle,
      suffix: person.suffix,
      nickName: person.nickName,
      firstNameLocal: person.firstNameLocal,
      middleNameLocal: person.middleNameLocal,
      lastNameLocal: person.lastNameLocal,
      otherLocal: person.otherLocal,
      gender: person.gender,
      birthDate: person.birthDate ? person.birthDate.format(DATE_TIME_FORMAT) : null,
      heigth: person.heigth,
      weight: person.weight,
      mothersMaidenName: person.mothersMaidenName,
      maritialStatus: person.maritialStatus,
      socialSecurityNo: person.socialSecurityNo,
      passportNo: person.passportNo,
      passportExpiryDate: person.passportExpiryDate,
      totalYearsWorkExperience: person.totalYearsWorkExperience,
      comments: person.comments,
      occupation: person.occupation,
      yearswithEmployer: person.yearswithEmployer,
      monthsWithEmployer: person.monthsWithEmployer,
      existingCustomer: person.existingCustomer,
      createdBy: person.createdBy,
      createdOn: person.createdOn ? person.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: person.updatedBy,
      updatedOn: person.updatedOn ? person.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: person.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, person.partyID);
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));
  }

  protected createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      salutation: this.editForm.get(['salutation'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      personalTitle: this.editForm.get(['personalTitle'])!.value,
      suffix: this.editForm.get(['suffix'])!.value,
      nickName: this.editForm.get(['nickName'])!.value,
      firstNameLocal: this.editForm.get(['firstNameLocal'])!.value,
      middleNameLocal: this.editForm.get(['middleNameLocal'])!.value,
      lastNameLocal: this.editForm.get(['lastNameLocal'])!.value,
      otherLocal: this.editForm.get(['otherLocal'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? dayjs(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      heigth: this.editForm.get(['heigth'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      mothersMaidenName: this.editForm.get(['mothersMaidenName'])!.value,
      maritialStatus: this.editForm.get(['maritialStatus'])!.value,
      socialSecurityNo: this.editForm.get(['socialSecurityNo'])!.value,
      passportNo: this.editForm.get(['passportNo'])!.value,
      passportExpiryDate: this.editForm.get(['passportExpiryDate'])!.value,
      totalYearsWorkExperience: this.editForm.get(['totalYearsWorkExperience'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      occupation: this.editForm.get(['occupation'])!.value,
      yearswithEmployer: this.editForm.get(['yearswithEmployer'])!.value,
      monthsWithEmployer: this.editForm.get(['monthsWithEmployer'])!.value,
      existingCustomer: this.editForm.get(['existingCustomer'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
