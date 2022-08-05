import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReminder } from '../reminder.model';
import { ReminderService } from '../service/reminder.service';
import { ReminderDeleteDialogComponent } from '../delete/reminder-delete-dialog.component';

@Component({
  selector: 'jhi-reminder',
  templateUrl: './reminder.component.html',
})
export class ReminderComponent implements OnInit {
  reminders?: IReminder[];
  isLoading = false;

  constructor(protected reminderService: ReminderService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.reminderService.query().subscribe({
      next: (res: HttpResponse<IReminder[]>) => {
        this.isLoading = false;
        this.reminders = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IReminder): number {
    return item.id!;
  }

  delete(reminder: IReminder): void {
    const modalRef = this.modalService.open(ReminderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reminder = reminder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
