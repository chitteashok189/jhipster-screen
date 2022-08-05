import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityGroupPermission } from '../security-group-permission.model';
import { SecurityGroupPermissionService } from '../service/security-group-permission.service';
import { SecurityGroupPermissionDeleteDialogComponent } from '../delete/security-group-permission-delete-dialog.component';

@Component({
  selector: 'jhi-security-group-permission',
  templateUrl: './security-group-permission.component.html',
})
export class SecurityGroupPermissionComponent implements OnInit {
  securityGroupPermissions?: ISecurityGroupPermission[];
  isLoading = false;

  constructor(protected securityGroupPermissionService: SecurityGroupPermissionService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.securityGroupPermissionService.query().subscribe({
      next: (res: HttpResponse<ISecurityGroupPermission[]>) => {
        this.isLoading = false;
        this.securityGroupPermissions = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISecurityGroupPermission): number {
    return item.id!;
  }

  delete(securityGroupPermission: ISecurityGroupPermission): void {
    const modalRef = this.modalService.open(SecurityGroupPermissionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.securityGroupPermission = securityGroupPermission;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
