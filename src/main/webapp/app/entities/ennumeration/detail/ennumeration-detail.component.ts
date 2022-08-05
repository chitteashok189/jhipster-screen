import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnnumeration } from '../ennumeration.model';

@Component({
  selector: 'jhi-ennumeration-detail',
  templateUrl: './ennumeration-detail.component.html',
})
export class EnnumerationDetailComponent implements OnInit {
  ennumeration: IEnnumeration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ennumeration }) => {
      this.ennumeration = ennumeration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
