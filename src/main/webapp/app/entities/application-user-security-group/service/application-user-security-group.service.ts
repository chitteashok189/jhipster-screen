import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApplicationUserSecurityGroup, getApplicationUserSecurityGroupIdentifier } from '../application-user-security-group.model';

export type EntityResponseType = HttpResponse<IApplicationUserSecurityGroup>;
export type EntityArrayResponseType = HttpResponse<IApplicationUserSecurityGroup[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationUserSecurityGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/application-user-security-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(applicationUserSecurityGroup: IApplicationUserSecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationUserSecurityGroup);
    return this.http
      .post<IApplicationUserSecurityGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationUserSecurityGroup: IApplicationUserSecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationUserSecurityGroup);
    return this.http
      .put<IApplicationUserSecurityGroup>(
        `${this.resourceUrl}/${getApplicationUserSecurityGroupIdentifier(applicationUserSecurityGroup) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(applicationUserSecurityGroup: IApplicationUserSecurityGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationUserSecurityGroup);
    return this.http
      .patch<IApplicationUserSecurityGroup>(
        `${this.resourceUrl}/${getApplicationUserSecurityGroupIdentifier(applicationUserSecurityGroup) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationUserSecurityGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationUserSecurityGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addApplicationUserSecurityGroupToCollectionIfMissing(
    applicationUserSecurityGroupCollection: IApplicationUserSecurityGroup[],
    ...applicationUserSecurityGroupsToCheck: (IApplicationUserSecurityGroup | null | undefined)[]
  ): IApplicationUserSecurityGroup[] {
    const applicationUserSecurityGroups: IApplicationUserSecurityGroup[] = applicationUserSecurityGroupsToCheck.filter(isPresent);
    if (applicationUserSecurityGroups.length > 0) {
      const applicationUserSecurityGroupCollectionIdentifiers = applicationUserSecurityGroupCollection.map(
        applicationUserSecurityGroupItem => getApplicationUserSecurityGroupIdentifier(applicationUserSecurityGroupItem)!
      );
      const applicationUserSecurityGroupsToAdd = applicationUserSecurityGroups.filter(applicationUserSecurityGroupItem => {
        const applicationUserSecurityGroupIdentifier = getApplicationUserSecurityGroupIdentifier(applicationUserSecurityGroupItem);
        if (
          applicationUserSecurityGroupIdentifier == null ||
          applicationUserSecurityGroupCollectionIdentifiers.includes(applicationUserSecurityGroupIdentifier)
        ) {
          return false;
        }
        applicationUserSecurityGroupCollectionIdentifiers.push(applicationUserSecurityGroupIdentifier);
        return true;
      });
      return [...applicationUserSecurityGroupsToAdd, ...applicationUserSecurityGroupCollection];
    }
    return applicationUserSecurityGroupCollection;
  }

  protected convertDateFromClient(applicationUserSecurityGroup: IApplicationUserSecurityGroup): IApplicationUserSecurityGroup {
    return Object.assign({}, applicationUserSecurityGroup, {
      fromDate: applicationUserSecurityGroup.fromDate?.isValid() ? applicationUserSecurityGroup.fromDate.toJSON() : undefined,
      thruDate: applicationUserSecurityGroup.thruDate?.isValid() ? applicationUserSecurityGroup.thruDate.toJSON() : undefined,
      createdOn: applicationUserSecurityGroup.createdOn?.isValid() ? applicationUserSecurityGroup.createdOn.toJSON() : undefined,
      updatedOn: applicationUserSecurityGroup.updatedOn?.isValid() ? applicationUserSecurityGroup.updatedOn.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? dayjs(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? dayjs(res.body.thruDate) : undefined;
      res.body.createdOn = res.body.createdOn ? dayjs(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? dayjs(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applicationUserSecurityGroup: IApplicationUserSecurityGroup) => {
        applicationUserSecurityGroup.fromDate = applicationUserSecurityGroup.fromDate
          ? dayjs(applicationUserSecurityGroup.fromDate)
          : undefined;
        applicationUserSecurityGroup.thruDate = applicationUserSecurityGroup.thruDate
          ? dayjs(applicationUserSecurityGroup.thruDate)
          : undefined;
        applicationUserSecurityGroup.createdOn = applicationUserSecurityGroup.createdOn
          ? dayjs(applicationUserSecurityGroup.createdOn)
          : undefined;
        applicationUserSecurityGroup.updatedOn = applicationUserSecurityGroup.updatedOn
          ? dayjs(applicationUserSecurityGroup.updatedOn)
          : undefined;
      });
    }
    return res;
  }
}
