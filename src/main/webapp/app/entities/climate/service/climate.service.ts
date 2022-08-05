import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClimate, getClimateIdentifier } from '../climate.model';

export type EntityResponseType = HttpResponse<IClimate>;
export type EntityArrayResponseType = HttpResponse<IClimate[]>;

@Injectable({ providedIn: 'root' })
export class ClimateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/climates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(climate: IClimate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(climate);
    return this.http
      .post<IClimate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(climate: IClimate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(climate);
    return this.http
      .put<IClimate>(`${this.resourceUrl}/${getClimateIdentifier(climate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(climate: IClimate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(climate);
    return this.http
      .patch<IClimate>(`${this.resourceUrl}/${getClimateIdentifier(climate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClimate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClimate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addClimateToCollectionIfMissing(climateCollection: IClimate[], ...climatesToCheck: (IClimate | null | undefined)[]): IClimate[] {
    const climates: IClimate[] = climatesToCheck.filter(isPresent);
    if (climates.length > 0) {
      const climateCollectionIdentifiers = climateCollection.map(climateItem => getClimateIdentifier(climateItem)!);
      const climatesToAdd = climates.filter(climateItem => {
        const climateIdentifier = getClimateIdentifier(climateItem);
        if (climateIdentifier == null || climateCollectionIdentifiers.includes(climateIdentifier)) {
          return false;
        }
        climateCollectionIdentifiers.push(climateIdentifier);
        return true;
      });
      return [...climatesToAdd, ...climateCollection];
    }
    return climateCollection;
  }

  protected convertDateFromClient(climate: IClimate): IClimate {
    return Object.assign({}, climate, {
      createdOn: climate.createdOn?.isValid() ? climate.createdOn.toJSON() : undefined,
      updatedOn: climate.updatedOn?.isValid() ? climate.updatedOn.toJSON() : undefined,
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
      res.body.forEach((climate: IClimate) => {
        climate.createdOn = climate.createdOn ? dayjs(climate.createdOn) : undefined;
        climate.updatedOn = climate.updatedOn ? dayjs(climate.updatedOn) : undefined;
      });
    }
    return res;
  }
}
