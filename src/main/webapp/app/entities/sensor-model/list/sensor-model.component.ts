import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISensorModel } from '../sensor-model.model';
import { SensorModelService } from '../service/sensor-model.service';
import { SensorModelDeleteDialogComponent } from '../delete/sensor-model-delete-dialog.component';

@Component({
  selector: 'jhi-sensor-model',
  templateUrl: './sensor-model.component.html',
})
export class SensorModelComponent implements OnInit {
  sensorModels?: ISensorModel[];
  isLoading = false;

  constructor(protected sensorModelService: SensorModelService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.sensorModelService.query().subscribe({
      next: (res: HttpResponse<ISensorModel[]>) => {
        this.isLoading = false;
        this.sensorModels = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISensorModel): number {
    return item.id!;
  }

  delete(sensorModel: ISensorModel): void {
    const modalRef = this.modalService.open(SensorModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sensorModel = sensorModel;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
