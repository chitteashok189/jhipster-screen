import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnnumerationType } from '../ennumeration-type.model';
import { EnnumerationTypeService } from '../service/ennumeration-type.service';
import { EnnumerationTypeDeleteDialogComponent } from '../delete/ennumeration-type-delete-dialog.component';

@Component({
  selector: 'jhi-ennumeration-type',
  templateUrl: './ennumeration-type.component.html',
})
export class EnnumerationTypeComponent implements OnInit {
  ennumerationTypes?: IEnnumerationType[];
  isLoading = false;

  constructor(protected ennumerationTypeService: EnnumerationTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ennumerationTypeService.query().subscribe({
      next: (res: HttpResponse<IEnnumerationType[]>) => {
        this.isLoading = false;
        this.ennumerationTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IEnnumerationType): number {
    return item.id!;
  }

  delete(ennumerationType: IEnnumerationType): void {
    const modalRef = this.modalService.open(EnnumerationTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ennumerationType = ennumerationType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
