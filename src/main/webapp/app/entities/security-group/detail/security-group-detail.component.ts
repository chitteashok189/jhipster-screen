import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityGroup } from '../security-group.model';

@Component({
  selector: 'jhi-security-group-detail',
  templateUrl: './security-group-detail.component.html',
})
export class SecurityGroupDetailComponent implements OnInit {
  securityGroup: ISecurityGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityGroup }) => {
      this.securityGroup = securityGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
