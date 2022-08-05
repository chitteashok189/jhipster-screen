import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IScouting, getScoutingIdentifier } from '../scouting.model';

export type EntityResponseType = HttpResponse<IScouting>;
export type EntityArrayResponseType = HttpResponse<IScouting[]>;

@Injectable({ providedIn: 'root' })
export class ScoutingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/scoutings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(scouting: IScouting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scouting);
    return this.http
      .post<IScouting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(scouting: IScouting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scouting);
    return this.http
      .put<IScouting>(`${this.resourceUrl}/${getScoutingIdentifier(scouting) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(scouting: IScouting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scouting);
    return this.http
      .patch<IScouting>(`${this.resourceUrl}/${getScoutingIdentifier(scouting) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScouting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScouting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addScoutingToCollectionIfMissing(scoutingCollection: IScouting[], ...scoutingsToCheck: (IScouting | null | undefined)[]): IScouting[] {
    const scoutings: IScouting[] = scoutingsToCheck.filter(isPresent);
    if (scoutings.length > 0) {
      const scoutingCollectionIdentifiers = scoutingCollection.map(scoutingItem => getScoutingIdentifier(scoutingItem)!);
      const scoutingsToAdd = scoutings.filter(scoutingItem => {
        const scoutingIdentifier = getScoutingIdentifier(scoutingItem);
        if (scoutingIdentifier == null || scoutingCollectionIdentifiers.includes(scoutingIdentifier)) {
          return false;
        }
        scoutingCollectionIdentifiers.push(scoutingIdentifier);
        return true;
      });
      return [...scoutingsToAdd, ...scoutingCollection];
    }
    return scoutingCollection;
  }

  protected convertDateFromClient(scouting: IScouting): IScouting {
    return Object.assign({}, scouting, {
      scoutingDate: scouting.scoutingDate?.isValid() ? scouting.scoutingDate.toJSON() : undefined,
      createdOn: scouting.createdOn?.isValid() ? scouting.createdOn.toJSON() : undefined,
      updatedOn: scouting.updatedOn?.isValid() ? scouting.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scoutingDate = res.body.scoutingDate ? dayjs(res.body.scoutingDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((scouting: IScouting) => {
        scouting.scoutingDate = scouting.scoutingDate ? dayjs(scouting.scoutingDate) : undefined;
        scouting.createdOn = scouting.createdOn ? dayjs(scouting.createdOn) : undefined;
        scouting.updatedOn = scouting.updatedOn ? dayjs(scouting.updatedOn) : undefined;
      });
    }
    return res;
  }
}
