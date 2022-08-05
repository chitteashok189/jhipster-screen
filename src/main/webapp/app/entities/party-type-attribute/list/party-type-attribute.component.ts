import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyTypeAttribute } from '../party-type-attribute.model';
import { PartyTypeAttributeService } from '../service/party-type-attribute.service';
import { PartyTypeAttributeDeleteDialogComponent } from '../delete/party-type-attribute-delete-dialog.component';

@Component({
  selector: 'jhi-party-type-attribute',
  templateUrl: './party-type-attribute.component.html',
})
export class PartyTypeAttributeComponent implements OnInit {
  partyTypeAttributes?: IPartyTypeAttribute[];
  isLoading = false;

  constructor(protected partyTypeAttributeService: PartyTypeAttributeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyTypeAttributeService.query().subscribe({
      next: (res: HttpResponse<IPartyTypeAttribute[]>) => {
        this.isLoading = false;
        this.partyTypeAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyTypeAttribute): number {
    return item.id!;
  }

  delete(partyTypeAttribute: IPartyTypeAttribute): void {
    const modalRef = this.modalService.open(PartyTypeAttributeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyTypeAttribute = partyTypeAttribute;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
