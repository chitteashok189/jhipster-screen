import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityPermission } from '../security-permission.model';
import { SecurityPermissionService } from '../service/security-permission.service';
import { SecurityPermissionDeleteDialogComponent } from '../delete/security-permission-delete-dialog.component';

@Component({
  selector: 'jhi-security-permission',
  templateUrl: './security-permission.component.html',
})
export class SecurityPermissionComponent implements OnInit {
  securityPermissions?: ISecurityPermission[];
  isLoading = false;

  constructor(protected securityPermissionService: SecurityPermissionService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.securityPermissionService.query().subscribe({
      next: (res: HttpResponse<ISecurityPermission[]>) => {
        this.isLoading = false;
        this.securityPermissions = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISecurityPermission): number {
    return item.id!;
  }

  delete(securityPermission: ISecurityPermission): void {
    const modalRef = this.modalService.open(SecurityPermissionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.securityPermission = securityPermission;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
