import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPartyNote, PartyNote } from '../party-note.model';
import { PartyNoteService } from '../service/party-note.service';
import { IParty } from 'app/entities/party/party.model';
import { PartyService } from 'app/entities/party/service/party.service';

@Component({
  selector: 'jhi-party-note-update',
  templateUrl: './party-note-update.component.html',
})
export class PartyNoteUpdateComponent implements OnInit {
  isSaving = false;

  partiesSharedCollection: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    noteID: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    partyID: [],
  });

  constructor(
    protected partyNoteService: PartyNoteService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyNote }) => {
      if (partyNote.id === undefined) {
        const today = dayjs().startOf('day');
        partyNote.createdOn = today;
        partyNote.updatedOn = today;
      }

      this.updateForm(partyNote);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyNote = this.createFromForm();
    if (partyNote.id !== undefined) {
      this.subscribeToSaveResponse(this.partyNoteService.update(partyNote));
    } else {
      this.subscribeToSaveResponse(this.partyNoteService.create(partyNote));
    }
  }

  trackPartyById(_index: number, item: IParty): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyNote>>): void {
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

  protected updateForm(partyNote: IPartyNote): void {
    this.editForm.patchValue({
      id: partyNote.id,
      gUID: partyNote.gUID,
      noteID: partyNote.noteID,
      createdBy: partyNote.createdBy,
      createdOn: partyNote.createdOn ? partyNote.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: partyNote.updatedBy,
      updatedOn: partyNote.updatedOn ? partyNote.updatedOn.format(DATE_TIME_FORMAT) : null,
      partyID: partyNote.partyID,
    });

    this.partiesSharedCollection = this.partyService.addPartyToCollectionIfMissing(this.partiesSharedCollection, partyNote.partyID);
  }

  protected loadRelationshipsOptions(): void {
    this.partyService
      .query()
      .pipe(map((res: HttpResponse<IParty[]>) => res.body ?? []))
      .pipe(map((parties: IParty[]) => this.partyService.addPartyToCollectionIfMissing(parties, this.editForm.get('partyID')!.value)))
      .subscribe((parties: IParty[]) => (this.partiesSharedCollection = parties));
  }

  protected createFromForm(): IPartyNote {
    return {
      ...new PartyNote(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      noteID: this.editForm.get(['noteID'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      partyID: this.editForm.get(['partyID'])!.value,
    };
  }
}
