import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INutrient } from '../nutrient.model';

@Component({
  selector: 'jhi-nutrient-detail',
  templateUrl: './nutrient-detail.component.html',
})
export class NutrientDetailComponent implements OnInit {
  nutrient: INutrient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nutrient }) => {
      this.nutrient = nutrient;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
