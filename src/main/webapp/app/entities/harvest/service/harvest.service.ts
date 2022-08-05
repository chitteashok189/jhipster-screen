import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHarvest, getHarvestIdentifier } from '../harvest.model';

export type EntityResponseType = HttpResponse<IHarvest>;
export type EntityArrayResponseType = HttpResponse<IHarvest[]>;

@Injectable({ providedIn: 'root' })
export class HarvestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/harvests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(harvest: IHarvest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(harvest);
    return this.http
      .post<IHarvest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(harvest: IHarvest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(harvest);
    return this.http
      .put<IHarvest>(`${this.resourceUrl}/${getHarvestIdentifier(harvest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(harvest: IHarvest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(harvest);
    return this.http
      .patch<IHarvest>(`${this.resourceUrl}/${getHarvestIdentifier(harvest) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHarvest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHarvest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHarvestToCollectionIfMissing(harvestCollection: IHarvest[], ...harvestsToCheck: (IHarvest | null | undefined)[]): IHarvest[] {
    const harvests: IHarvest[] = harvestsToCheck.filter(isPresent);
    if (harvests.length > 0) {
      const harvestCollectionIdentifiers = harvestCollection.map(harvestItem => getHarvestIdentifier(harvestItem)!);
      const harvestsToAdd = harvests.filter(harvestItem => {
        const harvestIdentifier = getHarvestIdentifier(harvestItem);
        if (harvestIdentifier == null || harvestCollectionIdentifiers.includes(harvestIdentifier)) {
          return false;
        }
        harvestCollectionIdentifiers.push(harvestIdentifier);
        return true;
      });
      return [...harvestsToAdd, ...harvestCollection];
    }
    return harvestCollection;
  }

  protected convertDateFromClient(harvest: IHarvest): IHarvest {
    return Object.assign({}, harvest, {
      harvestingDate: harvest.harvestingDate?.isValid() ? harvest.harvestingDate.toJSON() : undefined,
      createdOn: harvest.createdOn?.isValid() ? harvest.createdOn.toJSON() : undefined,
      updatedOn: harvest.updatedOn?.isValid() ? harvest.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.harvestingDate = res.body.harvestingDate ? dayjs(res.body.harvestingDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((harvest: IHarvest) => {
        harvest.harvestingDate = harvest.harvestingDate ? dayjs(harvest.harvestingDate) : undefined;
        harvest.createdOn = harvest.createdOn ? dayjs(harvest.createdOn) : undefined;
        harvest.updatedOn = harvest.updatedOn ? dayjs(harvest.updatedOn) : undefined;
      });
    }
    return res;
  }
}
