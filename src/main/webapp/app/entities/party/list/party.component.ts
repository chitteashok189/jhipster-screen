import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParty } from '../party.model';
import { PartyService } from '../service/party.service';
import { PartyDeleteDialogComponent } from '../delete/party-delete-dialog.component';

@Component({
  selector: 'jhi-party',
  templateUrl: './party.component.html',
})
export class PartyComponent implements OnInit {
  parties?: IParty[];
  isLoading = false;

  constructor(protected partyService: PartyService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyService.query().subscribe({
      next: (res: HttpResponse<IParty[]>) => {
        this.isLoading = false;
        this.parties = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IParty): number {
    return item.id!;
  }

  delete(party: IParty): void {
    const modalRef = this.modalService.open(PartyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.party = party;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
