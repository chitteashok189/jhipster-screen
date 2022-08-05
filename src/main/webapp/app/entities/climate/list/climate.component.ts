import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClimate } from '../climate.model';
import { ClimateService } from '../service/climate.service';
import { ClimateDeleteDialogComponent } from '../delete/climate-delete-dialog.component';

@Component({
  selector: 'jhi-climate',
  templateUrl: './climate.component.html',
})
export class ClimateComponent implements OnInit {
  climates?: IClimate[];
  isLoading = false;

  constructor(protected climateService: ClimateService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.climateService.query().subscribe({
      next: (res: HttpResponse<IClimate[]>) => {
        this.isLoading = false;
        this.climates = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IClimate): number {
    return item.id!;
  }

  delete(climate: IClimate): void {
    const modalRef = this.modalService.open(ClimateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.climate = climate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
