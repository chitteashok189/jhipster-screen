import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrop } from '../crop.model';
import { CropService } from '../service/crop.service';
import { CropDeleteDialogComponent } from '../delete/crop-delete-dialog.component';

@Component({
  selector: 'jhi-crop',
  templateUrl: './crop.component.html',
})
export class CropComponent implements OnInit {
  crops?: ICrop[];
  isLoading = false;

  constructor(protected cropService: CropService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cropService.query().subscribe({
      next: (res: HttpResponse<ICrop[]>) => {
        this.isLoading = false;
        this.crops = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ICrop): number {
    return item.id!;
  }

  delete(crop: ICrop): void {
    const modalRef = this.modalService.open(CropDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.crop = crop;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
