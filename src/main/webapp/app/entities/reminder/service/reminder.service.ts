import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReminder, getReminderIdentifier } from '../reminder.model';

export type EntityResponseType = HttpResponse<IReminder>;
export type EntityArrayResponseType = HttpResponse<IReminder[]>;

@Injectable({ providedIn: 'root' })
export class ReminderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reminders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reminder: IReminder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reminder);
    return this.http
      .post<IReminder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reminder: IReminder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reminder);
    return this.http
      .put<IReminder>(`${this.resourceUrl}/${getReminderIdentifier(reminder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(reminder: IReminder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reminder);
    return this.http
      .patch<IReminder>(`${this.resourceUrl}/${getReminderIdentifier(reminder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReminder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReminder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReminderToCollectionIfMissing(reminderCollection: IReminder[], ...remindersToCheck: (IReminder | null | undefined)[]): IReminder[] {
    const reminders: IReminder[] = remindersToCheck.filter(isPresent);
    if (reminders.length > 0) {
      const reminderCollectionIdentifiers = reminderCollection.map(reminderItem => getReminderIdentifier(reminderItem)!);
      const remindersToAdd = reminders.filter(reminderItem => {
        const reminderIdentifier = getReminderIdentifier(reminderItem);
        if (reminderIdentifier == null || reminderCollectionIdentifiers.includes(reminderIdentifier)) {
          return false;
        }
        reminderCollectionIdentifiers.push(reminderIdentifier);
        return true;
      });
      return [...remindersToAdd, ...reminderCollection];
    }
    return reminderCollection;
  }

  protected convertDateFromClient(reminder: IReminder): IReminder {
    return Object.assign({}, reminder, {
      date: reminder.date?.isValid() ? reminder.date.toJSON() : undefined,
      createdOn: reminder.createdOn?.isValid() ? reminder.createdOn.toJSON() : undefined,
      updatedOn: reminder.updatedOn?.isValid() ? reminder.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reminder: IReminder) => {
        reminder.date = reminder.date ? dayjs(reminder.date) : undefined;
        reminder.createdOn = reminder.createdOn ? dayjs(reminder.createdOn) : undefined;
        reminder.updatedOn = reminder.updatedOn ? dayjs(reminder.updatedOn) : undefined;
      });
    }
    return res;
  }
}
