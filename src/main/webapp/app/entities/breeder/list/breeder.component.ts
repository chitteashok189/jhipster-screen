import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBreeder } from '../breeder.model';
import { BreederService } from '../service/breeder.service';
import { BreederDeleteDialogComponent } from '../delete/breeder-delete-dialog.component';

@Component({
  selector: 'jhi-breeder',
  templateUrl: './breeder.component.html',
})
export class BreederComponent implements OnInit {
  breeders?: IBreeder[];
  isLoading = false;

  constructor(protected breederService: BreederService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.breederService.query().subscribe({
      next: (res: HttpResponse<IBreeder[]>) => {
        this.isLoading = false;
        this.breeders = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IBreeder): number {
    return item.id!;
  }

  delete(breeder: IBreeder): void {
    const modalRef = this.modalService.open(BreederDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.breeder = breeder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
