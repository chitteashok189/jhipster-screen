import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatusItem } from '../party-status-item.model';
import { PartyStatusItemService } from '../service/party-status-item.service';
import { PartyStatusItemDeleteDialogComponent } from '../delete/party-status-item-delete-dialog.component';

@Component({
  selector: 'jhi-party-status-item',
  templateUrl: './party-status-item.component.html',
})
export class PartyStatusItemComponent implements OnInit {
  partyStatusItems?: IPartyStatusItem[];
  isLoading = false;

  constructor(protected partyStatusItemService: PartyStatusItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyStatusItemService.query().subscribe({
      next: (res: HttpResponse<IPartyStatusItem[]>) => {
        this.isLoading = false;
        this.partyStatusItems = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyStatusItem): number {
    return item.id!;
  }

  delete(partyStatusItem: IPartyStatusItem): void {
    const modalRef = this.modalService.open(PartyStatusItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyStatusItem = partyStatusItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
