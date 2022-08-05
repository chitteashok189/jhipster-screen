import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILot, getLotIdentifier } from '../lot.model';

export type EntityResponseType = HttpResponse<ILot>;
export type EntityArrayResponseType = HttpResponse<ILot[]>;

@Injectable({ providedIn: 'root' })
export class LotService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lots');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lot: ILot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lot);
    return this.http
      .post<ILot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lot: ILot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lot);
    return this.http
      .put<ILot>(`${this.resourceUrl}/${getLotIdentifier(lot) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(lot: ILot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lot);
    return this.http
      .patch<ILot>(`${this.resourceUrl}/${getLotIdentifier(lot) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLotToCollectionIfMissing(lotCollection: ILot[], ...lotsToCheck: (ILot | null | undefined)[]): ILot[] {
    const lots: ILot[] = lotsToCheck.filter(isPresent);
    if (lots.length > 0) {
      const lotCollectionIdentifiers = lotCollection.map(lotItem => getLotIdentifier(lotItem)!);
      const lotsToAdd = lots.filter(lotItem => {
        const lotIdentifier = getLotIdentifier(lotItem);
        if (lotIdentifier == null || lotCollectionIdentifiers.includes(lotIdentifier)) {
          return false;
        }
        lotCollectionIdentifiers.push(lotIdentifier);
        return true;
      });
      return [...lotsToAdd, ...lotCollection];
    }
    return lotCollection;
  }

  protected convertDateFromClient(lot: ILot): ILot {
    return Object.assign({}, lot, {
      packingDate: lot.packingDate?.isValid() ? lot.packingDate.toJSON() : undefined,
      tranportationDate: lot.tranportationDate?.isValid() ? lot.tranportationDate.toJSON() : undefined,
      createdOn: lot.createdOn?.isValid() ? lot.createdOn.toJSON() : undefined,
      updatedOn: lot.updatedOn?.isValid() ? lot.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.packingDate = res.body.packingDate ? dayjs(res.body.packingDate) : undefined;
      res.body.tranportationDate = res.body.tranportationDate ? dayjs(res.body.tranportationDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lot: ILot) => {
        lot.packingDate = lot.packingDate ? dayjs(lot.packingDate) : undefined;
        lot.tranportationDate = lot.tranportationDate ? dayjs(lot.tranportationDate) : undefined;
        lot.createdOn = lot.createdOn ? dayjs(lot.createdOn) : undefined;
        lot.updatedOn = lot.updatedOn ? dayjs(lot.updatedOn) : undefined;
      });
    }
    return res;
  }
}
