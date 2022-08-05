import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceLevel } from '../device-level.model';

@Component({
  selector: 'jhi-device-level-detail',
  templateUrl: './device-level-detail.component.html',
})
export class DeviceLevelDetailComponent implements OnInit {
  deviceLevel: IDeviceLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceLevel }) => {
      this.deviceLevel = deviceLevel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
