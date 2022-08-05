import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceModel } from '../device-model.model';
import { DeviceModelService } from '../service/device-model.service';
import { DeviceModelDeleteDialogComponent } from '../delete/device-model-delete-dialog.component';

@Component({
  selector: 'jhi-device-model',
  templateUrl: './device-model.component.html',
})
export class DeviceModelComponent implements OnInit {
  deviceModels?: IDeviceModel[];
  isLoading = false;

  constructor(protected deviceModelService: DeviceModelService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.deviceModelService.query().subscribe({
      next: (res: HttpResponse<IDeviceModel[]>) => {
        this.isLoading = false;
        this.deviceModels = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDeviceModel): number {
    return item.id!;
  }

  delete(deviceModel: IDeviceModel): void {
    const modalRef = this.modalService.open(DeviceModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deviceModel = deviceModel;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
