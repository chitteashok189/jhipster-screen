import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlantFactoryController } from '../plant-factory-controller.model';

@Component({
  selector: 'jhi-plant-factory-controller-detail',
  templateUrl: './plant-factory-controller-detail.component.html',
})
export class PlantFactoryControllerDetailComponent implements OnInit {
  plantFactoryController: IPlantFactoryController | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantFactoryController }) => {
      this.plantFactoryController = plantFactoryController;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
