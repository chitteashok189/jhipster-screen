import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScouting } from '../scouting.model';
import { ScoutingService } from '../service/scouting.service';
import { ScoutingDeleteDialogComponent } from '../delete/scouting-delete-dialog.component';

@Component({
  selector: 'jhi-scouting',
  templateUrl: './scouting.component.html',
})
export class ScoutingComponent implements OnInit {
  scoutings?: IScouting[];
  isLoading = false;

  constructor(protected scoutingService: ScoutingService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.scoutingService.query().subscribe({
      next: (res: HttpResponse<IScouting[]>) => {
        this.isLoading = false;
        this.scoutings = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IScouting): number {
    return item.id!;
  }

  delete(scouting: IScouting): void {
    const modalRef = this.modalService.open(ScoutingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scouting = scouting;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
