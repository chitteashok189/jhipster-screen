import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyNote } from '../party-note.model';

@Component({
  selector: 'jhi-party-note-detail',
  templateUrl: './party-note-detail.component.html',
})
export class PartyNoteDetailComponent implements OnInit {
  partyNote: IPartyNote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyNote }) => {
      this.partyNote = partyNote;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
