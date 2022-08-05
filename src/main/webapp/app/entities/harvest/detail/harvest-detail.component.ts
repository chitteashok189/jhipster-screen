import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHarvest } from '../harvest.model';

@Component({
  selector: 'jhi-harvest-detail',
  templateUrl: './harvest-detail.component.html',
})
export class HarvestDetailComponent implements OnInit {
  harvest: IHarvest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ harvest }) => {
      this.harvest = harvest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
