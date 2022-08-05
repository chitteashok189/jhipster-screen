import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIrrigation } from '../irrigation.model';
import { IrrigationService } from '../service/irrigation.service';
import { IrrigationDeleteDialogComponent } from '../delete/irrigation-delete-dialog.component';

@Component({
  selector: 'jhi-irrigation',
  templateUrl: './irrigation.component.html',
})
export class IrrigationComponent implements OnInit {
  irrigations?: IIrrigation[];
  isLoading = false;

  constructor(protected irrigationService: IrrigationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.irrigationService.query().subscribe({
      next: (res: HttpResponse<IIrrigation[]>) => {
        this.isLoading = false;
        this.irrigations = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IIrrigation): number {
    return item.id!;
  }

  delete(irrigation: IIrrigation): void {
    const modalRef = this.modalService.open(IrrigationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.irrigation = irrigation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
