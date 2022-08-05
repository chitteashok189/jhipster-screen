import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILight } from '../light.model';

@Component({
  selector: 'jhi-light-detail',
  templateUrl: './light-detail.component.html',
})
export class LightDetailComponent implements OnInit {
  light: ILight | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ light }) => {
      this.light = light;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
