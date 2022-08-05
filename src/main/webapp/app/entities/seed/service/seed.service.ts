import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISeed, getSeedIdentifier } from '../seed.model';

export type EntityResponseType = HttpResponse<ISeed>;
export type EntityArrayResponseType = HttpResponse<ISeed[]>;

@Injectable({ providedIn: 'root' })
export class SeedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/seeds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(seed: ISeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seed);
    return this.http
      .post<ISeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(seed: ISeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seed);
    return this.http
      .put<ISeed>(`${this.resourceUrl}/${getSeedIdentifier(seed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(seed: ISeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seed);
    return this.http
      .patch<ISeed>(`${this.resourceUrl}/${getSeedIdentifier(seed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISeed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISeed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSeedToCollectionIfMissing(seedCollection: ISeed[], ...seedsToCheck: (ISeed | null | undefined)[]): ISeed[] {
    const seeds: ISeed[] = seedsToCheck.filter(isPresent);
    if (seeds.length > 0) {
      const seedCollectionIdentifiers = seedCollection.map(seedItem => getSeedIdentifier(seedItem)!);
      const seedsToAdd = seeds.filter(seedItem => {
        const seedIdentifier = getSeedIdentifier(seedItem);
        if (seedIdentifier == null || seedCollectionIdentifiers.includes(seedIdentifier)) {
          return false;
        }
        seedCollectionIdentifiers.push(seedIdentifier);
        return true;
      });
      return [...seedsToAdd, ...seedCollection];
    }
    return seedCollection;
  }

  protected convertDateFromClient(seed: ISeed): ISeed {
    return Object.assign({}, seed, {
      createdOn: seed.createdOn?.isValid() ? seed.createdOn.toJSON() : undefined,
      updatedOn: seed.updatedOn?.isValid() ? seed.updatedOn.toJSON() : undefined,
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
      res.body.forEach((seed: ISeed) => {
        seed.createdOn = seed.createdOn ? dayjs(seed.createdOn) : undefined;
        seed.updatedOn = seed.updatedOn ? dayjs(seed.updatedOn) : undefined;
      });
    }
    return res;
  }
}
