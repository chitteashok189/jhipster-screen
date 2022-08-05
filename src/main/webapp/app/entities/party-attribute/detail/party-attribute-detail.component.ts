import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyAttribute } from '../party-attribute.model';

@Component({
  selector: 'jhi-party-attribute-detail',
  templateUrl: './party-attribute-detail.component.html',
})
export class PartyAttributeDetailComponent implements OnInit {
  partyAttribute: IPartyAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyAttribute }) => {
      this.partyAttribute = partyAttribute;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
