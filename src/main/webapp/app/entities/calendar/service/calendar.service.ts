import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICalendar, getCalendarIdentifier } from '../calendar.model';

export type EntityResponseType = HttpResponse<ICalendar>;
export type EntityArrayResponseType = HttpResponse<ICalendar[]>;

@Injectable({ providedIn: 'root' })
export class CalendarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/calendars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(calendar: ICalendar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calendar);
    return this.http
      .post<ICalendar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(calendar: ICalendar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calendar);
    return this.http
      .put<ICalendar>(`${this.resourceUrl}/${getCalendarIdentifier(calendar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(calendar: ICalendar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calendar);
    return this.http
      .patch<ICalendar>(`${this.resourceUrl}/${getCalendarIdentifier(calendar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICalendar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICalendar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCalendarToCollectionIfMissing(calendarCollection: ICalendar[], ...calendarsToCheck: (ICalendar | null | undefined)[]): ICalendar[] {
    const calendars: ICalendar[] = calendarsToCheck.filter(isPresent);
    if (calendars.length > 0) {
      const calendarCollectionIdentifiers = calendarCollection.map(calendarItem => getCalendarIdentifier(calendarItem)!);
      const calendarsToAdd = calendars.filter(calendarItem => {
        const calendarIdentifier = getCalendarIdentifier(calendarItem);
        if (calendarIdentifier == null || calendarCollectionIdentifiers.includes(calendarIdentifier)) {
          return false;
        }
        calendarCollectionIdentifiers.push(calendarIdentifier);
        return true;
      });
      return [...calendarsToAdd, ...calendarCollection];
    }
    return calendarCollection;
  }

  protected convertDateFromClient(calendar: ICalendar): ICalendar {
    return Object.assign({}, calendar, {
      calenderDate: calendar.calenderDate?.isValid() ? calendar.calenderDate.toJSON() : undefined,
      createdOn: calendar.createdOn?.isValid() ? calendar.createdOn.toJSON() : undefined,
      updatedOn: calendar.updatedOn?.isValid() ? calendar.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.calenderDate = res.body.calenderDate ? dayjs(res.body.calenderDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((calendar: ICalendar) => {
        calendar.calenderDate = calendar.calenderDate ? dayjs(calendar.calenderDate) : undefined;
        calendar.createdOn = calendar.createdOn ? dayjs(calendar.createdOn) : undefined;
        calendar.updatedOn = calendar.updatedOn ? dayjs(calendar.updatedOn) : undefined;
      });
    }
    return res;
  }
}
