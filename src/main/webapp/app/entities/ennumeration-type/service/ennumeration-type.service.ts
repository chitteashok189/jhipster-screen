import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnnumerationType, getEnnumerationTypeIdentifier } from '../ennumeration-type.model';

export type EntityResponseType = HttpResponse<IEnnumerationType>;
export type EntityArrayResponseType = HttpResponse<IEnnumerationType[]>;

@Injectable({ providedIn: 'root' })
export class EnnumerationTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ennumeration-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ennumerationType: IEnnumerationType): Observable<EntityResponseType> {
    return this.http.post<IEnnumerationType>(this.resourceUrl, ennumerationType, { observe: 'response' });
  }

  update(ennumerationType: IEnnumerationType): Observable<EntityResponseType> {
    return this.http.put<IEnnumerationType>(
      `${this.resourceUrl}/${getEnnumerationTypeIdentifier(ennumerationType) as number}`,
      ennumerationType,
      { observe: 'response' }
    );
  }

  partialUpdate(ennumerationType: IEnnumerationType): Observable<EntityResponseType> {
    return this.http.patch<IEnnumerationType>(
      `${this.resourceUrl}/${getEnnumerationTypeIdentifier(ennumerationType) as number}`,
      ennumerationType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnnumerationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnnumerationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEnnumerationTypeToCollectionIfMissing(
    ennumerationTypeCollection: IEnnumerationType[],
    ...ennumerationTypesToCheck: (IEnnumerationType | null | undefined)[]
  ): IEnnumerationType[] {
    const ennumerationTypes: IEnnumerationType[] = ennumerationTypesToCheck.filter(isPresent);
    if (ennumerationTypes.length > 0) {
      const ennumerationTypeCollectionIdentifiers = ennumerationTypeCollection.map(
        ennumerationTypeItem => getEnnumerationTypeIdentifier(ennumerationTypeItem)!
      );
      const ennumerationTypesToAdd = ennumerationTypes.filter(ennumerationTypeItem => {
        const ennumerationTypeIdentifier = getEnnumerationTypeIdentifier(ennumerationTypeItem);
        if (ennumerationTypeIdentifier == null || ennumerationTypeCollectionIdentifiers.includes(ennumerationTypeIdentifier)) {
          return false;
        }
        ennumerationTypeCollectionIdentifiers.push(ennumerationTypeIdentifier);
        return true;
      });
      return [...ennumerationTypesToAdd, ...ennumerationTypeCollection];
    }
    return ennumerationTypeCollection;
  }
}
