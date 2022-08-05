import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPestControl } from '../pest-control.model';

@Component({
  selector: 'jhi-pest-control-detail',
  templateUrl: './pest-control-detail.component.html',
})
export class PestControlDetailComponent implements OnInit {
  pestControl: IPestControl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pestControl }) => {
      this.pestControl = pestControl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
