import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlantFactory } from '../plant-factory.model';

@Component({
  selector: 'jhi-plant-factory-detail',
  templateUrl: './plant-factory-detail.component.html',
})
export class PlantFactoryDetailComponent implements OnInit {
  plantFactory: IPlantFactory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantFactory }) => {
      this.plantFactory = plantFactory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
