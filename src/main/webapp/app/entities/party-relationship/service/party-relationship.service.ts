import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyRelationship, getPartyRelationshipIdentifier } from '../party-relationship.model';

export type EntityResponseType = HttpResponse<IPartyRelationship>;
export type EntityArrayResponseType = HttpResponse<IPartyRelationship[]>;

@Injectable({ providedIn: 'root' })
export class PartyRelationshipService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-relationships');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyRelationship: IPartyRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationship);
    return this.http
      .post<IPartyRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyRelationship: IPartyRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationship);
    return this.http
      .put<IPartyRelationship>(`${this.resourceUrl}/${getPartyRelationshipIdentifier(partyRelationship) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyRelationship: IPartyRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRelationship);
    return this.http
      .patch<IPartyRelationship>(`${this.resourceUrl}/${getPartyRelationshipIdentifier(partyRelationship) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyRelationship[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyRelationshipToCollectionIfMissing(
    partyRelationshipCollection: IPartyRelationship[],
    ...partyRelationshipsToCheck: (IPartyRelationship | null | undefined)[]
  ): IPartyRelationship[] {
    const partyRelationships: IPartyRelationship[] = partyRelationshipsToCheck.filter(isPresent);
    if (partyRelationships.length > 0) {
      const partyRelationshipCollectionIdentifiers = partyRelationshipCollection.map(
        partyRelationshipItem => getPartyRelationshipIdentifier(partyRelationshipItem)!
      );
      const partyRelationshipsToAdd = partyRelationships.filter(partyRelationshipItem => {
        const partyRelationshipIdentifier = getPartyRelationshipIdentifier(partyRelationshipItem);
        if (partyRelationshipIdentifier == null || partyRelationshipCollectionIdentifiers.includes(partyRelationshipIdentifier)) {
          return false;
        }
        partyRelationshipCollectionIdentifiers.push(partyRelationshipIdentifier);
        return true;
      });
      return [...partyRelationshipsToAdd, ...partyRelationshipCollection];
    }
    return partyRelationshipCollection;
  }

  protected convertDateFromClient(partyRelationship: IPartyRelationship): IPartyRelationship {
    return Object.assign({}, partyRelationship, {
      fromDate: partyRelationship.fromDate?.isValid() ? partyRelationship.fromDate.toJSON() : undefined,
      thruDate: partyRelationship.thruDate?.isValid() ? partyRelationship.thruDate.toJSON() : undefined,
      createdOn: partyRelationship.createdOn?.isValid() ? partyRelationship.createdOn.toJSON() : undefined,
      updatedOn: partyRelationship.updatedOn?.isValid() ? partyRelationship.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? dayjs(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? dayjs(res.body.thruDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partyRelationship: IPartyRelationship) => {
        partyRelationship.fromDate = partyRelationship.fromDate ? dayjs(partyRelationship.fromDate) : undefined;
        partyRelationship.thruDate = partyRelationship.thruDate ? dayjs(partyRelationship.thruDate) : undefined;
        partyRelationship.createdOn = partyRelationship.createdOn ? dayjs(partyRelationship.createdOn) : undefined;
        partyRelationship.updatedOn = partyRelationship.updatedOn ? dayjs(partyRelationship.updatedOn) : undefined;
      });
    }
    return res;
  }
}
