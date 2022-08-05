import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRelationship } from '../party-relationship.model';
import { PartyRelationshipService } from '../service/party-relationship.service';
import { PartyRelationshipDeleteDialogComponent } from '../delete/party-relationship-delete-dialog.component';

@Component({
  selector: 'jhi-party-relationship',
  templateUrl: './party-relationship.component.html',
})
export class PartyRelationshipComponent implements OnInit {
  partyRelationships?: IPartyRelationship[];
  isLoading = false;

  constructor(protected partyRelationshipService: PartyRelationshipService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyRelationshipService.query().subscribe({
      next: (res: HttpResponse<IPartyRelationship[]>) => {
        this.isLoading = false;
        this.partyRelationships = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyRelationship): number {
    return item.id!;
  }

  delete(partyRelationship: IPartyRelationship): void {
    const modalRef = this.modalService.open(PartyRelationshipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyRelationship = partyRelationship;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
