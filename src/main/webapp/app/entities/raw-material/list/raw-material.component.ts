import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRawMaterial } from '../raw-material.model';
import { RawMaterialService } from '../service/raw-material.service';
import { RawMaterialDeleteDialogComponent } from '../delete/raw-material-delete-dialog.component';

@Component({
  selector: 'jhi-raw-material',
  templateUrl: './raw-material.component.html',
})
export class RawMaterialComponent implements OnInit {
  rawMaterials?: IRawMaterial[];
  isLoading = false;

  constructor(protected rawMaterialService: RawMaterialService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.rawMaterialService.query().subscribe({
      next: (res: HttpResponse<IRawMaterial[]>) => {
        this.isLoading = false;
        this.rawMaterials = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRawMaterial): number {
    return item.id!;
  }

  delete(rawMaterial: IRawMaterial): void {
    const modalRef = this.modalService.open(RawMaterialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rawMaterial = rawMaterial;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
