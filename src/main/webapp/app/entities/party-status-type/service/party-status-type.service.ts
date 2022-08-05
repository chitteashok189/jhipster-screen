import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyStatusType, getPartyStatusTypeIdentifier } from '../party-status-type.model';

export type EntityResponseType = HttpResponse<IPartyStatusType>;
export type EntityArrayResponseType = HttpResponse<IPartyStatusType[]>;

@Injectable({ providedIn: 'root' })
export class PartyStatusTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-status-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyStatusType: IPartyStatusType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusType);
    return this.http
      .post<IPartyStatusType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyStatusType: IPartyStatusType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusType);
    return this.http
      .put<IPartyStatusType>(`${this.resourceUrl}/${getPartyStatusTypeIdentifier(partyStatusType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyStatusType: IPartyStatusType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyStatusType);
    return this.http
      .patch<IPartyStatusType>(`${this.resourceUrl}/${getPartyStatusTypeIdentifier(partyStatusType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyStatusType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyStatusType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyStatusTypeToCollectionIfMissing(
    partyStatusTypeCollection: IPartyStatusType[],
    ...partyStatusTypesToCheck: (IPartyStatusType | null | undefined)[]
  ): IPartyStatusType[] {
    const partyStatusTypes: IPartyStatusType[] = partyStatusTypesToCheck.filter(isPresent);
    if (partyStatusTypes.length > 0) {
      const partyStatusTypeCollectionIdentifiers = partyStatusTypeCollection.map(
        partyStatusTypeItem => getPartyStatusTypeIdentifier(partyStatusTypeItem)!
      );
      const partyStatusTypesToAdd = partyStatusTypes.filter(partyStatusTypeItem => {
        const partyStatusTypeIdentifier = getPartyStatusTypeIdentifier(partyStatusTypeItem);
        if (partyStatusTypeIdentifier == null || partyStatusTypeCollectionIdentifiers.includes(partyStatusTypeIdentifier)) {
          return false;
        }
        partyStatusTypeCollectionIdentifiers.push(partyStatusTypeIdentifier);
        return true;
      });
      return [...partyStatusTypesToAdd, ...partyStatusTypeCollection];
    }
    return partyStatusTypeCollection;
  }

  protected convertDateFromClient(partyStatusType: IPartyStatusType): IPartyStatusType {
    return Object.assign({}, partyStatusType, {
      createdOn: partyStatusType.createdOn?.isValid() ? partyStatusType.createdOn.toJSON() : undefined,
      updatedOn: partyStatusType.updatedOn?.isValid() ? partyStatusType.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyStatusType: IPartyStatusType) => {
        partyStatusType.createdOn = partyStatusType.createdOn ? dayjs(partyStatusType.createdOn) : undefined;
        partyStatusType.updatedOn = partyStatusType.updatedOn ? dayjs(partyStatusType.updatedOn) : undefined;
      });
    }
    return res;
  }
}
