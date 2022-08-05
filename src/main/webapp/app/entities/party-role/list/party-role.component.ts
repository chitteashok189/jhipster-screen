import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyRole } from '../party-role.model';
import { PartyRoleService } from '../service/party-role.service';
import { PartyRoleDeleteDialogComponent } from '../delete/party-role-delete-dialog.component';

@Component({
  selector: 'jhi-party-role',
  templateUrl: './party-role.component.html',
})
export class PartyRoleComponent implements OnInit {
  partyRoles?: IPartyRole[];
  isLoading = false;

  constructor(protected partyRoleService: PartyRoleService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.partyRoleService.query().subscribe({
      next: (res: HttpResponse<IPartyRole[]>) => {
        this.isLoading = false;
        this.partyRoles = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IPartyRole): number {
    return item.id!;
  }

  delete(partyRole: IPartyRole): void {
    const modalRef = this.modalService.open(PartyRoleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyRole = partyRole;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
