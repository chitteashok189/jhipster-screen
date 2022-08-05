import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPestControl, getPestControlIdentifier } from '../pest-control.model';

export type EntityResponseType = HttpResponse<IPestControl>;
export type EntityArrayResponseType = HttpResponse<IPestControl[]>;

@Injectable({ providedIn: 'root' })
export class PestControlService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pest-controls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pestControl: IPestControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pestControl);
    return this.http
      .post<IPestControl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pestControl: IPestControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pestControl);
    return this.http
      .put<IPestControl>(`${this.resourceUrl}/${getPestControlIdentifier(pestControl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pestControl: IPestControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pestControl);
    return this.http
      .patch<IPestControl>(`${this.resourceUrl}/${getPestControlIdentifier(pestControl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPestControl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPestControl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPestControlToCollectionIfMissing(
    pestControlCollection: IPestControl[],
    ...pestControlsToCheck: (IPestControl | null | undefined)[]
  ): IPestControl[] {
    const pestControls: IPestControl[] = pestControlsToCheck.filter(isPresent);
    if (pestControls.length > 0) {
      const pestControlCollectionIdentifiers = pestControlCollection.map(pestControlItem => getPestControlIdentifier(pestControlItem)!);
      const pestControlsToAdd = pestControls.filter(pestControlItem => {
        const pestControlIdentifier = getPestControlIdentifier(pestControlItem);
        if (pestControlIdentifier == null || pestControlCollectionIdentifiers.includes(pestControlIdentifier)) {
          return false;
        }
        pestControlCollectionIdentifiers.push(pestControlIdentifier);
        return true;
      });
      return [...pestControlsToAdd, ...pestControlCollection];
    }
    return pestControlCollection;
  }

  protected convertDateFromClient(pestControl: IPestControl): IPestControl {
    return Object.assign({}, pestControl, {
      createdOn: pestControl.createdOn?.isValid() ? pestControl.createdOn.toJSON() : undefined,
      updatedOn: pestControl.updatedOn?.isValid() ? pestControl.updatedOn.toJSON() : undefined,
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
      res.body.forEach((pestControl: IPestControl) => {
        pestControl.createdOn = pestControl.createdOn ? dayjs(pestControl.createdOn) : undefined;
        pestControl.updatedOn = pestControl.updatedOn ? dayjs(pestControl.updatedOn) : undefined;
      });
    }
    return res;
  }
}
