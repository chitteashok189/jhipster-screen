import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFarm } from '../farm.model';
import { FarmService } from '../service/farm.service';
import { FarmDeleteDialogComponent } from '../delete/farm-delete-dialog.component';
@Component({
  selector: 'jhi-farm',
  templateUrl: './farm.component.html',
})
export class FarmComponent implements OnInit {

 
  farms?: IFarm[];
  isLoading = false;

  constructor(protected farmService: FarmService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.farmService.query().subscribe({
      next: (res: HttpResponse<IFarm[]>) => {
        this.isLoading = false;
        this.farms = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
 
    this.loadAll();
  }

  trackId(_index: number, item: IFarm): number {
    return item.id!;
  }

  delete(farm: IFarm): void {
    const modalRef = this.modalService.open(FarmDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.farm = farm;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
