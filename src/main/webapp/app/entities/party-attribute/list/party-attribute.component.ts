import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyAttribute } from '../party-attribute.model';
import { PartyAttributeService } from '../service/party-attribute.service';
import { PartyAttributeDeleteDialogComponent } from '../delete/party-attribute-delete-dialog.component';

@Component({
  selector: 'jhi-party-attribute',
  templateUrl: './party-attribute.component.html',
})
export class PartyAttributeComponent implements OnInit {
  partyAttributes?: IPartyAttribute[];
  isLoading = false;

  constructor(protected partyAttributeService: PartyAttributeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyAttributeService.query().subscribe({
      next: (res: HttpResponse<IPartyAttribute[]>) => {
        this.isLoading = false;
        this.partyAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyAttribute): number {
    return item.id!;
  }

  delete(partyAttribute: IPartyAttribute): void {
    const modalRef = this.modalService.open(PartyAttributeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyAttribute = partyAttribute;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
