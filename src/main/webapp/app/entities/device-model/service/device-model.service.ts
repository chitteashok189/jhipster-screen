import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeviceModel, getDeviceModelIdentifier } from '../device-model.model';

export type EntityResponseType = HttpResponse<IDeviceModel>;
export type EntityArrayResponseType = HttpResponse<IDeviceModel[]>;

@Injectable({ providedIn: 'root' })
export class DeviceModelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/device-models');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(deviceModel: IDeviceModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceModel);
    return this.http
      .post<IDeviceModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deviceModel: IDeviceModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceModel);
    return this.http
      .put<IDeviceModel>(`${this.resourceUrl}/${getDeviceModelIdentifier(deviceModel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(deviceModel: IDeviceModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceModel);
    return this.http
      .patch<IDeviceModel>(`${this.resourceUrl}/${getDeviceModelIdentifier(deviceModel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeviceModel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeviceModel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDeviceModelToCollectionIfMissing(
    deviceModelCollection: IDeviceModel[],
    ...deviceModelsToCheck: (IDeviceModel | null | undefined)[]
  ): IDeviceModel[] {
    const deviceModels: IDeviceModel[] = deviceModelsToCheck.filter(isPresent);
    if (deviceModels.length > 0) {
      const deviceModelCollectionIdentifiers = deviceModelCollection.map(deviceModelItem => getDeviceModelIdentifier(deviceModelItem)!);
      const deviceModelsToAdd = deviceModels.filter(deviceModelItem => {
        const deviceModelIdentifier = getDeviceModelIdentifier(deviceModelItem);
        if (deviceModelIdentifier == null || deviceModelCollectionIdentifiers.includes(deviceModelIdentifier)) {
          return false;
        }
        deviceModelCollectionIdentifiers.push(deviceModelIdentifier);
        return true;
      });
      return [...deviceModelsToAdd, ...deviceModelCollection];
    }
    return deviceModelCollection;
  }

  protected convertDateFromClient(deviceModel: IDeviceModel): IDeviceModel {
    return Object.assign({}, deviceModel, {
      createdOn: deviceModel.createdOn?.isValid() ? deviceModel.createdOn.toJSON() : undefined,
      updatedOn: deviceModel.updatedOn?.isValid() ? deviceModel.updatedOn.toJSON() : undefined,
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
      res.body.forEach((deviceModel: IDeviceModel) => {
        deviceModel.createdOn = deviceModel.createdOn ? dayjs(deviceModel.createdOn) : undefined;
        deviceModel.updatedOn = deviceModel.updatedOn ? dayjs(deviceModel.updatedOn) : undefined;
      });
    }
    return res;
  }
}
