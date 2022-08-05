import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyRelationship } from '../party-relationship.model';

@Component({
  selector: 'jhi-party-relationship-detail',
  templateUrl: './party-relationship-detail.component.html',
})
export class PartyRelationshipDetailComponent implements OnInit {
  partyRelationship: IPartyRelationship | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRelationship }) => {
      this.partyRelationship = partyRelationship;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
