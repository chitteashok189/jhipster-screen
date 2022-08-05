import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFarm, getFarmIdentifier } from '../farm.model';

export type EntityResponseType = HttpResponse<IFarm>;
export type EntityArrayResponseType = HttpResponse<IFarm[]>;

@Injectable({ providedIn: 'root' })
export class FarmService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/farms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(farm: IFarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(farm);
    return this.http
      .post<IFarm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(farm: IFarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(farm);
    return this.http
      .put<IFarm>(`${this.resourceUrl}/${getFarmIdentifier(farm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(farm: IFarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(farm);
    return this.http
      .patch<IFarm>(`${this.resourceUrl}/${getFarmIdentifier(farm) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFarm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFarm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFarmToCollectionIfMissing(farmCollection: IFarm[], ...farmsToCheck: (IFarm | null | undefined)[]): IFarm[] {
    const farms: IFarm[] = farmsToCheck.filter(isPresent);
    if (farms.length > 0) {
      const farmCollectionIdentifiers = farmCollection.map(farmItem => getFarmIdentifier(farmItem)!);
      const farmsToAdd = farms.filter(farmItem => {
        const farmIdentifier = getFarmIdentifier(farmItem);
        if (farmIdentifier == null || farmCollectionIdentifiers.includes(farmIdentifier)) {
          return false;
        }
        farmCollectionIdentifiers.push(farmIdentifier);
        return true;
      });
      return [...farmsToAdd, ...farmCollection];
    }
    return farmCollection;
  }

  protected convertDateFromClient(farm: IFarm): IFarm {
    return Object.assign({}, farm, {
      createdOn: farm.createdOn?.isValid() ? farm.createdOn.toJSON() : undefined,
      updatedOn: farm.updatedOn?.isValid() ? farm.updatedOn.toJSON() : undefined,
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
      res.body.forEach((farm: IFarm) => {
        farm.createdOn = farm.createdOn ? dayjs(farm.createdOn) : undefined;
        farm.updatedOn = farm.updatedOn ? dayjs(farm.updatedOn) : undefined;
      });
    }
    return res;
  }
}
