import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRelationshipType } from '../party-relationship-type.model';
import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';
import { PartyRelationshipTypeDeleteDialogComponent } from '../delete/party-relationship-type-delete-dialog.component';

@Component({
  selector: 'jhi-party-relationship-type',
  templateUrl: './party-relationship-type.component.html',
})
export class PartyRelationshipTypeComponent implements OnInit {
  partyRelationshipTypes?: IPartyRelationshipType[];
  isLoading = false;

  constructor(protected partyRelationshipTypeService: PartyRelationshipTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyRelationshipTypeService.query().subscribe({
      next: (res: HttpResponse<IPartyRelationshipType[]>) => {
        this.isLoading = false;
        this.partyRelationshipTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyRelationshipType): number {
    return item.id!;
  }

  delete(partyRelationshipType: IPartyRelationshipType): void {
    const modalRef = this.modalService.open(PartyRelationshipTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyRelationshipType = partyRelationshipType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
