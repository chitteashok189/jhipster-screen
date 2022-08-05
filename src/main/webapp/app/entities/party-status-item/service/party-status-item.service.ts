import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyStatusItem, getPartyStatusItemIdentifier } from '../party-status-item.model';

export type EntityResponseType = HttpResponse<IPartyStatusItem>;
export type EntityArrayResponseType = HttpResponse<IPartyStatusItem[]>;

@Injectable({ providedIn: 'root' })
export class PartyStatusItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-status-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyStatusItem: IPartyStatusItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusItem);
    return this.http
      .post<IPartyStatusItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyStatusItem: IPartyStatusItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusItem);
    return this.http
      .put<IPartyStatusItem>(`${this.resourceUrl}/${getPartyStatusItemIdentifier(partyStatusItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyStatusItem: IPartyStatusItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusItem);
    return this.http
      .patch<IPartyStatusItem>(`${this.resourceUrl}/${getPartyStatusItemIdentifier(partyStatusItem) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyStatusItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyStatusItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyStatusItemToCollectionIfMissing(
    partyStatusItemCollection: IPartyStatusItem[],
    ...partyStatusItemsToCheck: (IPartyStatusItem | null | undefined)[]
  ): IPartyStatusItem[] {
    const partyStatusItems: IPartyStatusItem[] = partyStatusItemsToCheck.filter(isPresent);
    if (partyStatusItems.length > 0) {
      const partyStatusItemCollectionIdentifiers = partyStatusItemCollection.map(
        partyStatusItemItem => getPartyStatusItemIdentifier(partyStatusItemItem)!
      );
      const partyStatusItemsToAdd = partyStatusItems.filter(partyStatusItemItem => {
        const partyStatusItemIdentifier = getPartyStatusItemIdentifier(partyStatusItemItem);
        if (partyStatusItemIdentifier == null || partyStatusItemCollectionIdentifiers.includes(partyStatusItemIdentifier)) {
          return false;
        }
        partyStatusItemCollectionIdentifiers.push(partyStatusItemIdentifier);
        return true;
      });
      return [...partyStatusItemsToAdd, ...partyStatusItemCollection];
    }
    return partyStatusItemCollection;
  }

  protected convertDateFromClient(partyStatusItem: IPartyStatusItem): IPartyStatusItem {
    return Object.assign({}, partyStatusItem, {
      createdOn: partyStatusItem.createdOn?.isValid() ? partyStatusItem.createdOn.toJSON() : undefined,
      updatedOn: partyStatusItem.updatedOn?.isValid() ? partyStatusItem.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyStatusItem: IPartyStatusItem) => {
        partyStatusItem.createdOn = partyStatusItem.createdOn ? dayjs(partyStatusItem.createdOn) : undefined;
        partyStatusItem.updatedOn = partyStatusItem.updatedOn ? dayjs(partyStatusItem.updatedOn) : undefined;
      });
    }
    return res;
  }
}
