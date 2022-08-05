import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClimate } from '../climate.model';

@Component({
  selector: 'jhi-climate-detail',
  templateUrl: './climate-detail.component.html',
})
export class ClimateDetailComponent implements OnInit {
  climate: IClimate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ climate }) => {
      this.climate = climate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
