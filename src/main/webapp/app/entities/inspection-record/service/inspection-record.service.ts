import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInspectionRecord, getInspectionRecordIdentifier } from '../inspection-record.model';

export type EntityResponseType = HttpResponse<IInspectionRecord>;
export type EntityArrayResponseType = HttpResponse<IInspectionRecord[]>;

@Injectable({ providedIn: 'root' })
export class InspectionRecordService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inspection-records');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inspectionRecord: IInspectionRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionRecord);
    return this.http
      .post<IInspectionRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inspectionRecord: IInspectionRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionRecord);
    return this.http
      .put<IInspectionRecord>(`${this.resourceUrl}/${getInspectionRecordIdentifier(inspectionRecord) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(inspectionRecord: IInspectionRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inspectionRecord);
    return this.http
      .patch<IInspectionRecord>(`${this.resourceUrl}/${getInspectionRecordIdentifier(inspectionRecord) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInspectionRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInspectionRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInspectionRecordToCollectionIfMissing(
    inspectionRecordCollection: IInspectionRecord[],
    ...inspectionRecordsToCheck: (IInspectionRecord | null | undefined)[]
  ): IInspectionRecord[] {
    const inspectionRecords: IInspectionRecord[] = inspectionRecordsToCheck.filter(isPresent);
    if (inspectionRecords.length > 0) {
      const inspectionRecordCollectionIdentifiers = inspectionRecordCollection.map(
        inspectionRecordItem => getInspectionRecordIdentifier(inspectionRecordItem)!
      );
      const inspectionRecordsToAdd = inspectionRecords.filter(inspectionRecordItem => {
        const inspectionRecordIdentifier = getInspectionRecordIdentifier(inspectionRecordItem);
        if (inspectionRecordIdentifier == null || inspectionRecordCollectionIdentifiers.includes(inspectionRecordIdentifier)) {
          return false;
        }
        inspectionRecordCollectionIdentifiers.push(inspectionRecordIdentifier);
        return true;
      });
      return [...inspectionRecordsToAdd, ...inspectionRecordCollection];
    }
    return inspectionRecordCollection;
  }

  protected convertDateFromClient(inspectionRecord: IInspectionRecord): IInspectionRecord {
    return Object.assign({}, inspectionRecord, {
      createdOn: inspectionRecord.createdOn?.isValid() ? inspectionRecord.createdOn.toJSON() : undefined,
      updatedOn: inspectionRecord.updatedOn?.isValid() ? inspectionRecord.updatedOn.toJSON() : undefined,
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
      res.body.forEach((inspectionRecord: IInspectionRecord) => {
        inspectionRecord.createdOn = inspectionRecord.createdOn ? dayjs(inspectionRecord.createdOn) : undefined;
        inspectionRecord.updatedOn = inspectionRecord.updatedOn ? dayjs(inspectionRecord.updatedOn) : undefined;
      });
    }
    return res;
  }
}
