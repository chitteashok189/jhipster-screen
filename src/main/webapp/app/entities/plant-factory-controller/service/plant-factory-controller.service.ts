import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlantFactoryController, getPlantFactoryControllerIdentifier } from '../plant-factory-controller.model';

export type EntityResponseType = HttpResponse<IPlantFactoryController>;
export type EntityArrayResponseType = HttpResponse<IPlantFactoryController[]>;

@Injectable({ providedIn: 'root' })
export class PlantFactoryControllerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plant-factory-controllers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(plantFactoryController: IPlantFactoryController): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactoryController);
    return this.http
      .post<IPlantFactoryController>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plantFactoryController: IPlantFactoryController): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactoryController);
    return this.http
      .put<IPlantFactoryController>(`${this.resourceUrl}/${getPlantFactoryControllerIdentifier(plantFactoryController) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(plantFactoryController: IPlantFactoryController): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plantFactoryController);
    return this.http
      .patch<IPlantFactoryController>(
        `${this.resourceUrl}/${getPlantFactoryControllerIdentifier(plantFactoryController) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlantFactoryController>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlantFactoryController[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPlantFactoryControllerToCollectionIfMissing(
    plantFactoryControllerCollection: IPlantFactoryController[],
    ...plantFactoryControllersToCheck: (IPlantFactoryController | null | undefined)[]
  ): IPlantFactoryController[] {
    const plantFactoryControllers: IPlantFactoryController[] = plantFactoryControllersToCheck.filter(isPresent);
    if (plantFactoryControllers.length > 0) {
      const plantFactoryControllerCollectionIdentifiers = plantFactoryControllerCollection.map(
        plantFactoryControllerItem => getPlantFactoryControllerIdentifier(plantFactoryControllerItem)!
      );
      const plantFactoryControllersToAdd = plantFactoryControllers.filter(plantFactoryControllerItem => {
        const plantFactoryControllerIdentifier = getPlantFactoryControllerIdentifier(plantFactoryControllerItem);
        if (
          plantFactoryControllerIdentifier == null ||
          plantFactoryControllerCollectionIdentifiers.includes(plantFactoryControllerIdentifier)
        ) {
          return false;
        }
        plantFactoryControllerCollectionIdentifiers.push(plantFactoryControllerIdentifier);
        return true;
      });
      return [...plantFactoryControllersToAdd, ...plantFactoryControllerCollection];
    }
    return plantFactoryControllerCollection;
  }

  protected convertDateFromClient(plantFactoryController: IPlantFactoryController): IPlantFactoryController {
    return Object.assign({}, plantFactoryController, {
      createdOn: plantFactoryController.createdOn?.isValid() ? plantFactoryController.createdOn.toJSON() : undefined,
      updatedOn: plantFactoryController.updatedOn?.isValid() ? plantFactoryController.updatedOn.toJSON() : undefined,
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
      res.body.forEach((plantFactoryController: IPlantFactoryController) => {
        plantFactoryController.createdOn = plantFactoryController.createdOn ? dayjs(plantFactoryController.createdOn) : undefined;
        plantFactoryController.updatedOn = plantFactoryController.updatedOn ? dayjs(plantFactoryController.updatedOn) : undefined;
      });
    }
    return res;
  }
}
