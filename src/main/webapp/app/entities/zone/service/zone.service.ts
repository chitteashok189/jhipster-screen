import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IZone, getZoneIdentifier } from '../zone.model';

export type EntityResponseType = HttpResponse<IZone>;
export type EntityArrayResponseType = HttpResponse<IZone[]>;

@Injectable({ providedIn: 'root' })
export class ZoneService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/zones');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(zone: IZone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zone);
    return this.http
      .post<IZone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(zone: IZone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zone);
    return this.http
      .put<IZone>(`${this.resourceUrl}/${getZoneIdentifier(zone) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(zone: IZone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zone);
    return this.http
      .patch<IZone>(`${this.resourceUrl}/${getZoneIdentifier(zone) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IZone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IZone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addZoneToCollectionIfMissing(zoneCollection: IZone[], ...zonesToCheck: (IZone | null | undefined)[]): IZone[] {
    const zones: IZone[] = zonesToCheck.filter(isPresent);
    if (zones.length > 0) {
      const zoneCollectionIdentifiers = zoneCollection.map(zoneItem => getZoneIdentifier(zoneItem)!);
      const zonesToAdd = zones.filter(zoneItem => {
        const zoneIdentifier = getZoneIdentifier(zoneItem);
        if (zoneIdentifier == null || zoneCollectionIdentifiers.includes(zoneIdentifier)) {
          return false;
        }
        zoneCollectionIdentifiers.push(zoneIdentifier);
        return true;
      });
      return [...zonesToAdd, ...zoneCollection];
    }
    return zoneCollection;
  }

  protected convertDateFromClient(zone: IZone): IZone {
    return Object.assign({}, zone, {
      createdOn: zone.createdOn?.isValid() ? zone.createdOn.toJSON() : undefined,
      updatedOn: zone.updatedOn?.isValid() ? zone.updatedOn.toJSON() : undefined,
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
      res.body.forEach((zone: IZone) => {
        zone.createdOn = zone.createdOn ? dayjs(zone.createdOn) : undefined;
        zone.updatedOn = zone.updatedOn ? dayjs(zone.updatedOn) : undefined;
      });
    }
    return res;
  }
}
