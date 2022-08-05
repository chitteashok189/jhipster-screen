import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyRelationshipType, getPartyRelationshipTypeIdentifier } from '../party-relationship-type.model';

export type EntityResponseType = HttpResponse<IPartyRelationshipType>;
export type EntityArrayResponseType = HttpResponse<IPartyRelationshipType[]>;

@Injectable({ providedIn: 'root' })
export class PartyRelationshipTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-relationship-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyRelationshipType: IPartyRelationshipType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationshipType);
    return this.http
      .post<IPartyRelationshipType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyRelationshipType: IPartyRelationshipType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationshipType);
    return this.http
      .put<IPartyRelationshipType>(`${this.resourceUrl}/${getPartyRelationshipTypeIdentifier(partyRelationshipType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyRelationshipType: IPartyRelationshipType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationshipType);
    return this.http
      .patch<IPartyRelationshipType>(`${this.resourceUrl}/${getPartyRelationshipTypeIdentifier(partyRelationshipType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyRelationshipType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyRelationshipType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyRelationshipTypeToCollectionIfMissing(
    partyRelationshipTypeCollection: IPartyRelationshipType[],
    ...partyRelationshipTypesToCheck: (IPartyRelationshipType | null | undefined)[]
  ): IPartyRelationshipType[] {
    const partyRelationshipTypes: IPartyRelationshipType[] = partyRelationshipTypesToCheck.filter(isPresent);
    if (partyRelationshipTypes.length > 0) {
      const partyRelationshipTypeCollectionIdentifiers = partyRelationshipTypeCollection.map(
        partyRelationshipTypeItem => getPartyRelationshipTypeIdentifier(partyRelationshipTypeItem)!
      );
      const partyRelationshipTypesToAdd = partyRelationshipTypes.filter(partyRelationshipTypeItem => {
        const partyRelationshipTypeIdentifier = getPartyRelationshipTypeIdentifier(partyRelationshipTypeItem);
        if (
          partyRelationshipTypeIdentifier == null ||
          partyRelationshipTypeCollectionIdentifiers.includes(partyRelationshipTypeIdentifier)
        ) {
          return false;
        }
        partyRelationshipTypeCollectionIdentifiers.push(partyRelationshipTypeIdentifier);
        return true;
      });
      return [...partyRelationshipTypesToAdd, ...partyRelationshipTypeCollection];
    }
    return partyRelationshipTypeCollection;
  }

  protected convertDateFromClient(partyRelationshipType: IPartyRelationshipType): IPartyRelationshipType {
    return Object.assign({}, partyRelationshipType, {
      createdOn: partyRelationshipType.createdOn?.isValid() ? partyRelationshipType.createdOn.toJSON() : undefined,
      updatedOn: partyRelationshipType.updatedOn?.isValid() ? partyRelationshipType.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyRelationshipType: IPartyRelationshipType) => {
        partyRelationshipType.createdOn = partyRelationshipType.createdOn ? dayjs(partyRelationshipType.createdOn) : undefined;
        partyRelationshipType.updatedOn = partyRelationshipType.updatedOn ? dayjs(partyRelationshipType.updatedOn) : undefined;
      });
    }
    return res;
  }
}
