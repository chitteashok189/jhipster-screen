import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFarm, Farm } from '../farm.model';
import { FarmService } from '../service/farm.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { FarmType } from 'app/entities/enumerations/farm-type.model';

@Component({
  selector: 'jhi-farm-update',
  templateUrl: './farm-update.component.html',
})
export class FarmUpdateComponent implements OnInit {
  isSaving = false;
  farmTypeValues = Object.keys(FarmType);

  partiesSharedCollection: IParty[] = [];
  locationsSharedCollection: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    farmName: [],
    farmType: [],
    farmDescription: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
    locationID: [],
  });

  constructor(
    protected farmService: FarmService,
    protected partyService: PartyService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ farm }) => {
      if (farm.id === undefined) {
        const today = dayjs().startOf('day');
        farm.createdOn = today;
        farm.updatedOn = today;
      }

      this.updateForm(farm);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const farm = this.createFromForm();
    if (farm.id !== undefined) {
      this.subscribeToSaveResponse(this.farmService.update(farm));
    } else {
      this.subscribeToSaveResponse(this.farmService.create(farm));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  trackLocationById(_index: number, item: ILocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarm>>): void {
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

  protected updateForm(farm: IFarm): void {
    this.editForm.patchValue({
      id: farm.id,
      gUID: farm.gUID,
      farmName: farm.farmName,
      farmType: farm.farmType,
      farmDescription: farm.farmDescription,
      createdBy: farm.createdBy,
      createdOn: farm.createdOn ? farm.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: farm.updatedBy,
      updatedOn: farm.updatedOn ? farm.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: farm.partyID,
      locationID: farm.locationID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, farm.partyID);
    this.locationsSharedCollection = this.locationService.addLocationToCollectionIfMissing(this.locationsSharedCollection, farm.locationID);
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));

    this.locationService
      .query()
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing(locations, this.editForm.get('locationID')!.value)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsSharedCollection = locations));
  }

  protected createFromForm(): IFarm {
    return {
      ...new Farm(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      farmName: this.editForm.get(['farmName'])!.value,
      farmType: this.editForm.get(['farmType'])!.value,
      farmDescription: this.editForm.get(['farmDescription'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
      locationID: this.editForm.get(['locationID'])!.value,
    };
  }
}
