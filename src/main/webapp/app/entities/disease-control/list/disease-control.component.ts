import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiseaseControl } from '../disease-control.model';
import { DiseaseControlService } from '../service/disease-control.service';
import { DiseaseControlDeleteDialogComponent } from '../delete/disease-control-delete-dialog.component';

@Component({
  selector: 'jhi-disease-control',
  templateUrl: './disease-control.component.html',
})
export class DiseaseControlComponent implements OnInit {
  diseaseControls?: IDiseaseControl[];
  isLoading = false;

  constructor(protected diseaseControlService: DiseaseControlService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.diseaseControlService.query().subscribe({
      next: (res: HttpResponse<IDiseaseControl[]>) => {
        this.isLoading = false;
        this.diseaseControls = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDiseaseControl): number {
    return item.id!;
  }

  delete(diseaseControl: IDiseaseControl): void {
    const modalRef = this.modalService.open(DiseaseControlDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.diseaseControl = diseaseControl;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
