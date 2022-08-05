import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyClassification } from '../party-classification.model';
import { PartyClassificationService } from '../service/party-classification.service';
import { PartyClassificationDeleteDialogComponent } from '../delete/party-classification-delete-dialog.component';

@Component({
  selector: 'jhi-party-classification',
  templateUrl: './party-classification.component.html',
})
export class PartyClassificationComponent implements OnInit {
  partyClassifications?: IPartyClassification[];
  isLoading = false;

  constructor(protected partyClassificationService: PartyClassificationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyClassificationService.query().subscribe({
      next: (res: HttpResponse<IPartyClassification[]>) => {
        this.isLoading = false;
        this.partyClassifications = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyClassification): number {
    return item.id!;
  }

  delete(partyClassification: IPartyClassification): void {
    const modalRef = this.modalService.open(PartyClassificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyClassification = partyClassification;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
