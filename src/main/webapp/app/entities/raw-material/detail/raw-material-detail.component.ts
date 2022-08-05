import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRawMaterial } from '../raw-material.model';

@Component({
  selector: 'jhi-raw-material-detail',
  templateUrl: './raw-material-detail.component.html',
})
export class RawMaterialDetailComponent implements OnInit {
  rawMaterial: IRawMaterial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterial }) => {
      this.rawMaterial = rawMaterial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
