import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPest, getPestIdentifier } from '../pest.model';

export type EntityResponseType = HttpResponse<IPest>;
export type EntityArrayResponseType = HttpResponse<IPest[]>;

@Injectable({ providedIn: 'root' })
export class PestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pest: IPest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pest);
    return this.http
      .post<IPest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pest: IPest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pest);
    return this.http
      .put<IPest>(`${this.resourceUrl}/${getPestIdentifier(pest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pest: IPest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pest);
    return this.http
      .patch<IPest>(`${this.resourceUrl}/${getPestIdentifier(pest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPestToCollectionIfMissing(pestCollection: IPest[], ...pestsToCheck: (IPest | null | undefined)[]): IPest[] {
    const pests: IPest[] = pestsToCheck.filter(isPresent);
    if (pests.length > 0) {
      const pestCollectionIdentifiers = pestCollection.map(pestItem => getPestIdentifier(pestItem)!);
      const pestsToAdd = pests.filter(pestItem => {
        const pestIdentifier = getPestIdentifier(pestItem);
        if (pestIdentifier == null || pestCollectionIdentifiers.includes(pestIdentifier)) {
          return false;
        }
        pestCollectionIdentifiers.push(pestIdentifier);
        return true;
      });
      return [...pestsToAdd, ...pestCollection];
    }
    return pestCollection;
  }

  protected convertDateFromClient(pest: IPest): IPest {
    return Object.assign({}, pest, {
      createdOn: pest.createdOn?.isValid() ? pest.createdOn.toJSON() : undefined,
      updatedOn: pest.updatedOn?.isValid() ? pest.updatedOn.toJSON() : undefined,
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
      res.body.forEach((pest: IPest) => {
        pest.createdOn = pest.createdOn ? dayjs(pest.createdOn) : undefined;
        pest.updatedOn = pest.updatedOn ? dayjs(pest.updatedOn) : undefined;
      });
    }
    return res;
  }
}
