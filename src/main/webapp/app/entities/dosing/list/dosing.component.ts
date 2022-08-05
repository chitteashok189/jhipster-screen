import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDosing } from '../dosing.model';
import { DosingService } from '../service/dosing.service';
import { DosingDeleteDialogComponent } from '../delete/dosing-delete-dialog.component';

@Component({
  selector: 'jhi-dosing',
  templateUrl: './dosing.component.html',
})
export class DosingComponent implements OnInit {
  dosings?: IDosing[];
  isLoading = false;

  constructor(protected dosingService: DosingService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dosingService.query().subscribe({
      next: (res: HttpResponse<IDosing[]>) => {
        this.isLoading = false;
        this.dosings = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDosing): number {
    return item.id!;
  }

  delete(dosing: IDosing): void {
    const modalRef = this.modalService.open(DosingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dosing = dosing;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
