import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyTypeAttribute, getPartyTypeAttributeIdentifier } from '../party-type-attribute.model';

export type EntityResponseType = HttpResponse<IPartyTypeAttribute>;
export type EntityArrayResponseType = HttpResponse<IPartyTypeAttribute[]>;

@Injectable({ providedIn: 'root' })
export class PartyTypeAttributeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-type-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyTypeAttribute: IPartyTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyTypeAttribute);
    return this.http
      .post<IPartyTypeAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyTypeAttribute: IPartyTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyTypeAttribute);
    return this.http
      .put<IPartyTypeAttribute>(`${this.resourceUrl}/${getPartyTypeAttributeIdentifier(partyTypeAttribute) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyTypeAttribute: IPartyTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyTypeAttribute);
    return this.http
      .patch<IPartyTypeAttribute>(`${this.resourceUrl}/${getPartyTypeAttributeIdentifier(partyTypeAttribute) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyTypeAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyTypeAttribute[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyTypeAttributeToCollectionIfMissing(
    partyTypeAttributeCollection: IPartyTypeAttribute[],
    ...partyTypeAttributesToCheck: (IPartyTypeAttribute | null | undefined)[]
  ): IPartyTypeAttribute[] {
    const partyTypeAttributes: IPartyTypeAttribute[] = partyTypeAttributesToCheck.filter(isPresent);
    if (partyTypeAttributes.length > 0) {
      const partyTypeAttributeCollectionIdentifiers = partyTypeAttributeCollection.map(
        partyTypeAttributeItem => getPartyTypeAttributeIdentifier(partyTypeAttributeItem)!
      );
      const partyTypeAttributesToAdd = partyTypeAttributes.filter(partyTypeAttributeItem => {
        const partyTypeAttributeIdentifier = getPartyTypeAttributeIdentifier(partyTypeAttributeItem);
        if (partyTypeAttributeIdentifier == null || partyTypeAttributeCollectionIdentifiers.includes(partyTypeAttributeIdentifier)) {
          return false;
        }
        partyTypeAttributeCollectionIdentifiers.push(partyTypeAttributeIdentifier);
        return true;
      });
      return [...partyTypeAttributesToAdd, ...partyTypeAttributeCollection];
    }
    return partyTypeAttributeCollection;
  }

  protected convertDateFromClient(partyTypeAttribute: IPartyTypeAttribute): IPartyTypeAttribute {
    return Object.assign({}, partyTypeAttribute, {
      createdOn: partyTypeAttribute.createdOn?.isValid() ? partyTypeAttribute.createdOn.toJSON() : undefined,
      updatedOn: partyTypeAttribute.updatedOn?.isValid() ? partyTypeAttribute.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyTypeAttribute: IPartyTypeAttribute) => {
        partyTypeAttribute.createdOn = partyTypeAttribute.createdOn ? dayjs(partyTypeAttribute.createdOn) : undefined;
        partyTypeAttribute.updatedOn = partyTypeAttribute.updatedOn ? dayjs(partyTypeAttribute.updatedOn) : undefined;
      });
    }
    return res;
  }
}
