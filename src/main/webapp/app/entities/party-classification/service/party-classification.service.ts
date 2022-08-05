import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyClassification, getPartyClassificationIdentifier } from '../party-classification.model';

export type EntityResponseType = HttpResponse<IPartyClassification>;
export type EntityArrayResponseType = HttpResponse<IPartyClassification[]>;

@Injectable({ providedIn: 'root' })
export class PartyClassificationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-classifications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyClassification: IPartyClassification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyClassification);
    return this.http
      .post<IPartyClassification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyClassification: IPartyClassification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyClassification);
    return this.http
      .put<IPartyClassification>(`${this.resourceUrl}/${getPartyClassificationIdentifier(partyClassification) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyClassification: IPartyClassification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyClassification);
    return this.http
      .patch<IPartyClassification>(`${this.resourceUrl}/${getPartyClassificationIdentifier(partyClassification) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyClassification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyClassification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyClassificationToCollectionIfMissing(
    partyClassificationCollection: IPartyClassification[],
    ...partyClassificationsToCheck: (IPartyClassification | null | undefined)[]
  ): IPartyClassification[] {
    const partyClassifications: IPartyClassification[] = partyClassificationsToCheck.filter(isPresent);
    if (partyClassifications.length > 0) {
      const partyClassificationCollectionIdentifiers = partyClassificationCollection.map(
        partyClassificationItem => getPartyClassificationIdentifier(partyClassificationItem)!
      );
      const partyClassificationsToAdd = partyClassifications.filter(partyClassificationItem => {
        const partyClassificationIdentifier = getPartyClassificationIdentifier(partyClassificationItem);
        if (partyClassificationIdentifier == null || partyClassificationCollectionIdentifiers.includes(partyClassificationIdentifier)) {
          return false;
        }
        partyClassificationCollectionIdentifiers.push(partyClassificationIdentifier);
        return true;
      });
      return [...partyClassificationsToAdd, ...partyClassificationCollection];
    }
    return partyClassificationCollection;
  }

  protected convertDateFromClient(partyClassification: IPartyClassification): IPartyClassification {
    return Object.assign({}, partyClassification, {
      fromDate: partyClassification.fromDate?.isValid() ? partyClassification.fromDate.toJSON() : undefined,
      thruDate: partyClassification.thruDate?.isValid() ? partyClassification.thruDate.toJSON() : undefined,
      createdOn: partyClassification.createdOn?.isValid() ? partyClassification.createdOn.toJSON() : undefined,
      updatedOn: partyClassification.updatedOn?.isValid() ? partyClassification.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyClassification: IPartyClassification) => {
        partyClassification.fromDate = partyClassification.fromDate ? dayjs(partyClassification.fromDate) : undefined;
        partyClassification.thruDate = partyClassification.thruDate ? dayjs(partyClassification.thruDate) : undefined;
        partyClassification.createdOn = partyClassification.createdOn ? dayjs(partyClassification.createdOn) : undefined;
        partyClassification.updatedOn = partyClassification.updatedOn ? dayjs(partyClassification.updatedOn) : undefined;
      });
    }
    return res;
  }
}
