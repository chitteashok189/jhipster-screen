import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHarvest } from '../harvest.model';
import { HarvestService } from '../service/harvest.service';
import { HarvestDeleteDialogComponent } from '../delete/harvest-delete-dialog.component';

@Component({
  selector: 'jhi-harvest',
  templateUrl: './harvest.component.html',
})
export class HarvestComponent implements OnInit {
  harvests?: IHarvest[];
  isLoading = false;

  constructor(protected harvestService: HarvestService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.harvestService.query().subscribe({
      next: (res: HttpResponse<IHarvest[]>) => {
        this.isLoading = false;
        this.harvests = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IHarvest): number {
    return item.id!;
  }

  delete(harvest: IHarvest): void {
    const modalRef = this.modalService.open(HarvestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.harvest = harvest;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
