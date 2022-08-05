import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyRelationshipType } from '../party-relationship-type.model';

@Component({
  selector: 'jhi-party-relationship-type-detail',
  templateUrl: './party-relationship-type-detail.component.html',
})
export class PartyRelationshipTypeDetailComponent implements OnInit {
  partyRelationshipType: IPartyRelationshipType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRelationshipType }) => {
      this.partyRelationshipType = partyRelationshipType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
