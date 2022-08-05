import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISeed } from '../seed.model';
import { SeedService } from '../service/seed.service';
import { SeedDeleteDialogComponent } from '../delete/seed-delete-dialog.component';

@Component({
  selector: 'jhi-seed',
  templateUrl: './seed.component.html',
})
export class SeedComponent implements OnInit {
  seeds?: ISeed[];
  isLoading = false;

  constructor(protected seedService: SeedService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.seedService.query().subscribe({
      next: (res: HttpResponse<ISeed[]>) => {
        this.isLoading = false;
        this.seeds = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISeed): number {
    return item.id!;
  }

  delete(seed: ISeed): void {
    const modalRef = this.modalService.open(SeedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.seed = seed;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
