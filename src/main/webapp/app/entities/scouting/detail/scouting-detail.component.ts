import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScouting } from '../scouting.model';

@Component({
  selector: 'jhi-scouting-detail',
  templateUrl: './scouting-detail.component.html',
})
export class ScoutingDetailComponent implements OnInit {
  scouting: IScouting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scouting }) => {
      this.scouting = scouting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
