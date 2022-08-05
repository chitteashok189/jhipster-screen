import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyStatus } from '../party-status.model';

@Component({
  selector: 'jhi-party-status-detail',
  templateUrl: './party-status-detail.component.html',
})
export class PartyStatusDetailComponent implements OnInit {
  partyStatus: IPartyStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatus }) => {
      this.partyStatus = partyStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
