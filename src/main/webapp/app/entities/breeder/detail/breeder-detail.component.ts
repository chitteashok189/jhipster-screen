import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBreeder } from '../breeder.model';

@Component({
  selector: 'jhi-breeder-detail',
  templateUrl: './breeder-detail.component.html',
})
export class BreederDetailComponent implements OnInit {
  breeder: IBreeder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ breeder }) => {
      this.breeder = breeder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
