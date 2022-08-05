import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeed } from '../seed.model';

@Component({
  selector: 'jhi-seed-detail',
  templateUrl: './seed-detail.component.html',
})
export class SeedDetailComponent implements OnInit {
  seed: ISeed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seed }) => {
      this.seed = seed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
