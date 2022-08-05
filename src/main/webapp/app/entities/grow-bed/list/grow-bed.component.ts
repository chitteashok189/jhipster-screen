import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrowBed } from '../grow-bed.model';
import { GrowBedService } from '../service/grow-bed.service';
import { GrowBedDeleteDialogComponent } from '../delete/grow-bed-delete-dialog.component';

@Component({
  selector: 'jhi-grow-bed',
  templateUrl: './grow-bed.component.html',
})
export class GrowBedComponent implements OnInit {
  growBeds?: IGrowBed[];
  isLoading = false;

  constructor(protected growBedService: GrowBedService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.growBedService.query().subscribe({
      next: (res: HttpResponse<IGrowBed[]>) => {
        this.isLoading = false;
        this.growBeds = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IGrowBed): number {
    return item.id!;
  }

  delete(growBed: IGrowBed): void {
    const modalRef = this.modalService.open(GrowBedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.growBed = growBed;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
