import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeviceLevel, getDeviceLevelIdentifier } from '../device-level.model';

export type EntityResponseType = HttpResponse<IDeviceLevel>;
export type EntityArrayResponseType = HttpResponse<IDeviceLevel[]>;

@Injectable({ providedIn: 'root' })
export class DeviceLevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/device-levels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(deviceLevel: IDeviceLevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceLevel);
    return this.http
      .post<IDeviceLevel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deviceLevel: IDeviceLevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceLevel);
    return this.http
      .put<IDeviceLevel>(`${this.resourceUrl}/${getDeviceLevelIdentifier(deviceLevel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(deviceLevel: IDeviceLevel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceLevel);
    return this.http
      .patch<IDeviceLevel>(`${this.resourceUrl}/${getDeviceLevelIdentifier(deviceLevel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeviceLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeviceLevel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDeviceLevelToCollectionIfMissing(
    deviceLevelCollection: IDeviceLevel[],
    ...deviceLevelsToCheck: (IDeviceLevel | null | undefined)[]
  ): IDeviceLevel[] {
    const deviceLevels: IDeviceLevel[] = deviceLevelsToCheck.filter(isPresent);
    if (deviceLevels.length > 0) {
      const deviceLevelCollectionIdentifiers = deviceLevelCollection.map(deviceLevelItem => getDeviceLevelIdentifier(deviceLevelItem)!);
      const deviceLevelsToAdd = deviceLevels.filter(deviceLevelItem => {
        const deviceLevelIdentifier = getDeviceLevelIdentifier(deviceLevelItem);
        if (deviceLevelIdentifier == null || deviceLevelCollectionIdentifiers.includes(deviceLevelIdentifier)) {
          return false;
        }
        deviceLevelCollectionIdentifiers.push(deviceLevelIdentifier);
        return true;
      });
      return [...deviceLevelsToAdd, ...deviceLevelCollection];
    }
    return deviceLevelCollection;
  }

  protected convertDateFromClient(deviceLevel: IDeviceLevel): IDeviceLevel {
    return Object.assign({}, deviceLevel, {
      createdOn: deviceLevel.createdOn?.isValid() ? deviceLevel.createdOn.toJSON() : undefined,
      updatedOn: deviceLevel.updatedOn?.isValid() ? deviceLevel.updatedOn.toJSON() : undefined,
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
      res.body.forEach((deviceLevel: IDeviceLevel) => {
        deviceLevel.createdOn = deviceLevel.createdOn ? dayjs(deviceLevel.createdOn) : undefined;
        deviceLevel.updatedOn = deviceLevel.updatedOn ? dayjs(deviceLevel.updatedOn) : undefined;
      });
    }
    return res;
  }
}
