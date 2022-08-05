import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INutrient } from '../nutrient.model';
import { NutrientService } from '../service/nutrient.service';
import { NutrientDeleteDialogComponent } from '../delete/nutrient-delete-dialog.component';

@Component({
  selector: 'jhi-nutrient',
  templateUrl: './nutrient.component.html',
})
export class NutrientComponent implements OnInit {
  nutrients?: INutrient[];
  isLoading = false;

  constructor(protected nutrientService: NutrientService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nutrientService.query().subscribe({
      next: (res: HttpResponse<INutrient[]>) => {
        this.isLoading = false;
        this.nutrients = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: INutrient): number {
    return item.id!;
  }

  delete(nutrient: INutrient): void {
    const modalRef = this.modalService.open(NutrientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nutrient = nutrient;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
