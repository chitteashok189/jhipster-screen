import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDisorder, getDisorderIdentifier } from '../disorder.model';

export type EntityResponseType = HttpResponse<IDisorder>;
export type EntityArrayResponseType = HttpResponse<IDisorder[]>;

@Injectable({ providedIn: 'root' })
export class DisorderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/disorders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(disorder: IDisorder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disorder);
    return this.http
      .post<IDisorder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(disorder: IDisorder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disorder);
    return this.http
      .put<IDisorder>(`${this.resourceUrl}/${getDisorderIdentifier(disorder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(disorder: IDisorder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disorder);
    return this.http
      .patch<IDisorder>(`${this.resourceUrl}/${getDisorderIdentifier(disorder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDisorder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDisorder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDisorderToCollectionIfMissing(disorderCollection: IDisorder[], ...disordersToCheck: (IDisorder | null | undefined)[]): IDisorder[] {
    const disorders: IDisorder[] = disordersToCheck.filter(isPresent);
    if (disorders.length > 0) {
      const disorderCollectionIdentifiers = disorderCollection.map(disorderItem => getDisorderIdentifier(disorderItem)!);
      const disordersToAdd = disorders.filter(disorderItem => {
        const disorderIdentifier = getDisorderIdentifier(disorderItem);
        if (disorderIdentifier == null || disorderCollectionIdentifiers.includes(disorderIdentifier)) {
          return false;
        }
        disorderCollectionIdentifiers.push(disorderIdentifier);
        return true;
      });
      return [...disordersToAdd, ...disorderCollection];
    }
    return disorderCollection;
  }

  protected convertDateFromClient(disorder: IDisorder): IDisorder {
    return Object.assign({}, disorder, {
      createdOn: disorder.createdOn?.isValid() ? disorder.createdOn.toJSON() : undefined,
      updatedOn: disorder.updatedOn?.isValid() ? disorder.updatedOn.toJSON() : undefined,
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
      res.body.forEach((disorder: IDisorder) => {
        disorder.createdOn = disorder.createdOn ? dayjs(disorder.createdOn) : undefined;
        disorder.updatedOn = disorder.updatedOn ? dayjs(disorder.updatedOn) : undefined;
      });
    }
    return res;
  }
}
