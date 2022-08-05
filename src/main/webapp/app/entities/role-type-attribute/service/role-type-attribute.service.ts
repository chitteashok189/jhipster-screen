import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRoleTypeAttribute, getRoleTypeAttributeIdentifier } from '../role-type-attribute.model';

export type EntityResponseType = HttpResponse<IRoleTypeAttribute>;
export type EntityArrayResponseType = HttpResponse<IRoleTypeAttribute[]>;

@Injectable({ providedIn: 'root' })
export class RoleTypeAttributeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/role-type-attributes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(roleTypeAttribute: IRoleTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleTypeAttribute);
    return this.http
      .post<IRoleTypeAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(roleTypeAttribute: IRoleTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleTypeAttribute);
    return this.http
      .put<IRoleTypeAttribute>(`${this.resourceUrl}/${getRoleTypeAttributeIdentifier(roleTypeAttribute) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(roleTypeAttribute: IRoleTypeAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(roleTypeAttribute);
    return this.http
      .patch<IRoleTypeAttribute>(`${this.resourceUrl}/${getRoleTypeAttributeIdentifier(roleTypeAttribute) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRoleTypeAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRoleTypeAttribute[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRoleTypeAttributeToCollectionIfMissing(
    roleTypeAttributeCollection: IRoleTypeAttribute[],
    ...roleTypeAttributesToCheck: (IRoleTypeAttribute | null | undefined)[]
  ): IRoleTypeAttribute[] {
    const roleTypeAttributes: IRoleTypeAttribute[] = roleTypeAttributesToCheck.filter(isPresent);
    if (roleTypeAttributes.length > 0) {
      const roleTypeAttributeCollectionIdentifiers = roleTypeAttributeCollection.map(
        roleTypeAttributeItem => getRoleTypeAttributeIdentifier(roleTypeAttributeItem)!
      );
      const roleTypeAttributesToAdd = roleTypeAttributes.filter(roleTypeAttributeItem => {
        const roleTypeAttributeIdentifier = getRoleTypeAttributeIdentifier(roleTypeAttributeItem);
        if (roleTypeAttributeIdentifier == null || roleTypeAttributeCollectionIdentifiers.includes(roleTypeAttributeIdentifier)) {
          return false;
        }
        roleTypeAttributeCollectionIdentifiers.push(roleTypeAttributeIdentifier);
        return true;
      });
      return [...roleTypeAttributesToAdd, ...roleTypeAttributeCollection];
    }
    return roleTypeAttributeCollection;
  }

  protected convertDateFromClient(roleTypeAttribute: IRoleTypeAttribute): IRoleTypeAttribute {
    return Object.assign({}, roleTypeAttribute, {
      createdOn: roleTypeAttribute.createdOn?.isValid() ? roleTypeAttribute.createdOn.toJSON() : undefined,
      updatedOn: roleTypeAttribute.updatedOn?.isValid() ? roleTypeAttribute.updatedOn.toJSON() : undefined,
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
      res.body.forEach((roleTypeAttribute: IRoleTypeAttribute) => {
        roleTypeAttribute.createdOn = roleTypeAttribute.createdOn ? dayjs(roleTypeAttribute.createdOn) : undefined;
        roleTypeAttribute.updatedOn = roleTypeAttribute.updatedOn ? dayjs(roleTypeAttribute.updatedOn) : undefined;
      });
    }
    return res;
  }
}
