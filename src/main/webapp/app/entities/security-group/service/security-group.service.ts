import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityGroup, getSecurityGroupIdentifier } from '../security-group.model';

export type EntityResponseType = HttpResponse<ISecurityGroup>;
export type EntityArrayResponseType = HttpResponse<ISecurityGroup[]>;

@Injectable({ providedIn: 'root' })
export class SecurityGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityGroup: ISecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroup);
    return this.http
      .post<ISecurityGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityGroup: ISecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroup);
    return this.http
      .put<ISecurityGroup>(`${this.resourceUrl}/${getSecurityGroupIdentifier(securityGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(securityGroup: ISecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityGroup);
    return this.http
      .patch<ISecurityGroup>(`${this.resourceUrl}/${getSecurityGroupIdentifier(securityGroup) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityGroupToCollectionIfMissing(
    securityGroupCollection: ISecurityGroup[],
    ...securityGroupsToCheck: (ISecurityGroup | null | undefined)[]
  ): ISecurityGroup[] {
    const securityGroups: ISecurityGroup[] = securityGroupsToCheck.filter(isPresent);
    if (securityGroups.length > 0) {
      const securityGroupCollectionIdentifiers = securityGroupCollection.map(
        securityGroupItem => getSecurityGroupIdentifier(securityGroupItem)!
      );
      const securityGroupsToAdd = securityGroups.filter(securityGroupItem => {
        const securityGroupIdentifier = getSecurityGroupIdentifier(securityGroupItem);
        if (securityGroupIdentifier == null || securityGroupCollectionIdentifiers.includes(securityGroupIdentifier)) {
          return false;
        }
        securityGroupCollectionIdentifiers.push(securityGroupIdentifier);
        return true;
      });
      return [...securityGroupsToAdd, ...securityGroupCollection];
    }
    return securityGroupCollection;
  }

  protected convertDateFromClient(securityGroup: ISecurityGroup): ISecurityGroup {
    return Object.assign({}, securityGroup, {
      createdOn: securityGroup.createdOn?.isValid() ? securityGroup.createdOn.toJSON() : undefined,
      updatedOn: securityGroup.updatedOn?.isValid() ? securityGroup.updatedOn.toJSON() : undefined,
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
      res.body.forEach((securityGroup: ISecurityGroup) => {
        securityGroup.createdOn = securityGroup.createdOn ? dayjs(securityGroup.createdOn) : undefined;
        securityGroup.updatedOn = securityGroup.updatedOn ? dayjs(securityGroup.updatedOn) : undefined;
      });
    }
    return res;
  }
}
