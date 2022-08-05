import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyTypeAttribute } from '../party-type-attribute.model';

@Component({
  selector: 'jhi-party-type-attribute-detail',
  templateUrl: './party-type-attribute-detail.component.html',
})
export class PartyTypeAttributeDetailComponent implements OnInit {
  partyTypeAttribute: IPartyTypeAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyTypeAttribute }) => {
      this.partyTypeAttribute = partyTypeAttribute;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
