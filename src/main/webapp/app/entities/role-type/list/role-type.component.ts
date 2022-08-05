import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoleType } from '../role-type.model';
import { RoleTypeService } from '../service/role-type.service';
import { RoleTypeDeleteDialogComponent } from '../delete/role-type-delete-dialog.component';

@Component({
  selector: 'jhi-role-type',
  templateUrl: './role-type.component.html',
})
export class RoleTypeComponent implements OnInit {
  roleTypes?: IRoleType[];
  isLoading = false;

  constructor(protected roleTypeService: RoleTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.roleTypeService.query().subscribe({
      next: (res: HttpResponse<IRoleType[]>) => {
        this.isLoading = false;
        this.roleTypes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IRoleType): number {
    return item.id!;
  }

  delete(roleType: IRoleType): void {
    const modalRef = this.modalService.open(RoleTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roleType = roleType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
