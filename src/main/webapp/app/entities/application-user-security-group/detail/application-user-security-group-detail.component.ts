import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationUserSecurityGroup } from '../application-user-security-group.model';

@Component({
  selector: 'jhi-application-user-security-group-detail',
  templateUrl: './application-user-security-group-detail.component.html',
})
export class ApplicationUserSecurityGroupDetailComponent implements OnInit {
  applicationUserSecurityGroup: IApplicationUserSecurityGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationUserSecurityGroup }) => {
      this.applicationUserSecurityGroup = applicationUserSecurityGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
