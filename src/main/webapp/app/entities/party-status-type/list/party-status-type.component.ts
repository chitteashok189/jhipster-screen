import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyStatusType } from '../party-status-type.model';
import { PartyStatusTypeService } from '../service/party-status-type.service';
import { PartyStatusTypeDeleteDialogComponent } from '../delete/party-status-type-delete-dialog.component';

@Component({
  selector: 'jhi-party-status-type',
  templateUrl: './party-status-type.component.html',
})
export class PartyStatusTypeComponent implements OnInit {
  partyStatusTypes?: IPartyStatusType[];
  isLoading = false;

  constructor(protected partyStatusTypeService: PartyStatusTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyStatusTypeService.query().subscribe({
      next: (res: HttpResponse<IPartyStatusType[]>) => {
        this.isLoading = false;
        this.partyStatusTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyStatusType): number {
    return item.id!;
  }

  delete(partyStatusType: IPartyStatusType): void {
    const modalRef = this.modalService.open(PartyStatusTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyStatusType = partyStatusType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
