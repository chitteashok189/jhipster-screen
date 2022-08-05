import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisease } from '../disease.model';

@Component({
  selector: 'jhi-disease-detail',
  templateUrl: './disease-detail.component.html',
})
export class DiseaseDetailComponent implements OnInit {
  disease: IDisease | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disease }) => {
      this.disease = disease;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
