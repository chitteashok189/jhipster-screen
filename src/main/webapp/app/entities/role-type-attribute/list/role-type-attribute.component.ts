import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleTypeAttribute } from '../role-type-attribute.model';
import { RoleTypeAttributeService } from '../service/role-type-attribute.service';
import { RoleTypeAttributeDeleteDialogComponent } from '../delete/role-type-attribute-delete-dialog.component';

@Component({
  selector: 'jhi-role-type-attribute',
  templateUrl: './role-type-attribute.component.html',
})
export class RoleTypeAttributeComponent implements OnInit {
  roleTypeAttributes?: IRoleTypeAttribute[];
  isLoading = false;

  constructor(protected roleTypeAttributeService: RoleTypeAttributeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.roleTypeAttributeService.query().subscribe({
      next: (res: HttpResponse<IRoleTypeAttribute[]>) => {
        this.isLoading = false;
        this.roleTypeAttributes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRoleTypeAttribute): number {
    return item.id!;
  }

  delete(roleTypeAttribute: IRoleTypeAttribute): void {
    const modalRef = this.modalService.open(RoleTypeAttributeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roleTypeAttribute = roleTypeAttribute;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
