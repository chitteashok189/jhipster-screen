import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRawMaterial, getRawMaterialIdentifier } from '../raw-material.model';

export type EntityResponseType = HttpResponse<IRawMaterial>;
export type EntityArrayResponseType = HttpResponse<IRawMaterial[]>;

@Injectable({ providedIn: 'root' })
export class RawMaterialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/raw-materials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rawMaterial: IRawMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterial);
    return this.http
      .post<IRawMaterial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rawMaterial: IRawMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterial);
    return this.http
      .put<IRawMaterial>(`${this.resourceUrl}/${getRawMaterialIdentifier(rawMaterial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rawMaterial: IRawMaterial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rawMaterial);
    return this.http
      .patch<IRawMaterial>(`${this.resourceUrl}/${getRawMaterialIdentifier(rawMaterial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRawMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRawMaterial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRawMaterialToCollectionIfMissing(
    rawMaterialCollection: IRawMaterial[],
    ...rawMaterialsToCheck: (IRawMaterial | null | undefined)[]
  ): IRawMaterial[] {
    const rawMaterials: IRawMaterial[] = rawMaterialsToCheck.filter(isPresent);
    if (rawMaterials.length > 0) {
      const rawMaterialCollectionIdentifiers = rawMaterialCollection.map(rawMaterialItem => getRawMaterialIdentifier(rawMaterialItem)!);
      const rawMaterialsToAdd = rawMaterials.filter(rawMaterialItem => {
        const rawMaterialIdentifier = getRawMaterialIdentifier(rawMaterialItem);
        if (rawMaterialIdentifier == null || rawMaterialCollectionIdentifiers.includes(rawMaterialIdentifier)) {
          return false;
        }
        rawMaterialCollectionIdentifiers.push(rawMaterialIdentifier);
        return true;
      });
      return [...rawMaterialsToAdd, ...rawMaterialCollection];
    }
    return rawMaterialCollection;
  }

  protected convertDateFromClient(rawMaterial: IRawMaterial): IRawMaterial {
    return Object.assign({}, rawMaterial, {
      createdOn: rawMaterial.createdOn?.isValid() ? rawMaterial.createdOn.toJSON() : undefined,
      updatedOn: rawMaterial.updatedOn?.isValid() ? rawMaterial.updatedOn.toJSON() : undefined,
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
      res.body.forEach((rawMaterial: IRawMaterial) => {
        rawMaterial.createdOn = rawMaterial.createdOn ? dayjs(rawMaterial.createdOn) : undefined;
        rawMaterial.updatedOn = rawMaterial.updatedOn ? dayjs(rawMaterial.updatedOn) : undefined;
      });
    }
    return res;
  }
}
