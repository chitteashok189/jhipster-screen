import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIrrigation, getIrrigationIdentifier } from '../irrigation.model';

export type EntityResponseType = HttpResponse<IIrrigation>;
export type EntityArrayResponseType = HttpResponse<IIrrigation[]>;

@Injectable({ providedIn: 'root' })
export class IrrigationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/irrigations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(irrigation: IIrrigation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(irrigation);
    return this.http
      .post<IIrrigation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(irrigation: IIrrigation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(irrigation);
    return this.http
      .put<IIrrigation>(`${this.resourceUrl}/${getIrrigationIdentifier(irrigation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(irrigation: IIrrigation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(irrigation);
    return this.http
      .patch<IIrrigation>(`${this.resourceUrl}/${getIrrigationIdentifier(irrigation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIrrigation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIrrigation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIrrigationToCollectionIfMissing(
    irrigationCollection: IIrrigation[],
    ...irrigationsToCheck: (IIrrigation | null | undefined)[]
  ): IIrrigation[] {
    const irrigations: IIrrigation[] = irrigationsToCheck.filter(isPresent);
    if (irrigations.length > 0) {
      const irrigationCollectionIdentifiers = irrigationCollection.map(irrigationItem => getIrrigationIdentifier(irrigationItem)!);
      const irrigationsToAdd = irrigations.filter(irrigationItem => {
        const irrigationIdentifier = getIrrigationIdentifier(irrigationItem);
        if (irrigationIdentifier == null || irrigationCollectionIdentifiers.includes(irrigationIdentifier)) {
          return false;
        }
        irrigationCollectionIdentifiers.push(irrigationIdentifier);
        return true;
      });
      return [...irrigationsToAdd, ...irrigationCollection];
    }
    return irrigationCollection;
  }

  protected convertDateFromClient(irrigation: IIrrigation): IIrrigation {
    return Object.assign({}, irrigation, {
      createdOn: irrigation.createdOn?.isValid() ? irrigation.createdOn.toJSON() : undefined,
      updatedOn: irrigation.updatedOn?.isValid() ? irrigation.updatedOn.toJSON() : undefined,
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
      res.body.forEach((irrigation: IIrrigation) => {
        irrigation.createdOn = irrigation.createdOn ? dayjs(irrigation.createdOn) : undefined;
        irrigation.updatedOn = irrigation.updatedOn ? dayjs(irrigation.updatedOn) : undefined;
      });
    }
    return res;
  }
}
