import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILot } from '../lot.model';
import { LotService } from '../service/lot.service';
import { LotDeleteDialogComponent } from '../delete/lot-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-lot',
  templateUrl: './lot.component.html',
})
export class LotComponent implements OnInit {
  lots?: ILot[];
  isLoading = false;

  constructor(protected lotService: LotService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.lotService.query().subscribe({
      next: (res: HttpResponse<ILot[]>) => {
        this.isLoading = false;
        this.lots = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ILot): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(lot: ILot): void {
    const modalRef = this.modalService.open(LotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lot = lot;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
