import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatus } from '../party-status.model';
import { PartyStatusService } from '../service/party-status.service';
import { PartyStatusDeleteDialogComponent } from '../delete/party-status-delete-dialog.component';

@Component({
  selector: 'jhi-party-status',
  templateUrl: './party-status.component.html',
})
export class PartyStatusComponent implements OnInit {
  partyStatuses?: IPartyStatus[];
  isLoading = false;

  constructor(protected partyStatusService: PartyStatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyStatusService.query().subscribe({
      next: (res: HttpResponse<IPartyStatus[]>) => {
        this.isLoading = false;
        this.partyStatuses = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyStatus): number {
    return item.id!;
  }

  delete(partyStatus: IPartyStatus): void {
    const modalRef = this.modalService.open(PartyStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyStatus = partyStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
