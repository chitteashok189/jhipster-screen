import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiseaseControl, getDiseaseControlIdentifier } from '../disease-control.model';

export type EntityResponseType = HttpResponse<IDiseaseControl>;
export type EntityArrayResponseType = HttpResponse<IDiseaseControl[]>;

@Injectable({ providedIn: 'root' })
export class DiseaseControlService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/disease-controls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(diseaseControl: IDiseaseControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diseaseControl);
    return this.http
      .post<IDiseaseControl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(diseaseControl: IDiseaseControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diseaseControl);
    return this.http
      .put<IDiseaseControl>(`${this.resourceUrl}/${getDiseaseControlIdentifier(diseaseControl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(diseaseControl: IDiseaseControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diseaseControl);
    return this.http
      .patch<IDiseaseControl>(`${this.resourceUrl}/${getDiseaseControlIdentifier(diseaseControl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDiseaseControl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDiseaseControl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDiseaseControlToCollectionIfMissing(
    diseaseControlCollection: IDiseaseControl[],
    ...diseaseControlsToCheck: (IDiseaseControl | null | undefined)[]
  ): IDiseaseControl[] {
    const diseaseControls: IDiseaseControl[] = diseaseControlsToCheck.filter(isPresent);
    if (diseaseControls.length > 0) {
      const diseaseControlCollectionIdentifiers = diseaseControlCollection.map(
        diseaseControlItem => getDiseaseControlIdentifier(diseaseControlItem)!
      );
      const diseaseControlsToAdd = diseaseControls.filter(diseaseControlItem => {
        const diseaseControlIdentifier = getDiseaseControlIdentifier(diseaseControlItem);
        if (diseaseControlIdentifier == null || diseaseControlCollectionIdentifiers.includes(diseaseControlIdentifier)) {
          return false;
        }
        diseaseControlCollectionIdentifiers.push(diseaseControlIdentifier);
        return true;
      });
      return [...diseaseControlsToAdd, ...diseaseControlCollection];
    }
    return diseaseControlCollection;
  }

  protected convertDateFromClient(diseaseControl: IDiseaseControl): IDiseaseControl {
    return Object.assign({}, diseaseControl, {
      createdOn: diseaseControl.createdOn?.isValid() ? diseaseControl.createdOn.toJSON() : undefined,
      updatedOn: diseaseControl.updatedOn?.isValid() ? diseaseControl.updatedOn.toJSON() : undefined,
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
      res.body.forEach((diseaseControl: IDiseaseControl) => {
        diseaseControl.createdOn = diseaseControl.createdOn ? dayjs(diseaseControl.createdOn) : undefined;
        diseaseControl.updatedOn = diseaseControl.updatedOn ? dayjs(diseaseControl.updatedOn) : undefined;
      });
    }
    return res;
  }
}
