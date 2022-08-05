import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisorder } from '../disorder.model';

@Component({
  selector: 'jhi-disorder-detail',
  templateUrl: './disorder-detail.component.html',
})
export class DisorderDetailComponent implements OnInit {
  disorder: IDisorder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disorder }) => {
      this.disorder = disorder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
