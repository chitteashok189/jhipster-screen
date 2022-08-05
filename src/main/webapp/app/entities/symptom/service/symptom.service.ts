import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISymptom, getSymptomIdentifier } from '../symptom.model';

export type EntityResponseType = HttpResponse<ISymptom>;
export type EntityArrayResponseType = HttpResponse<ISymptom[]>;

@Injectable({ providedIn: 'root' })
export class SymptomService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/symptoms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(symptom: ISymptom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(symptom);
    return this.http
      .post<ISymptom>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(symptom: ISymptom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(symptom);
    return this.http
      .put<ISymptom>(`${this.resourceUrl}/${getSymptomIdentifier(symptom) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(symptom: ISymptom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(symptom);
    return this.http
      .patch<ISymptom>(`${this.resourceUrl}/${getSymptomIdentifier(symptom) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISymptom>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISymptom[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSymptomToCollectionIfMissing(symptomCollection: ISymptom[], ...symptomsToCheck: (ISymptom | null | undefined)[]): ISymptom[] {
    const symptoms: ISymptom[] = symptomsToCheck.filter(isPresent);
    if (symptoms.length > 0) {
      const symptomCollectionIdentifiers = symptomCollection.map(symptomItem => getSymptomIdentifier(symptomItem)!);
      const symptomsToAdd = symptoms.filter(symptomItem => {
        const symptomIdentifier = getSymptomIdentifier(symptomItem);
        if (symptomIdentifier == null || symptomCollectionIdentifiers.includes(symptomIdentifier)) {
          return false;
        }
        symptomCollectionIdentifiers.push(symptomIdentifier);
        return true;
      });
      return [...symptomsToAdd, ...symptomCollection];
    }
    return symptomCollection;
  }

  protected convertDateFromClient(symptom: ISymptom): ISymptom {
    return Object.assign({}, symptom, {
      createdOn: symptom.createdOn?.isValid() ? symptom.createdOn.toJSON() : undefined,
      updatedOn: symptom.updatedOn?.isValid() ? symptom.updatedOn.toJSON() : undefined,
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
      res.body.forEach((symptom: ISymptom) => {
        symptom.createdOn = symptom.createdOn ? dayjs(symptom.createdOn) : undefined;
        symptom.updatedOn = symptom.updatedOn ? dayjs(symptom.updatedOn) : undefined;
      });
    }
    return res;
  }
}
