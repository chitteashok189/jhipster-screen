import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisorder } from '../disorder.model';
import { DisorderService } from '../service/disorder.service';
import { DisorderDeleteDialogComponent } from '../delete/disorder-delete-dialog.component';

@Component({
  selector: 'jhi-disorder',
  templateUrl: './disorder.component.html',
})
export class DisorderComponent implements OnInit {
  disorders?: IDisorder[];
  isLoading = false;

  constructor(protected disorderService: DisorderService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.disorderService.query().subscribe({
      next: (res: HttpResponse<IDisorder[]>) => {
        this.isLoading = false;
        this.disorders = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDisorder): number {
    return item.id!;
  }

  delete(disorder: IDisorder): void {
    const modalRef = this.modalService.open(DisorderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.disorder = disorder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
