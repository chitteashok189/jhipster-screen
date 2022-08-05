import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnnumerationType } from '../ennumeration-type.model';

@Component({
  selector: 'jhi-ennumeration-type-detail',
  templateUrl: './ennumeration-type-detail.component.html',
})
export class EnnumerationTypeDetailComponent implements OnInit {
  ennumerationType: IEnnumerationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ennumerationType }) => {
      this.ennumerationType = ennumerationType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
