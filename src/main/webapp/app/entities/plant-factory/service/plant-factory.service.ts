import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlantFactory, getPlantFactoryIdentifier } from '../plant-factory.model';

export type EntityResponseType = HttpResponse<IPlantFactory>;
export type EntityArrayResponseType = HttpResponse<IPlantFactory[]>;

@Injectable({ providedIn: 'root' })
export class PlantFactoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plant-factories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plantFactory: IPlantFactory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactory);
    return this.http
      .post<IPlantFactory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plantFactory: IPlantFactory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactory);
    return this.http
      .put<IPlantFactory>(`${this.resourceUrl}/${getPlantFactoryIdentifier(plantFactory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(plantFactory: IPlantFactory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactory);
    return this.http
      .patch<IPlantFactory>(`${this.resourceUrl}/${getPlantFactoryIdentifier(plantFactory) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlantFactory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlantFactory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPlantFactoryToCollectionIfMissing(
    plantFactoryCollection: IPlantFactory[],
    ...plantFactoriesToCheck: (IPlantFactory | null | undefined)[]
  ): IPlantFactory[] {
    const plantFactories: IPlantFactory[] = plantFactoriesToCheck.filter(isPresent);
    if (plantFactories.length > 0) {
      const plantFactoryCollectionIdentifiers = plantFactoryCollection.map(
        plantFactoryItem => getPlantFactoryIdentifier(plantFactoryItem)!
      );
      const plantFactoriesToAdd = plantFactories.filter(plantFactoryItem => {
        const plantFactoryIdentifier = getPlantFactoryIdentifier(plantFactoryItem);
        if (plantFactoryIdentifier == null || plantFactoryCollectionIdentifiers.includes(plantFactoryIdentifier)) {
          return false;
        }
        plantFactoryCollectionIdentifiers.push(plantFactoryIdentifier);
        return true;
      });
      return [...plantFactoriesToAdd, ...plantFactoryCollection];
    }
    return plantFactoryCollection;
  }

  protected convertDateFromClient(plantFactory: IPlantFactory): IPlantFactory {
    return Object.assign({}, plantFactory, {
      createdOn: plantFactory.createdOn?.isValid() ? plantFactory.createdOn.toJSON() : undefined,
      updatedOn: plantFactory.updatedOn?.isValid() ? plantFactory.updatedOn.toJSON() : undefined,
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
      res.body.forEach((plantFactory: IPlantFactory) => {
        plantFactory.createdOn = plantFactory.createdOn ? dayjs(plantFactory.createdOn) : undefined;
        plantFactory.updatedOn = plantFactory.updatedOn ? dayjs(plantFactory.updatedOn) : undefined;
      });
    }
    return res;
  }
}
