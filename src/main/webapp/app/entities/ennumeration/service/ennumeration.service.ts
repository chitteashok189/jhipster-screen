import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnnumeration, getEnnumerationIdentifier } from '../ennumeration.model';

export type EntityResponseType = HttpResponse<IEnnumeration>;
export type EntityArrayResponseType = HttpResponse<IEnnumeration[]>;

@Injectable({ providedIn: 'root' })
export class EnnumerationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ennumerations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ennumeration: IEnnumeration): Observable<EntityResponseType> {
    return this.http.post<IEnnumeration>(this.resourceUrl, ennumeration, { observe: 'response' });
  }

  update(ennumeration: IEnnumeration): Observable<EntityResponseType> {
    return this.http.put<IEnnumeration>(`${this.resourceUrl}/${getEnnumerationIdentifier(ennumeration) as number}`, ennumeration, {
      observe: 'response',
    });
  }

  partialUpdate(ennumeration: IEnnumeration): Observable<EntityResponseType> {
    return this.http.patch<IEnnumeration>(`${this.resourceUrl}/${getEnnumerationIdentifier(ennumeration) as number}`, ennumeration, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnnumeration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnnumeration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEnnumerationToCollectionIfMissing(
    ennumerationCollection: IEnnumeration[],
    ...ennumerationsToCheck: (IEnnumeration | null | undefined)[]
  ): IEnnumeration[] {
    const ennumerations: IEnnumeration[] = ennumerationsToCheck.filter(isPresent);
    if (ennumerations.length > 0) {
      const ennumerationCollectionIdentifiers = ennumerationCollection.map(
        ennumerationItem => getEnnumerationIdentifier(ennumerationItem)!
      );
      const ennumerationsToAdd = ennumerations.filter(ennumerationItem => {
        const ennumerationIdentifier = getEnnumerationIdentifier(ennumerationItem);
        if (ennumerationIdentifier == null || ennumerationCollectionIdentifiers.includes(ennumerationIdentifier)) {
          return false;
        }
        ennumerationCollectionIdentifiers.push(ennumerationIdentifier);
        return true;
      });
      return [...ennumerationsToAdd, ...ennumerationCollection];
    }
    return ennumerationCollection;
  }
}
