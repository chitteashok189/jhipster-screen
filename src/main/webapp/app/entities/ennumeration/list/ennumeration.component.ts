import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnnumeration } from '../ennumeration.model';
import { EnnumerationService } from '../service/ennumeration.service';
import { EnnumerationDeleteDialogComponent } from '../delete/ennumeration-delete-dialog.component';

@Component({
  selector: 'jhi-ennumeration',
  templateUrl: './ennumeration.component.html',
})
export class EnnumerationComponent implements OnInit {
  ennumerations?: IEnnumeration[];
  isLoading = false;

  constructor(protected ennumerationService: EnnumerationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ennumerationService.query().subscribe({
      next: (res: HttpResponse<IEnnumeration[]>) => {
        this.isLoading = false;
        this.ennumerations = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IEnnumeration): number {
    return item.id!;
  }

  delete(ennumeration: IEnnumeration): void {
    const modalRef = this.modalService.open(EnnumerationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ennumeration = ennumeration;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
