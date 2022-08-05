import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceModel } from '../device-model.model';

@Component({
  selector: 'jhi-device-model-detail',
  templateUrl: './device-model-detail.component.html',
})
export class DeviceModelDetailComponent implements OnInit {
  deviceModel: IDeviceModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      this.deviceModel = deviceModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
