import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';
import { ApplicationUserSecurityGroupDeleteDialogComponent } from '../delete/application-user-security-group-delete-dialog.component';

@Component({
  selector: 'jhi-application-user-security-group',
  templateUrl: './application-user-security-group.component.html',
})
export class ApplicationUserSecurityGroupComponent implements OnInit {
  applicationUserSecurityGroups?: IApplicationUserSecurityGroup[];
  isLoading = false;

  constructor(protected applicationUserSecurityGroupService: ApplicationUserSecurityGroupService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.applicationUserSecurityGroupService.query().subscribe({
      next: (res: HttpResponse<IApplicationUserSecurityGroup[]>) => {
        this.isLoading = false;
        this.applicationUserSecurityGroups = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IApplicationUserSecurityGroup): number {
    return item.id!;
  }

  delete(applicationUserSecurityGroup: IApplicationUserSecurityGroup): void {
    const modalRef = this.modalService.open(ApplicationUserSecurityGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.applicationUserSecurityGroup = applicationUserSecurityGroup;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
