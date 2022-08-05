import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIrrigation } from '../irrigation.model';

@Component({
  selector: 'jhi-irrigation-detail',
  templateUrl: './irrigation-detail.component.html',
})
export class IrrigationDetailComponent implements OnInit {
  irrigation: IIrrigation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ irrigation }) => {
      this.irrigation = irrigation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
