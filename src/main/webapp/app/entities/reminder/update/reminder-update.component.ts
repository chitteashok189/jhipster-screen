import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReminder, Reminder } from '../reminder.model';
import { ReminderService } from '../service/reminder.service';
import { RemItem } from 'app/entities/enumerations/rem-item.model';

@Component({
  selector: 'jhi-reminder-update',
  templateUrl: './reminder-update.component.html',
})
export class ReminderUpdateComponent implements OnInit {
  isSaving = false;
  remItemValues = Object.keys(RemItem);

  editForm = this.fb.group({
    id: [],
    gUID: [],
    name: [],
    date: [],
    time: [],
    item: [],
    description: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
  });

  constructor(protected reminderService: ReminderService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reminder }) => {
      if (reminder.id === undefined) {
        const today = dayjs().startOf('day');
        reminder.date = today;
        reminder.createdOn = today;
        reminder.updatedOn = today;
      }

      this.updateForm(reminder);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reminder = this.createFromForm();
    if (reminder.id !== undefined) {
      this.subscribeToSaveResponse(this.reminderService.update(reminder));
    } else {
      this.subscribeToSaveResponse(this.reminderService.create(reminder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReminder>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reminder: IReminder): void {
    this.editForm.patchValue({
      id: reminder.id,
      gUID: reminder.gUID,
      name: reminder.name,
      date: reminder.date ? reminder.date.format(DATE_TIME_FORMAT) : null,
      time: reminder.time,
      item: reminder.item,
      description: reminder.description,
      createdBy: reminder.createdBy,
      createdOn: reminder.createdOn ? reminder.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: reminder.updatedBy,
      updatedOn: reminder.updatedOn ? reminder.updatedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IReminder {
    return {
      ...new Reminder(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      name: this.editForm.get(['name'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      time: this.editForm.get(['time'])!.value,
      item: this.editForm.get(['item'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
