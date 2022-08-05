import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyClassification } from '../party-classification.model';

@Component({
  selector: 'jhi-party-classification-detail',
  templateUrl: './party-classification-detail.component.html',
})
export class PartyClassificationDetailComponent implements OnInit {
  partyClassification: IPartyClassification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassification }) => {
      this.partyClassification = partyClassification;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
