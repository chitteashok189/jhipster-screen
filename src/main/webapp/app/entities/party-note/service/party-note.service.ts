import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartyNote, getPartyNoteIdentifier } from '../party-note.model';

export type EntityResponseType = HttpResponse<IPartyNote>;
export type EntityArrayResponseType = HttpResponse<IPartyNote[]>;

@Injectable({ providedIn: 'root' })
export class PartyNoteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/party-notes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partyNote: IPartyNote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyNote);
    return this.http
      .post<IPartyNote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyNote: IPartyNote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyNote);
    return this.http
      .put<IPartyNote>(`${this.resourceUrl}/${getPartyNoteIdentifier(partyNote) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(partyNote: IPartyNote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyNote);
    return this.http
      .patch<IPartyNote>(`${this.resourceUrl}/${getPartyNoteIdentifier(partyNote) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyNote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyNote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPartyNoteToCollectionIfMissing(
    partyNoteCollection: IPartyNote[],
    ...partyNotesToCheck: (IPartyNote | null | undefined)[]
  ): IPartyNote[] {
    const partyNotes: IPartyNote[] = partyNotesToCheck.filter(isPresent);
    if (partyNotes.length > 0) {
      const partyNoteCollectionIdentifiers = partyNoteCollection.map(partyNoteItem => getPartyNoteIdentifier(partyNoteItem)!);
      const partyNotesToAdd = partyNotes.filter(partyNoteItem => {
        const partyNoteIdentifier = getPartyNoteIdentifier(partyNoteItem);
        if (partyNoteIdentifier == null || partyNoteCollectionIdentifiers.includes(partyNoteIdentifier)) {
          return false;
        }
        partyNoteCollectionIdentifiers.push(partyNoteIdentifier);
        return true;
      });
      return [...partyNotesToAdd, ...partyNoteCollection];
    }
    return partyNoteCollection;
  }

  protected convertDateFromClient(partyNote: IPartyNote): IPartyNote {
    return Object.assign({}, partyNote, {
      createdOn: partyNote.createdOn?.isValid() ? partyNote.createdOn.toJSON() : undefined,
      updatedOn: partyNote.updatedOn?.isValid() ? partyNote.updatedOn.toJSON() : undefined,
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
      res.body.forEach((partyNote: IPartyNote) => {
        partyNote.createdOn = partyNote.createdOn ? dayjs(partyNote.createdOn) : undefined;
        partyNote.updatedOn = partyNote.updatedOn ? dayjs(partyNote.updatedOn) : undefined;
      });
    }
    return res;
  }
}
