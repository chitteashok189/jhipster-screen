import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantFactoryController } from '../plant-factory-controller.model';
import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';
import { PlantFactoryControllerDeleteDialogComponent } from '../delete/plant-factory-controller-delete-dialog.component';

@Component({
  selector: 'jhi-plant-factory-controller',
  templateUrl: './plant-factory-controller.component.html',
})
export class PlantFactoryControllerComponent implements OnInit {
  plantFactoryControllers?: IPlantFactoryController[];
  isLoading = false;

  constructor(protected plantFactoryControllerService: PlantFactoryControllerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.plantFactoryControllerService.query().subscribe({
      next: (res: HttpResponse<IPlantFactoryController[]>) => {
        this.isLoading = false;
        this.plantFactoryControllers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPlantFactoryController): number {
    return item.id!;
  }

  delete(plantFactoryController: IPlantFactoryController): void {
    const modalRef = this.modalService.open(PlantFactoryControllerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plantFactoryController = plantFactoryController;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
