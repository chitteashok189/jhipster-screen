import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantFactory } from '../plant-factory.model';
import { PlantFactoryService } from '../service/plant-factory.service';
import { PlantFactoryDeleteDialogComponent } from '../delete/plant-factory-delete-dialog.component';

@Component({
  selector: 'jhi-plant-factory',
  templateUrl: './plant-factory.component.html',
})
export class PlantFactoryComponent implements OnInit {
  plantFactories?: IPlantFactory[];
  isLoading = false;

  constructor(protected plantFactoryService: PlantFactoryService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.plantFactoryService.query().subscribe({
      next: (res: HttpResponse<IPlantFactory[]>) => {
        this.isLoading = false;
        this.plantFactories = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPlantFactory): number {
    return item.id!;
  }

  delete(plantFactory: IPlantFactory): void {
    const modalRef = this.modalService.open(PlantFactoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plantFactory = plantFactory;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
