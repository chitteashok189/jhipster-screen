import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiseaseControl } from '../disease-control.model';

@Component({
  selector: 'jhi-disease-control-detail',
  templateUrl: './disease-control-detail.component.html',
})
export class DiseaseControlDetailComponent implements OnInit {
  diseaseControl: IDiseaseControl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diseaseControl }) => {
      this.diseaseControl = diseaseControl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
