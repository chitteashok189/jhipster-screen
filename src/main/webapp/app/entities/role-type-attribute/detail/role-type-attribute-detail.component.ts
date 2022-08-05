import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleTypeAttribute } from '../role-type-attribute.model';

@Component({
  selector: 'jhi-role-type-attribute-detail',
  templateUrl: './role-type-attribute-detail.component.html',
})
export class RoleTypeAttributeDetailComponent implements OnInit {
  roleTypeAttribute: IRoleTypeAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roleTypeAttribute }) => {
      this.roleTypeAttribute = roleTypeAttribute;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
