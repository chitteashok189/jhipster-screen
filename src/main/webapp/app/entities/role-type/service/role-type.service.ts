import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoleType, getRoleTypeIdentifier } from '../role-type.model';

export type EntityResponseType = HttpResponse<IRoleType>;
export type EntityArrayResponseType = HttpResponse<IRoleType[]>;

@Injectable({ providedIn: 'root' })
export class RoleTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(roleType: IRoleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleType);
    return this.http
      .post<IRoleType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(roleType: IRoleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleType);
    return this.http
      .put<IRoleType>(`${this.resourceUrl}/${getRoleTypeIdentifier(roleType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(roleType: IRoleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleType);
    return this.http
      .patch<IRoleType>(`${this.resourceUrl}/${getRoleTypeIdentifier(roleType) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRoleType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRoleType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRoleTypeToCollectionIfMissing(roleTypeCollection: IRoleType[], ...roleTypesToCheck: (IRoleType | null | undefined)[]): IRoleType[] {
    const roleTypes: IRoleType[] = roleTypesToCheck.filter(isPresent);
    if (roleTypes.length > 0) {
      const roleTypeCollectionIdentifiers = roleTypeCollection.map(roleTypeItem => getRoleTypeIdentifier(roleTypeItem)!);
      const roleTypesToAdd = roleTypes.filter(roleTypeItem => {
        const roleTypeIdentifier = getRoleTypeIdentifier(roleTypeItem);
        if (roleTypeIdentifier == null || roleTypeCollectionIdentifiers.includes(roleTypeIdentifier)) {
          return false;
        }
        roleTypeCollectionIdentifiers.push(roleTypeIdentifier);
        return true;
      });
      return [...roleTypesToAdd, ...roleTypeCollection];
    }
    return roleTypeCollection;
  }

  protected convertDateFromClient(roleType: IRoleType): IRoleType {
    return Object.assign({}, roleType, {
      createdOn: roleType.createdOn?.isValid() ? roleType.createdOn.toJSON() : undefined,
      updatedOn: roleType.updatedOn?.isValid() ? roleType.updatedOn.toJSON() : undefined,
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
      res.body.forEach((roleType: IRoleType) => {
        roleType.createdOn = roleType.createdOn ? dayjs(roleType.createdOn) : undefined;
        roleType.updatedOn = roleType.updatedOn ? dayjs(roleType.updatedOn) : undefined;
      });
    }
    return res;
  }
}
