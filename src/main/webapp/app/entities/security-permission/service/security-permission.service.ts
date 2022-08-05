import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityPermission, getSecurityPermissionIdentifier } from '../security-permission.model';

export type EntityResponseType = HttpResponse<ISecurityPermission>;
export type EntityArrayResponseType = HttpResponse<ISecurityPermission[]>;

@Injectable({ providedIn: 'root' })
export class SecurityPermissionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-permissions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityPermission);
    return this.http
      .post<ISecurityPermission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityPermission);
    return this.http
      .put<ISecurityPermission>(`${this.resourceUrl}/${getSecurityPermissionIdentifier(securityPermission) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityPermission);
    return this.http
      .patch<ISecurityPermission>(`${this.resourceUrl}/${getSecurityPermissionIdentifier(securityPermission) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityPermission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityPermissionToCollectionIfMissing(
    securityPermissionCollection: ISecurityPermission[],
    ...securityPermissionsToCheck: (ISecurityPermission | null | undefined)[]
  ): ISecurityPermission[] {
    const securityPermissions: ISecurityPermission[] = securityPermissionsToCheck.filter(isPresent);
    if (securityPermissions.length > 0) {
      const securityPermissionCollectionIdentifiers = securityPermissionCollection.map(
        securityPermissionItem => getSecurityPermissionIdentifier(securityPermissionItem)!
      );
      const securityPermissionsToAdd = securityPermissions.filter(securityPermissionItem => {
        const securityPermissionIdentifier = getSecurityPermissionIdentifier(securityPermissionItem);
        if (securityPermissionIdentifier == null || securityPermissionCollectionIdentifiers.includes(securityPermissionIdentifier)) {
          return false;
        }
        securityPermissionCollectionIdentifiers.push(securityPermissionIdentifier);
        return true;
      });
      return [...securityPermissionsToAdd, ...securityPermissionCollection];
    }
    return securityPermissionCollection;
  }

  protected convertDateFromClient(securityPermission: ISecurityPermission): ISecurityPermission {
    return Object.assign({}, securityPermission, {
      createdOn: securityPermission.createdOn?.isValid() ? securityPermission.createdOn.toJSON() : undefined,
      updatedOn: securityPermission.updatedOn?.isValid() ? securityPermission.updatedOn.toJSON() : undefined,
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
      res.body.forEach((securityPermission: ISecurityPermission) => {
        securityPermission.createdOn = securityPermission.createdOn ? dayjs(securityPermission.createdOn) : undefined;
        securityPermission.updatedOn = securityPermission.updatedOn ? dayjs(securityPermission.updatedOn) : undefined;
      });
    }
    return res;
  }
}
