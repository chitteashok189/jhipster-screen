import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInspection, getInspectionIdentifier } from '../inspection.model';

export type EntityResponseType = HttpResponse<IInspection>;
export type EntityArrayResponseType = HttpResponse<IInspection[]>;

@Injectable({ providedIn: 'root' })
export class InspectionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inspections');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inspection: IInspection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspection);
    return this.http
      .post<IInspection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inspection: IInspection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspection);
    return this.http
      .put<IInspection>(`${this.resourceUrl}/${getInspectionIdentifier(inspection) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inspection: IInspection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspection);
    return this.http
      .patch<IInspection>(`${this.resourceUrl}/${getInspectionIdentifier(inspection) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInspection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInspection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInspectionToCollectionIfMissing(
    inspectionCollection: IInspection[],
    ...inspectionsToCheck: (IInspection | null | undefined)[]
  ): IInspection[] {
    const inspections: IInspection[] = inspectionsToCheck.filter(isPresent);
    if (inspections.length > 0) {
      const inspectionCollectionIdentifiers = inspectionCollection.map(inspectionItem => getInspectionIdentifier(inspectionItem)!);
      const inspectionsToAdd = inspections.filter(inspectionItem => {
        const inspectionIdentifier = getInspectionIdentifier(inspectionItem);
        if (inspectionIdentifier == null || inspectionCollectionIdentifiers.includes(inspectionIdentifier)) {
          return false;
        }
        inspectionCollectionIdentifiers.push(inspectionIdentifier);
        return true;
      });
      return [...inspectionsToAdd, ...inspectionCollection];
    }
    return inspectionCollection;
  }

  protected convertDateFromClient(inspection: IInspection): IInspection {
    return Object.assign({}, inspection, {
      createdOn: inspection.createdOn?.isValid() ? inspection.createdOn.toJSON() : undefined,
      updatedOn: inspection.updatedOn?.isValid() ? inspection.updatedOn.toJSON() : undefined,
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
      res.body.forEach((inspection: IInspection) => {
        inspection.createdOn = inspection.createdOn ? dayjs(inspection.createdOn) : undefined;
        inspection.updatedOn = inspection.updatedOn ? dayjs(inspection.updatedOn) : undefined;
      });
    }
    return res;
  }
}
