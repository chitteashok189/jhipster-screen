import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISensorModel, getSensorModelIdentifier } from '../sensor-model.model';

export type EntityResponseType = HttpResponse<ISensorModel>;
export type EntityArrayResponseType = HttpResponse<ISensorModel[]>;

@Injectable({ providedIn: 'root' })
export class SensorModelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sensor-models');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sensorModel: ISensorModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensorModel);
    return this.http
      .post<ISensorModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sensorModel: ISensorModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensorModel);
    return this.http
      .put<ISensorModel>(`${this.resourceUrl}/${getSensorModelIdentifier(sensorModel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sensorModel: ISensorModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensorModel);
    return this.http
      .patch<ISensorModel>(`${this.resourceUrl}/${getSensorModelIdentifier(sensorModel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISensorModel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISensorModel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSensorModelToCollectionIfMissing(
    sensorModelCollection: ISensorModel[],
    ...sensorModelsToCheck: (ISensorModel | null | undefined)[]
  ): ISensorModel[] {
    const sensorModels: ISensorModel[] = sensorModelsToCheck.filter(isPresent);
    if (sensorModels.length > 0) {
      const sensorModelCollectionIdentifiers = sensorModelCollection.map(sensorModelItem => getSensorModelIdentifier(sensorModelItem)!);
      const sensorModelsToAdd = sensorModels.filter(sensorModelItem => {
        const sensorModelIdentifier = getSensorModelIdentifier(sensorModelItem);
        if (sensorModelIdentifier == null || sensorModelCollectionIdentifiers.includes(sensorModelIdentifier)) {
          return false;
        }
        sensorModelCollectionIdentifiers.push(sensorModelIdentifier);
        return true;
      });
      return [...sensorModelsToAdd, ...sensorModelCollection];
    }
    return sensorModelCollection;
  }

  protected convertDateFromClient(sensorModel: ISensorModel): ISensorModel {
    return Object.assign({}, sensorModel, {
      createdOn: sensorModel.createdOn?.isValid() ? sensorModel.createdOn.toJSON() : undefined,
      updatedOn: sensorModel.updatedOn?.isValid() ? sensorModel.updatedOn.toJSON() : undefined,
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
      res.body.forEach((sensorModel: ISensorModel) => {
        sensorModel.createdOn = sensorModel.createdOn ? dayjs(sensorModel.createdOn) : undefined;
        sensorModel.updatedOn = sensorModel.updatedOn ? dayjs(sensorModel.updatedOn) : undefined;
      });
    }
    return res;
  }
}
