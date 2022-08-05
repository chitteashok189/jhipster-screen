import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlant, getPlantIdentifier } from '../plant.model';

export type EntityResponseType = HttpResponse<IPlant>;
export type EntityArrayResponseType = HttpResponse<IPlant[]>;

@Injectable({ providedIn: 'root' })
export class PlantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plant: IPlant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plant);
    return this.http
      .post<IPlant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plant: IPlant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plant);
    return this.http
      .put<IPlant>(`${this.resourceUrl}/${getPlantIdentifier(plant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(plant: IPlant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plant);
    return this.http
      .patch<IPlant>(`${this.resourceUrl}/${getPlantIdentifier(plant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPlantToCollectionIfMissing(plantCollection: IPlant[], ...plantsToCheck: (IPlant | null | undefined)[]): IPlant[] {
    const plants: IPlant[] = plantsToCheck.filter(isPresent);
    if (plants.length > 0) {
      const plantCollectionIdentifiers = plantCollection.map(plantItem => getPlantIdentifier(plantItem)!);
      const plantsToAdd = plants.filter(plantItem => {
        const plantIdentifier = getPlantIdentifier(plantItem);
        if (plantIdentifier == null || plantCollectionIdentifiers.includes(plantIdentifier)) {
          return false;
        }
        plantCollectionIdentifiers.push(plantIdentifier);
        return true;
      });
      return [...plantsToAdd, ...plantCollection];
    }
    return plantCollection;
  }

  protected convertDateFromClient(plant: IPlant): IPlant {
    return Object.assign({}, plant, {
      createdOn: plant.createdOn?.isValid() ? plant.createdOn.toJSON() : undefined,
      updatedOn: plant.updatedOn?.isValid() ? plant.updatedOn.toJSON() : undefined,
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
      res.body.forEach((plant: IPlant) => {
        plant.createdOn = plant.createdOn ? dayjs(plant.createdOn) : undefined;
        plant.updatedOn = plant.updatedOn ? dayjs(plant.updatedOn) : undefined;
      });
    }
    return res;
  }
}
