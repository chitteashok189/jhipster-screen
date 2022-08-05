import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDosing } from '../dosing.model';

@Component({
  selector: 'jhi-dosing-detail',
  templateUrl: './dosing-detail.component.html',
})
export class DosingDetailComponent implements OnInit {
  dosing: IDosing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dosing }) => {
      this.dosing = dosing;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
