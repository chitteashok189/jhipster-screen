import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyStatusItem } from '../party-status-item.model';

@Component({
  selector: 'jhi-party-status-item-detail',
  templateUrl: './party-status-item-detail.component.html',
})
export class PartyStatusItemDetailComponent implements OnInit {
  partyStatusItem: IPartyStatusItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyStatusItem }) => {
      this.partyStatusItem = partyStatusItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
