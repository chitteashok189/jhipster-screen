import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISensor, getSensorIdentifier } from '../sensor.model';

export type EntityResponseType = HttpResponse<ISensor>;
export type EntityArrayResponseType = HttpResponse<ISensor[]>;

@Injectable({ providedIn: 'root' })
export class SensorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sensors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sensor: ISensor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensor);
    return this.http
      .post<ISensor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sensor: ISensor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensor);
    return this.http
      .put<ISensor>(`${this.resourceUrl}/${getSensorIdentifier(sensor) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sensor: ISensor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sensor);
    return this.http
      .patch<ISensor>(`${this.resourceUrl}/${getSensorIdentifier(sensor) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISensor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISensor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSensorToCollectionIfMissing(sensorCollection: ISensor[], ...sensorsToCheck: (ISensor | null | undefined)[]): ISensor[] {
    const sensors: ISensor[] = sensorsToCheck.filter(isPresent);
    if (sensors.length > 0) {
      const sensorCollectionIdentifiers = sensorCollection.map(sensorItem => getSensorIdentifier(sensorItem)!);
      const sensorsToAdd = sensors.filter(sensorItem => {
        const sensorIdentifier = getSensorIdentifier(sensorItem);
        if (sensorIdentifier == null || sensorCollectionIdentifiers.includes(sensorIdentifier)) {
          return false;
        }
        sensorCollectionIdentifiers.push(sensorIdentifier);
        return true;
      });
      return [...sensorsToAdd, ...sensorCollection];
    }
    return sensorCollection;
  }

  protected convertDateFromClient(sensor: ISensor): ISensor {
    return Object.assign({}, sensor, {
      createdOn: sensor.createdOn?.isValid() ? sensor.createdOn.toJSON() : undefined,
      updatedOn: sensor.updatedOn?.isValid() ? sensor.updatedOn.toJSON() : undefined,
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
      res.body.forEach((sensor: ISensor) => {
        sensor.createdOn = sensor.createdOn ? dayjs(sensor.createdOn) : undefined;
        sensor.updatedOn = sensor.updatedOn ? dayjs(sensor.updatedOn) : undefined;
      });
    }
    return res;
  }
}
