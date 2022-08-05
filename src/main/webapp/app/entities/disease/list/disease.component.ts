import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisease } from '../disease.model';
import { DiseaseService } from '../service/disease.service';
import { DiseaseDeleteDialogComponent } from '../delete/disease-delete-dialog.component';

@Component({
  selector: 'jhi-disease',
  templateUrl: './disease.component.html',
})
export class DiseaseComponent implements OnInit {
  diseases?: IDisease[];
  isLoading = false;

  constructor(protected diseaseService: DiseaseService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.diseaseService.query().subscribe({
      next: (res: HttpResponse<IDisease[]>) => {
        this.isLoading = false;
        this.diseases = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDisease): number {
    return item.id!;
  }

  delete(disease: IDisease): void {
    const modalRef = this.modalService.open(DiseaseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.disease = disease;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
