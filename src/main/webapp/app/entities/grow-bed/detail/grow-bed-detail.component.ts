import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrowBed } from '../grow-bed.model';

@Component({
  selector: 'jhi-grow-bed-detail',
  templateUrl: './grow-bed-detail.component.html',
})
export class GrowBedDetailComponent implements OnInit {
  growBed: IGrowBed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ growBed }) => {
      this.growBed = growBed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
