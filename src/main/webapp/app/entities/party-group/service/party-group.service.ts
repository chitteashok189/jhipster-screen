import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyGroup, getPartyGroupIdentifier } from '../party-group.model';

export type EntityResponseType = HttpResponse<IPartyGroup>;
export type EntityArrayResponseType = HttpResponse<IPartyGroup[]>;

@Injectable({ providedIn: 'root' })
export class PartyGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyGroup: IPartyGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyGroup);
    return this.http
      .post<IPartyGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyGroup: IPartyGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyGroup);
    return this.http
      .put<IPartyGroup>(`${this.resourceUrl}/${getPartyGroupIdentifier(partyGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyGroup: IPartyGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyGroup);
    return this.http
      .patch<IPartyGroup>(`${this.resourceUrl}/${getPartyGroupIdentifier(partyGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyGroupToCollectionIfMissing(
    partyGroupCollection: IPartyGroup[],
    ...partyGroupsToCheck: (IPartyGroup | null | undefined)[]
  ): IPartyGroup[] {
    const partyGroups: IPartyGroup[] = partyGroupsToCheck.filter(isPresent);
    if (partyGroups.length > 0) {
      const partyGroupCollectionIdentifiers = partyGroupCollection.map(partyGroupItem => getPartyGroupIdentifier(partyGroupItem)!);
      const partyGroupsToAdd = partyGroups.filter(partyGroupItem => {
        const partyGroupIdentifier = getPartyGroupIdentifier(partyGroupItem);
        if (partyGroupIdentifier == null || partyGroupCollectionIdentifiers.includes(partyGroupIdentifier)) {
          return false;
        }
        partyGroupCollectionIdentifiers.push(partyGroupIdentifier);
        return true;
      });
      return [...partyGroupsToAdd, ...partyGroupCollection];
    }
    return partyGroupCollection;
  }

  protected convertDateFromClient(partyGroup: IPartyGroup): IPartyGroup {
    return Object.assign({}, partyGroup, {
      createdOn: partyGroup.createdOn?.isValid() ? partyGroup.createdOn.toJSON() : undefined,
      updatedOn: partyGroup.updatedOn?.isValid() ? partyGroup.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyGroup: IPartyGroup) => {
        partyGroup.createdOn = partyGroup.createdOn ? dayjs(partyGroup.createdOn) : undefined;
        partyGroup.updatedOn = partyGroup.updatedOn ? dayjs(partyGroup.updatedOn) : undefined;
      });
    }
    return res;
  }
}
