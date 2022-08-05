import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPest } from '../pest.model';
import { PestService } from '../service/pest.service';
import { PestDeleteDialogComponent } from '../delete/pest-delete-dialog.component';

@Component({
  selector: 'jhi-pest',
  templateUrl: './pest.component.html',
})
export class PestComponent implements OnInit {
  pests?: IPest[];
  isLoading = false;

  constructor(protected pestService: PestService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pestService.query().subscribe({
      next: (res: HttpResponse<IPest[]>) => {
        this.isLoading = false;
        this.pests = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPest): number {
    return item.id!;
  }

  delete(pest: IPest): void {
    const modalRef = this.modalService.open(PestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pest = pest;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
