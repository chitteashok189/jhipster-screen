import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILight, getLightIdentifier } from '../light.model';

export type EntityResponseType = HttpResponse<ILight>;
export type EntityArrayResponseType = HttpResponse<ILight[]>;

@Injectable({ providedIn: 'root' })
export class LightService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lights');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(light: ILight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(light);
    return this.http
      .post<ILight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(light: ILight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(light);
    return this.http
      .put<ILight>(`${this.resourceUrl}/${getLightIdentifier(light) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(light: ILight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(light);
    return this.http
      .patch<ILight>(`${this.resourceUrl}/${getLightIdentifier(light) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILight>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILight[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLightToCollectionIfMissing(lightCollection: ILight[], ...lightsToCheck: (ILight | null | undefined)[]): ILight[] {
    const lights: ILight[] = lightsToCheck.filter(isPresent);
    if (lights.length > 0) {
      const lightCollectionIdentifiers = lightCollection.map(lightItem => getLightIdentifier(lightItem)!);
      const lightsToAdd = lights.filter(lightItem => {
        const lightIdentifier = getLightIdentifier(lightItem);
        if (lightIdentifier == null || lightCollectionIdentifiers.includes(lightIdentifier)) {
          return false;
        }
        lightCollectionIdentifiers.push(lightIdentifier);
        return true;
      });
      return [...lightsToAdd, ...lightCollection];
    }
    return lightCollection;
  }

  protected convertDateFromClient(light: ILight): ILight {
    return Object.assign({}, light, {
      createdOn: light.createdOn?.isValid() ? light.createdOn.toJSON() : undefined,
      updatedOn: light.updatedOn?.isValid() ? light.updatedOn.toJSON() : undefined,
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
      res.body.forEach((light: ILight) => {
        light.createdOn = light.createdOn ? dayjs(light.createdOn) : undefined;
        light.updatedOn = light.updatedOn ? dayjs(light.updatedOn) : undefined;
      });
    }
    return res;
  }
}
