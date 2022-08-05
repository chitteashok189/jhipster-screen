import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyStatusType } from '../party-status-type.model';

@Component({
  selector: 'jhi-party-status-type-detail',
  templateUrl: './party-status-type-detail.component.html',
})
export class PartyStatusTypeDetailComponent implements OnInit {
  partyStatusType: IPartyStatusType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatusType }) => {
      this.partyStatusType = partyStatusType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
