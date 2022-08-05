import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILight } from '../light.model';
import { LightService } from '../service/light.service';
import { LightDeleteDialogComponent } from '../delete/light-delete-dialog.component';

@Component({
  selector: 'jhi-light',
  templateUrl: './light.component.html',
})
export class LightComponent implements OnInit {
  lights?: ILight[];
  isLoading = false;

  constructor(protected lightService: LightService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.lightService.query().subscribe({
      next: (res: HttpResponse<ILight[]>) => {
        this.isLoading = false;
        this.lights = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ILight): number {
    return item.id!;
  }

  delete(light: ILight): void {
    const modalRef = this.modalService.open(LightDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.light = light;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
