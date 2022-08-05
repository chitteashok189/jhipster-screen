import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPestControl } from '../pest-control.model';
import { PestControlService } from '../service/pest-control.service';
import { PestControlDeleteDialogComponent } from '../delete/pest-control-delete-dialog.component';

@Component({
  selector: 'jhi-pest-control',
  templateUrl: './pest-control.component.html',
})
export class PestControlComponent implements OnInit {
  pestControls?: IPestControl[];
  isLoading = false;

  constructor(protected pestControlService: PestControlService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pestControlService.query().subscribe({
      next: (res: HttpResponse<IPestControl[]>) => {
        this.isLoading = false;
        this.pestControls = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPestControl): number {
    return item.id!;
  }

  delete(pestControl: IPestControl): void {
    const modalRef = this.modalService.open(PestControlDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pestControl = pestControl;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
