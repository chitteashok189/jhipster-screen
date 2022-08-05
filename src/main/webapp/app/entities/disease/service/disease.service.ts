import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDisease, getDiseaseIdentifier } from '../disease.model';

export type EntityResponseType = HttpResponse<IDisease>;
export type EntityArrayResponseType = HttpResponse<IDisease[]>;

@Injectable({ providedIn: 'root' })
export class DiseaseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/diseases');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(disease: IDisease): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disease);
    return this.http
      .post<IDisease>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(disease: IDisease): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disease);
    return this.http
      .put<IDisease>(`${this.resourceUrl}/${getDiseaseIdentifier(disease) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(disease: IDisease): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disease);
    return this.http
      .patch<IDisease>(`${this.resourceUrl}/${getDiseaseIdentifier(disease) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDisease>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDisease[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDiseaseToCollectionIfMissing(diseaseCollection: IDisease[], ...diseasesToCheck: (IDisease | null | undefined)[]): IDisease[] {
    const diseases: IDisease[] = diseasesToCheck.filter(isPresent);
    if (diseases.length > 0) {
      const diseaseCollectionIdentifiers = diseaseCollection.map(diseaseItem => getDiseaseIdentifier(diseaseItem)!);
      const diseasesToAdd = diseases.filter(diseaseItem => {
        const diseaseIdentifier = getDiseaseIdentifier(diseaseItem);
        if (diseaseIdentifier == null || diseaseCollectionIdentifiers.includes(diseaseIdentifier)) {
          return false;
        }
        diseaseCollectionIdentifiers.push(diseaseIdentifier);
        return true;
      });
      return [...diseasesToAdd, ...diseaseCollection];
    }
    return diseaseCollection;
  }

  protected convertDateFromClient(disease: IDisease): IDisease {
    return Object.assign({}, disease, {
      createdOn: disease.createdOn?.isValid() ? disease.createdOn.toJSON() : undefined,
      updatedOn: disease.updatedOn?.isValid() ? disease.updatedOn.toJSON() : undefined,
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
      res.body.forEach((disease: IDisease) => {
        disease.createdOn = disease.createdOn ? dayjs(disease.createdOn) : undefined;
        disease.updatedOn = disease.updatedOn ? dayjs(disease.updatedOn) : undefined;
      });
    }
    return res;
  }
}
