import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyRole, getPartyRoleIdentifier } from '../party-role.model';

export type EntityResponseType = HttpResponse<IPartyRole>;
export type EntityArrayResponseType = HttpResponse<IPartyRole[]>;

@Injectable({ providedIn: 'root' })
export class PartyRoleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-roles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyRole: IPartyRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRole);
    return this.http
      .post<IPartyRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyRole: IPartyRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRole);
    return this.http
      .put<IPartyRole>(`${this.resourceUrl}/${getPartyRoleIdentifier(partyRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyRole: IPartyRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyRole);
    return this.http
      .patch<IPartyRole>(`${this.resourceUrl}/${getPartyRoleIdentifier(partyRole) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyRole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyRole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyRoleToCollectionIfMissing(
    partyRoleCollection: IPartyRole[],
    ...partyRolesToCheck: (IPartyRole | null | undefined)[]
  ): IPartyRole[] {
    const partyRoles: IPartyRole[] = partyRolesToCheck.filter(isPresent);
    if (partyRoles.length > 0) {
      const partyRoleCollectionIdentifiers = partyRoleCollection.map(partyRoleItem => getPartyRoleIdentifier(partyRoleItem)!);
      const partyRolesToAdd = partyRoles.filter(partyRoleItem => {
        const partyRoleIdentifier = getPartyRoleIdentifier(partyRoleItem);
        if (partyRoleIdentifier == null || partyRoleCollectionIdentifiers.includes(partyRoleIdentifier)) {
          return false;
        }
        partyRoleCollectionIdentifiers.push(partyRoleIdentifier);
        return true;
      });
      return [...partyRolesToAdd, ...partyRoleCollection];
    }
    return partyRoleCollection;
  }

  protected convertDateFromClient(partyRole: IPartyRole): IPartyRole {
    return Object.assign({}, partyRole, {
      createdOn: partyRole.createdOn?.isValid() ? partyRole.createdOn.toJSON() : undefined,
      updatedOn: partyRole.updatedOn?.isValid() ? partyRole.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyRole: IPartyRole) => {
        partyRole.createdOn = partyRole.createdOn ? dayjs(partyRole.createdOn) : undefined;
        partyRole.updatedOn = partyRole.updatedOn ? dayjs(partyRole.updatedOn) : undefined;
      });
    }
    return res;
  }
}
