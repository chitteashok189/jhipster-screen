import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceLevel } from '../device-level.model';
import { DeviceLevelService } from '../service/device-level.service';
import { DeviceLevelDeleteDialogComponent } from '../delete/device-level-delete-dialog.component';

@Component({
  selector: 'jhi-device-level',
  templateUrl: './device-level.component.html',
})
export class DeviceLevelComponent implements OnInit {
  deviceLevels?: IDeviceLevel[];
  isLoading = false;

  constructor(protected deviceLevelService: DeviceLevelService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.deviceLevelService.query().subscribe({
      next: (res: HttpResponse<IDeviceLevel[]>) => {
        this.isLoading = false;
        this.deviceLevels = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDeviceLevel): number {
    return item.id!;
  }

  delete(deviceLevel: IDeviceLevel): void {
    const modalRef = this.modalService.open(DeviceLevelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deviceLevel = deviceLevel;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
