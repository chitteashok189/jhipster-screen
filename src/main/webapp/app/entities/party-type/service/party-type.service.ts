import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyType, getPartyTypeIdentifier } from '../party-type.model';

export type EntityResponseType = HttpResponse<IPartyType>;
export type EntityArrayResponseType = HttpResponse<IPartyType[]>;

@Injectable({ providedIn: 'root' })
export class PartyTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyType: IPartyType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyType);
    return this.http
      .post<IPartyType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyType: IPartyType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyType);
    return this.http
      .put<IPartyType>(`${this.resourceUrl}/${getPartyTypeIdentifier(partyType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyType: IPartyType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyType);
    return this.http
      .patch<IPartyType>(`${this.resourceUrl}/${getPartyTypeIdentifier(partyType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyTypeToCollectionIfMissing(
    partyTypeCollection: IPartyType[],
    ...partyTypesToCheck: (IPartyType | null | undefined)[]
  ): IPartyType[] {
    const partyTypes: IPartyType[] = partyTypesToCheck.filter(isPresent);
    if (partyTypes.length > 0) {
      const partyTypeCollectionIdentifiers = partyTypeCollection.map(partyTypeItem => getPartyTypeIdentifier(partyTypeItem)!);
      const partyTypesToAdd = partyTypes.filter(partyTypeItem => {
        const partyTypeIdentifier = getPartyTypeIdentifier(partyTypeItem);
        if (partyTypeIdentifier == null || partyTypeCollectionIdentifiers.includes(partyTypeIdentifier)) {
          return false;
        }
        partyTypeCollectionIdentifiers.push(partyTypeIdentifier);
        return true;
      });
      return [...partyTypesToAdd, ...partyTypeCollection];
    }
    return partyTypeCollection;
  }

  protected convertDateFromClient(partyType: IPartyType): IPartyType {
    return Object.assign({}, partyType, {
      createdOn: partyType.createdOn?.isValid() ? partyType.createdOn.toJSON() : undefined,
      updatedOn: partyType.updatedOn?.isValid() ? partyType.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyType: IPartyType) => {
        partyType.createdOn = partyType.createdOn ? dayjs(partyType.createdOn) : undefined;
        partyType.updatedOn = partyType.updatedOn ? dayjs(partyType.updatedOn) : undefined;
      });
    }
    return res;
  }
}
