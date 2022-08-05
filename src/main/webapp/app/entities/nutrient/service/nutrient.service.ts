import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INutrient, getNutrientIdentifier } from '../nutrient.model';

export type EntityResponseType = HttpResponse<INutrient>;
export type EntityArrayResponseType = HttpResponse<INutrient[]>;

@Injectable({ providedIn: 'root' })
export class NutrientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nutrients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nutrient: INutrient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nutrient);
    return this.http
      .post<INutrient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nutrient: INutrient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nutrient);
    return this.http
      .put<INutrient>(`${this.resourceUrl}/${getNutrientIdentifier(nutrient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(nutrient: INutrient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nutrient);
    return this.http
      .patch<INutrient>(`${this.resourceUrl}/${getNutrientIdentifier(nutrient) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INutrient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INutrient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNutrientToCollectionIfMissing(nutrientCollection: INutrient[], ...nutrientsToCheck: (INutrient | null | undefined)[]): INutrient[] {
    const nutrients: INutrient[] = nutrientsToCheck.filter(isPresent);
    if (nutrients.length > 0) {
      const nutrientCollectionIdentifiers = nutrientCollection.map(nutrientItem => getNutrientIdentifier(nutrientItem)!);
      const nutrientsToAdd = nutrients.filter(nutrientItem => {
        const nutrientIdentifier = getNutrientIdentifier(nutrientItem);
        if (nutrientIdentifier == null || nutrientCollectionIdentifiers.includes(nutrientIdentifier)) {
          return false;
        }
        nutrientCollectionIdentifiers.push(nutrientIdentifier);
        return true;
      });
      return [...nutrientsToAdd, ...nutrientCollection];
    }
    return nutrientCollection;
  }

  protected convertDateFromClient(nutrient: INutrient): INutrient {
    return Object.assign({}, nutrient, {
      createdOn: nutrient.createdOn?.isValid() ? nutrient.createdOn.toJSON() : undefined,
      updatedOn: nutrient.updatedOn?.isValid() ? nutrient.updatedOn.toJSON() : undefined,
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
      res.body.forEach((nutrient: INutrient) => {
        nutrient.createdOn = nutrient.createdOn ? dayjs(nutrient.createdOn) : undefined;
        nutrient.updatedOn = nutrient.updatedOn ? dayjs(nutrient.updatedOn) : undefined;
      });
    }
    return res;
  }
}
