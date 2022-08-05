import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlant } from '../plant.model';
import { PlantService } from '../service/plant.service';
import { PlantDeleteDialogComponent } from '../delete/plant-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-plant',
  templateUrl: './plant.component.html',
})
export class PlantComponent implements OnInit {
  plants?: IPlant[];
  isLoading = false;

  constructor(protected plantService: PlantService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.plantService.query().subscribe({
      next: (res: HttpResponse<IPlant[]>) => {
        this.isLoading = false;
        this.plants = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPlant): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(plant: IPlant): void {
    const modalRef = this.modalService.open(PlantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plant = plant;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
