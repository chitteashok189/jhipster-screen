import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityGroupPermission, getSecurityGroupPermissionIdentifier } from '../security-group-permission.model';

export type EntityResponseType = HttpResponse<ISecurityGroupPermission>;
export type EntityArrayResponseType = HttpResponse<ISecurityGroupPermission[]>;

@Injectable({ providedIn: 'root' })
export class SecurityGroupPermissionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-group-permissions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityGroupPermission: ISecurityGroupPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroupPermission);
    return this.http
      .post<ISecurityGroupPermission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityGroupPermission: ISecurityGroupPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroupPermission);
    return this.http
      .put<ISecurityGroupPermission>(
        `${this.resourceUrl}/${getSecurityGroupPermissionIdentifier(securityGroupPermission) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(securityGroupPermission: ISecurityGroupPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroupPermission);
    return this.http
      .patch<ISecurityGroupPermission>(
        `${this.resourceUrl}/${getSecurityGroupPermissionIdentifier(securityGroupPermission) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityGroupPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityGroupPermission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityGroupPermissionToCollectionIfMissing(
    securityGroupPermissionCollection: ISecurityGroupPermission[],
    ...securityGroupPermissionsToCheck: (ISecurityGroupPermission | null | undefined)[]
  ): ISecurityGroupPermission[] {
    const securityGroupPermissions: ISecurityGroupPermission[] = securityGroupPermissionsToCheck.filter(isPresent);
    if (securityGroupPermissions.length > 0) {
      const securityGroupPermissionCollectionIdentifiers = securityGroupPermissionCollection.map(
        securityGroupPermissionItem => getSecurityGroupPermissionIdentifier(securityGroupPermissionItem)!
      );
      const securityGroupPermissionsToAdd = securityGroupPermissions.filter(securityGroupPermissionItem => {
        const securityGroupPermissionIdentifier = getSecurityGroupPermissionIdentifier(securityGroupPermissionItem);
        if (
          securityGroupPermissionIdentifier == null ||
          securityGroupPermissionCollectionIdentifiers.includes(securityGroupPermissionIdentifier)
        ) {
          return false;
        }
        securityGroupPermissionCollectionIdentifiers.push(securityGroupPermissionIdentifier);
        return true;
      });
      return [...securityGroupPermissionsToAdd, ...securityGroupPermissionCollection];
    }
    return securityGroupPermissionCollection;
  }

  protected convertDateFromClient(securityGroupPermission: ISecurityGroupPermission): ISecurityGroupPermission {
    return Object.assign({}, securityGroupPermission, {
      createdOn: securityGroupPermission.createdOn?.isValid() ? securityGroupPermission.createdOn.toJSON() : undefined,
      updatedOn: securityGroupPermission.updatedOn?.isValid() ? securityGroupPermission.updatedOn.toJSON() : undefined,
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
      res.body.forEach((securityGroupPermission: ISecurityGroupPermission) => {
        securityGroupPermission.createdOn = securityGroupPermission.createdOn ? dayjs(securityGroupPermission.createdOn) : undefined;
        securityGroupPermission.updatedOn = securityGroupPermission.updatedOn ? dayjs(securityGroupPermission.updatedOn) : undefined;
      });
    }
    return res;
  }
}
