import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICrop, getCropIdentifier } from '../crop.model';

export type EntityResponseType = HttpResponse<ICrop>;
export type EntityArrayResponseType = HttpResponse<ICrop[]>;

@Injectable({ providedIn: 'root' })
export class CropService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(crop: ICrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crop);
    return this.http
      .post<ICrop>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(crop: ICrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crop);
    return this.http
      .put<ICrop>(`${this.resourceUrl}/${getCropIdentifier(crop) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(crop: ICrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crop);
    return this.http
      .patch<ICrop>(`${this.resourceUrl}/${getCropIdentifier(crop) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICrop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICrop[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCropToCollectionIfMissing(cropCollection: ICrop[], ...cropsToCheck: (ICrop | null | undefined)[]): ICrop[] {
    const crops: ICrop[] = cropsToCheck.filter(isPresent);
    if (crops.length > 0) {
      const cropCollectionIdentifiers = cropCollection.map(cropItem => getCropIdentifier(cropItem)!);
      const cropsToAdd = crops.filter(cropItem => {
        const cropIdentifier = getCropIdentifier(cropItem);
        if (cropIdentifier == null || cropCollectionIdentifiers.includes(cropIdentifier)) {
          return false;
        }
        cropCollectionIdentifiers.push(cropIdentifier);
        return true;
      });
      return [...cropsToAdd, ...cropCollection];
    }
    return cropCollection;
  }

  protected convertDateFromClient(crop: ICrop): ICrop {
    return Object.assign({}, crop, {
      sowingDate: crop.sowingDate?.isValid() ? crop.sowingDate.toJSON() : undefined,
      plantingDate: crop.plantingDate?.isValid() ? crop.plantingDate.toJSON() : undefined,
      transplantationDate: crop.transplantationDate?.isValid() ? crop.transplantationDate.toJSON() : undefined,
      createdOn: crop.createdOn?.isValid() ? crop.createdOn.toJSON() : undefined,
      updatedOn: crop.updatedOn?.isValid() ? crop.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sowingDate = res.body.sowingDate ? dayjs(res.body.sowingDate) : undefined;
      res.body.plantingDate = res.body.plantingDate ? dayjs(res.body.plantingDate) : undefined;
      res.body.transplantationDate = res.body.transplantationDate ? dayjs(res.body.transplantationDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((crop: ICrop) => {
        crop.sowingDate = crop.sowingDate ? dayjs(crop.sowingDate) : undefined;
        crop.plantingDate = crop.plantingDate ? dayjs(crop.plantingDate) : undefined;
        crop.transplantationDate = crop.transplantationDate ? dayjs(crop.transplantationDate) : undefined;
        crop.createdOn = crop.createdOn ? dayjs(crop.createdOn) : undefined;
        crop.updatedOn = crop.updatedOn ? dayjs(crop.updatedOn) : undefined;
      });
    }
    return res;
  }
}
