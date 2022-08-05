import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWeather, getWeatherIdentifier } from '../weather.model';

export type EntityResponseType = HttpResponse<IWeather>;
export type EntityArrayResponseType = HttpResponse<IWeather[]>;

@Injectable({ providedIn: 'root' })
export class WeatherService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/weathers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(weather: IWeather): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weather);
    return this.http
      .post<IWeather>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(weather: IWeather): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weather);
    return this.http
      .put<IWeather>(`${this.resourceUrl}/${getWeatherIdentifier(weather) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(weather: IWeather): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weather);
    return this.http
      .patch<IWeather>(`${this.resourceUrl}/${getWeatherIdentifier(weather) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWeather>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWeather[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWeatherToCollectionIfMissing(weatherCollection: IWeather[], ...weathersToCheck: (IWeather | null | undefined)[]): IWeather[] {
    const weathers: IWeather[] = weathersToCheck.filter(isPresent);
    if (weathers.length > 0) {
      const weatherCollectionIdentifiers = weatherCollection.map(weatherItem => getWeatherIdentifier(weatherItem)!);
      const weathersToAdd = weathers.filter(weatherItem => {
        const weatherIdentifier = getWeatherIdentifier(weatherItem);
        if (weatherIdentifier == null || weatherCollectionIdentifiers.includes(weatherIdentifier)) {
          return false;
        }
        weatherCollectionIdentifiers.push(weatherIdentifier);
        return true;
      });
      return [...weathersToAdd, ...weatherCollection];
    }
    return weatherCollection;
  }

  protected convertDateFromClient(weather: IWeather): IWeather {
    return Object.assign({}, weather, {
      createdOn: weather.createdOn?.isValid() ? weather.createdOn.toJSON() : undefined,
      updatedOn: weather.updatedOn?.isValid() ? weather.updatedOn.toJSON() : undefined,
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
      res.body.forEach((weather: IWeather) => {
        weather.createdOn = weather.createdOn ? dayjs(weather.createdOn) : undefined;
        weather.updatedOn = weather.updatedOn ? dayjs(weather.updatedOn) : undefined;
      });
    }
    return res;
  }
}
