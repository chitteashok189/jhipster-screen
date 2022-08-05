import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyRole } from '../party-role.model';

@Component({
  selector: 'jhi-party-role-detail',
  templateUrl: './party-role-detail.component.html',
})
export class PartyRoleDetailComponent implements OnInit {
  partyRole: IPartyRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRole }) => {
      this.partyRole = partyRole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
