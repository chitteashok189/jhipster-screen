import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyAttribute, getPartyAttributeIdentifier } from '../party-attribute.model';

export type EntityResponseType = HttpResponse<IPartyAttribute>;
export type EntityArrayResponseType = HttpResponse<IPartyAttribute[]>;

@Injectable({ providedIn: 'root' })
export class PartyAttributeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyAttribute: IPartyAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyAttribute);
    return this.http
      .post<IPartyAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyAttribute: IPartyAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyAttribute);
    return this.http
      .put<IPartyAttribute>(`${this.resourceUrl}/${getPartyAttributeIdentifier(partyAttribute) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyAttribute: IPartyAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyAttribute);
    return this.http
      .patch<IPartyAttribute>(`${this.resourceUrl}/${getPartyAttributeIdentifier(partyAttribute) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyAttribute[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyAttributeToCollectionIfMissing(
    partyAttributeCollection: IPartyAttribute[],
    ...partyAttributesToCheck: (IPartyAttribute | null | undefined)[]
  ): IPartyAttribute[] {
    const partyAttributes: IPartyAttribute[] = partyAttributesToCheck.filter(isPresent);
    if (partyAttributes.length > 0) {
      const partyAttributeCollectionIdentifiers = partyAttributeCollection.map(
        partyAttributeItem => getPartyAttributeIdentifier(partyAttributeItem)!
      );
      const partyAttributesToAdd = partyAttributes.filter(partyAttributeItem => {
        const partyAttributeIdentifier = getPartyAttributeIdentifier(partyAttributeItem);
        if (partyAttributeIdentifier == null || partyAttributeCollectionIdentifiers.includes(partyAttributeIdentifier)) {
          return false;
        }
        partyAttributeCollectionIdentifiers.push(partyAttributeIdentifier);
        return true;
      });
      return [...partyAttributesToAdd, ...partyAttributeCollection];
    }
    return partyAttributeCollection;
  }

  protected convertDateFromClient(partyAttribute: IPartyAttribute): IPartyAttribute {
    return Object.assign({}, partyAttribute, {
      createdOn: partyAttribute.createdOn?.isValid() ? partyAttribute.createdOn.toJSON() : undefined,
      updatedOn: partyAttribute.updatedOn?.isValid() ? partyAttribute.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyAttribute: IPartyAttribute) => {
        partyAttribute.createdOn = partyAttribute.createdOn ? dayjs(partyAttribute.createdOn) : undefined;
        partyAttribute.updatedOn = partyAttribute.updatedOn ? dayjs(partyAttribute.updatedOn) : undefined;
      });
    }
    return res;
  }
}
