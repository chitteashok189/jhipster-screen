import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrowBed, getGrowBedIdentifier } from '../grow-bed.model';

export type EntityResponseType = HttpResponse<IGrowBed>;
export type EntityArrayResponseType = HttpResponse<IGrowBed[]>;

@Injectable({ providedIn: 'root' })
export class GrowBedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grow-beds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(growBed: IGrowBed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(growBed);
    return this.http
      .post<IGrowBed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(growBed: IGrowBed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(growBed);
    return this.http
      .put<IGrowBed>(`${this.resourceUrl}/${getGrowBedIdentifier(growBed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(growBed: IGrowBed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(growBed);
    return this.http
      .patch<IGrowBed>(`${this.resourceUrl}/${getGrowBedIdentifier(growBed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGrowBed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGrowBed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGrowBedToCollectionIfMissing(growBedCollection: IGrowBed[], ...growBedsToCheck: (IGrowBed | null | undefined)[]): IGrowBed[] {
    const growBeds: IGrowBed[] = growBedsToCheck.filter(isPresent);
    if (growBeds.length > 0) {
      const growBedCollectionIdentifiers = growBedCollection.map(growBedItem => getGrowBedIdentifier(growBedItem)!);
      const growBedsToAdd = growBeds.filter(growBedItem => {
        const growBedIdentifier = getGrowBedIdentifier(growBedItem);
        if (growBedIdentifier == null || growBedCollectionIdentifiers.includes(growBedIdentifier)) {
          return false;
        }
        growBedCollectionIdentifiers.push(growBedIdentifier);
        return true;
      });
      return [...growBedsToAdd, ...growBedCollection];
    }
    return growBedCollection;
  }

  protected convertDateFromClient(growBed: IGrowBed): IGrowBed {
    return Object.assign({}, growBed, {
      createdOn: growBed.createdOn?.isValid() ? growBed.createdOn.toJSON() : undefined,
      updatedOn: growBed.updatedOn?.isValid() ? growBed.updatedOn.toJSON() : undefined,
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
      res.body.forEach((growBed: IGrowBed) => {
        growBed.createdOn = growBed.createdOn ? dayjs(growBed.createdOn) : undefined;
        growBed.updatedOn = growBed.updatedOn ? dayjs(growBed.updatedOn) : undefined;
      });
    }
    return res;
  }
}
