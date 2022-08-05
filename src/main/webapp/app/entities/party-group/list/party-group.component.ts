import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyGroup } from '../party-group.model';
import { PartyGroupService } from '../service/party-group.service';
import { PartyGroupDeleteDialogComponent } from '../delete/party-group-delete-dialog.component';

@Component({
  selector: 'jhi-party-group',
  templateUrl: './party-group.component.html',
})
export class PartyGroupComponent implements OnInit {
  partyGroups?: IPartyGroup[];
  isLoading = false;

  constructor(protected partyGroupService: PartyGroupService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyGroupService.query().subscribe({
      next: (res: HttpResponse<IPartyGroup[]>) => {
        this.isLoading = false;
        this.partyGroups = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyGroup): number {
    return item.id!;
  }

  delete(partyGroup: IPartyGroup): void {
    const modalRef = this.modalService.open(PartyGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyGroup = partyGroup;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
