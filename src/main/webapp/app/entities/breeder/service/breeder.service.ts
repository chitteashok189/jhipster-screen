import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBreeder, getBreederIdentifier } from '../breeder.model';

export type EntityResponseType = HttpResponse<IBreeder>;
export type EntityArrayResponseType = HttpResponse<IBreeder[]>;

@Injectable({ providedIn: 'root' })
export class BreederService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/breeders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(breeder: IBreeder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(breeder);
    return this.http
      .post<IBreeder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(breeder: IBreeder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(breeder);
    return this.http
      .put<IBreeder>(`${this.resourceUrl}/${getBreederIdentifier(breeder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(breeder: IBreeder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(breeder);
    return this.http
      .patch<IBreeder>(`${this.resourceUrl}/${getBreederIdentifier(breeder) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBreeder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBreeder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBreederToCollectionIfMissing(breederCollection: IBreeder[], ...breedersToCheck: (IBreeder | null | undefined)[]): IBreeder[] {
    const breeders: IBreeder[] = breedersToCheck.filter(isPresent);
    if (breeders.length > 0) {
      const breederCollectionIdentifiers = breederCollection.map(breederItem => getBreederIdentifier(breederItem)!);
      const breedersToAdd = breeders.filter(breederItem => {
        const breederIdentifier = getBreederIdentifier(breederItem);
        if (breederIdentifier == null || breederCollectionIdentifiers.includes(breederIdentifier)) {
          return false;
        }
        breederCollectionIdentifiers.push(breederIdentifier);
        return true;
      });
      return [...breedersToAdd, ...breederCollection];
    }
    return breederCollection;
  }

  protected convertDateFromClient(breeder: IBreeder): IBreeder {
    return Object.assign({}, breeder, {
      createdOn: breeder.createdOn?.isValid() ? breeder.createdOn.toJSON() : undefined,
      updatedOn: breeder.updatedOn?.isValid() ? breeder.updatedOn.toJSON() : undefined,
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
      res.body.forEach((breeder: IBreeder) => {
        breeder.createdOn = breeder.createdOn ? dayjs(breeder.createdOn) : undefined;
        breeder.updatedOn = breeder.updatedOn ? dayjs(breeder.updatedOn) : undefined;
      });
    }
    return res;
  }
}
