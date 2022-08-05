import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyNote } from '../party-note.model';
import { PartyNoteService } from '../service/party-note.service';
import { PartyNoteDeleteDialogComponent } from '../delete/party-note-delete-dialog.component';

@Component({
  selector: 'jhi-party-note',
  templateUrl: './party-note.component.html',
})
export class PartyNoteComponent implements OnInit {
  partyNotes?: IPartyNote[];
  isLoading = false;

  constructor(protected partyNoteService: PartyNoteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyNoteService.query().subscribe({
      next: (res: HttpResponse<IPartyNote[]>) => {
        this.isLoading = false;
        this.partyNotes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyNote): number {
    return item.id!;
  }

  delete(partyNote: IPartyNote): void {
    const modalRef = this.modalService.open(PartyNoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyNote = partyNote;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
