import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';
import { SymptomDeleteDialogComponent } from '../delete/symptom-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-symptom',
  templateUrl: './symptom.component.html',
})
export class SymptomComponent implements OnInit {
  symptoms?: ISymptom[];
  isLoading = false;

  constructor(protected symptomService: SymptomService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.symptomService.query().subscribe({
      next: (res: HttpResponse<ISymptom[]>) => {
        this.isLoading = false;
        this.symptoms = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISymptom): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(symptom: ISymptom): void {
    const modalRef = this.modalService.open(SymptomDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptom = symptom;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
