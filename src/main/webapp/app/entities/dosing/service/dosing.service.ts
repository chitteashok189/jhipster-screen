import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDosing, getDosingIdentifier } from '../dosing.model';

export type EntityResponseType = HttpResponse<IDosing>;
export type EntityArrayResponseType = HttpResponse<IDosing[]>;

@Injectable({ providedIn: 'root' })
export class DosingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dosings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dosing: IDosing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dosing);
    return this.http
      .post<IDosing>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dosing: IDosing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dosing);
    return this.http
      .put<IDosing>(`${this.resourceUrl}/${getDosingIdentifier(dosing) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dosing: IDosing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dosing);
    return this.http
      .patch<IDosing>(`${this.resourceUrl}/${getDosingIdentifier(dosing) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDosing>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDosing[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDosingToCollectionIfMissing(dosingCollection: IDosing[], ...dosingsToCheck: (IDosing | null | undefined)[]): IDosing[] {
    const dosings: IDosing[] = dosingsToCheck.filter(isPresent);
    if (dosings.length > 0) {
      const dosingCollectionIdentifiers = dosingCollection.map(dosingItem => getDosingIdentifier(dosingItem)!);
      const dosingsToAdd = dosings.filter(dosingItem => {
        const dosingIdentifier = getDosingIdentifier(dosingItem);
        if (dosingIdentifier == null || dosingCollectionIdentifiers.includes(dosingIdentifier)) {
          return false;
        }
        dosingCollectionIdentifiers.push(dosingIdentifier);
        return true;
      });
      return [...dosingsToAdd, ...dosingCollection];
    }
    return dosingCollection;
  }

  protected convertDateFromClient(dosing: IDosing): IDosing {
    return Object.assign({}, dosing, {
      createdOn: dosing.createdOn?.isValid() ? dosing.createdOn.toJSON() : undefined,
      updatedOn: dosing.updatedOn?.isValid() ? dosing.updatedOn.toJSON() : undefined,
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
      res.body.forEach((dosing: IDosing) => {
        dosing.createdOn = dosing.createdOn ? dayjs(dosing.createdOn) : undefined;
        dosing.updatedOn = dosing.updatedOn ? dayjs(dosing.updatedOn) : undefined;
      });
    }
    return res;
  }
}
