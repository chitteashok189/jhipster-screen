import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlert, getAlertIdentifier } from '../alert.model';

export type EntityResponseType = HttpResponse<IAlert>;
export type EntityArrayResponseType = HttpResponse<IAlert[]>;

@Injectable({ providedIn: 'root' })
export class AlertService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/alerts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(alert: IAlert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alert);
    return this.http
      .post<IAlert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alert: IAlert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alert);
    return this.http
      .put<IAlert>(`${this.resourceUrl}/${getAlertIdentifier(alert) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(alert: IAlert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alert);
    return this.http
      .patch<IAlert>(`${this.resourceUrl}/${getAlertIdentifier(alert) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlert>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlert[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAlertToCollectionIfMissing(alertCollection: IAlert[], ...alertsToCheck: (IAlert | null | undefined)[]): IAlert[] {
    const alerts: IAlert[] = alertsToCheck.filter(isPresent);
    if (alerts.length > 0) {
      const alertCollectionIdentifiers = alertCollection.map(alertItem => getAlertIdentifier(alertItem)!);
      const alertsToAdd = alerts.filter(alertItem => {
        const alertIdentifier = getAlertIdentifier(alertItem);
        if (alertIdentifier == null || alertCollectionIdentifiers.includes(alertIdentifier)) {
          return false;
        }
        alertCollectionIdentifiers.push(alertIdentifier);
        return true;
      });
      return [...alertsToAdd, ...alertCollection];
    }
    return alertCollection;
  }

  protected convertDateFromClient(alert: IAlert): IAlert {
    return Object.assign({}, alert, {
      createdOn: alert.createdOn?.isValid() ? alert.createdOn.toJSON() : undefined,
      updatedOn: alert.updatedOn?.isValid() ? alert.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((alert: IAlert) => {
        alert.createdOn = alert.createdOn ? dayjs(alert.createdOn) : undefined;
        alert.updatedOn = alert.updatedOn ? dayjs(alert.updatedOn) : undefined;
      });
    }
    return res;
  }
}
