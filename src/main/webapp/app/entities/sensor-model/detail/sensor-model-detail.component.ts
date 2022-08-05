import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISensorModel } from '../sensor-model.model';

@Component({
  selector: 'jhi-sensor-model-detail',
  templateUrl: './sensor-model-detail.component.html',
})
export class SensorModelDetailComponent implements OnInit {
  sensorModel: ISensorModel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensorModel }) => {
      this.sensorModel = sensorModel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
