import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyStatus, getPartyStatusIdentifier } from '../party-status.model';

export type EntityResponseType = HttpResponse<IPartyStatus>;
export type EntityArrayResponseType = HttpResponse<IPartyStatus[]>;

@Injectable({ providedIn: 'root' })
export class PartyStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyStatus: IPartyStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatus);
    return this.http
      .post<IPartyStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyStatus: IPartyStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatus);
    return this.http
      .put<IPartyStatus>(`${this.resourceUrl}/${getPartyStatusIdentifier(partyStatus) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyStatus: IPartyStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatus);
    return this.http
      .patch<IPartyStatus>(`${this.resourceUrl}/${getPartyStatusIdentifier(partyStatus) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyStatusToCollectionIfMissing(
    partyStatusCollection: IPartyStatus[],
    ...partyStatusesToCheck: (IPartyStatus | null | undefined)[]
  ): IPartyStatus[] {
    const partyStatuses: IPartyStatus[] = partyStatusesToCheck.filter(isPresent);
    if (partyStatuses.length > 0) {
      const partyStatusCollectionIdentifiers = partyStatusCollection.map(partyStatusItem => getPartyStatusIdentifier(partyStatusItem)!);
      const partyStatusesToAdd = partyStatuses.filter(partyStatusItem => {
        const partyStatusIdentifier = getPartyStatusIdentifier(partyStatusItem);
        if (partyStatusIdentifier == null || partyStatusCollectionIdentifiers.includes(partyStatusIdentifier)) {
          return false;
        }
        partyStatusCollectionIdentifiers.push(partyStatusIdentifier);
        return true;
      });
      return [...partyStatusesToAdd, ...partyStatusCollection];
    }
    return partyStatusCollection;
  }

  protected convertDateFromClient(partyStatus: IPartyStatus): IPartyStatus {
    return Object.assign({}, partyStatus, {
      statusDate: partyStatus.statusDate?.isValid() ? partyStatus.statusDate.toJSON() : undefined,
      createdOn: partyStatus.createdOn?.isValid() ? partyStatus.createdOn.toJSON() : undefined,
      updatedOn: partyStatus.updatedOn?.isValid() ? partyStatus.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.statusDate = res.body.statusDate ? dayjs(res.body.statusDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partyStatus: IPartyStatus) => {
        partyStatus.statusDate = partyStatus.statusDate ? dayjs(partyStatus.statusDate) : undefined;
        partyStatus.createdOn = partyStatus.createdOn ? dayjs(partyStatus.createdOn) : undefined;
        partyStatus.updatedOn = partyStatus.updatedOn ? dayjs(partyStatus.updatedOn) : undefined;
      });
    }
    return res;
  }
}
