import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecurityGroup } from '../security-group.model';
import { SecurityGroupService } from '../service/security-group.service';
import { SecurityGroupDeleteDialogComponent } from '../delete/security-group-delete-dialog.component';

@Component({
  selector: 'jhi-security-group',
  templateUrl: './security-group.component.html',
})
export class SecurityGroupComponent implements OnInit {
  securityGroups?: ISecurityGroup[];
  isLoading = false;

  constructor(protected securityGroupService: SecurityGroupService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.securityGroupService.query().subscribe({
      next: (res: HttpResponse<ISecurityGroup[]>) => {
        this.isLoading = false;
        this.securityGroups = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: ISecurityGroup): number {
    return item.id!;
  }

  delete(securityGroup: ISecurityGroup): void {
    const modalRef = this.modalService.open(SecurityGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.securityGroup = securityGroup;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
