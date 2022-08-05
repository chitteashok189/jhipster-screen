import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityGroupPermission } from '../security-group-permission.model';

@Component({
  selector: 'jhi-security-group-permission-detail',
  templateUrl: './security-group-permission-detail.component.html',
})
export class SecurityGroupPermissionDetailComponent implements OnInit {
  securityGroupPermission: ISecurityGroupPermission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityGroupPermission }) => {
      this.securityGroupPermission = securityGroupPermission;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
