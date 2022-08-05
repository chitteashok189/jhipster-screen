import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITool, getToolIdentifier } from '../tool.model';

export type EntityResponseType = HttpResponse<ITool>;
export type EntityArrayResponseType = HttpResponse<ITool[]>;

@Injectable({ providedIn: 'root' })
export class ToolService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tools');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tool: ITool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tool);
    return this.http
      .post<ITool>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tool: ITool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tool);
    return this.http
      .put<ITool>(`${this.resourceUrl}/${getToolIdentifier(tool) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tool: ITool): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tool);
    return this.http
      .patch<ITool>(`${this.resourceUrl}/${getToolIdentifier(tool) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITool>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITool[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addToolToCollectionIfMissing(toolCollection: ITool[], ...toolsToCheck: (ITool | null | undefined)[]): ITool[] {
    const tools: ITool[] = toolsToCheck.filter(isPresent);
    if (tools.length > 0) {
      const toolCollectionIdentifiers = toolCollection.map(toolItem => getToolIdentifier(toolItem)!);
      const toolsToAdd = tools.filter(toolItem => {
        const toolIdentifier = getToolIdentifier(toolItem);
        if (toolIdentifier == null || toolCollectionIdentifiers.includes(toolIdentifier)) {
          return false;
        }
        toolCollectionIdentifiers.push(toolIdentifier);
        return true;
      });
      return [...toolsToAdd, ...toolCollection];
    }
    return toolCollection;
  }

  protected convertDateFromClient(tool: ITool): ITool {
    return Object.assign({}, tool, {
      createdOn: tool.createdOn?.isValid() ? tool.createdOn.toJSON() : undefined,
      updatedOn: tool.updatedOn?.isValid() ? tool.updatedOn.toJSON() : undefined,
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
      res.body.forEach((tool: ITool) => {
        tool.createdOn = tool.createdOn ? dayjs(tool.createdOn) : undefined;
        tool.updatedOn = tool.updatedOn ? dayjs(tool.updatedOn) : undefined;
      });
    }
    return res;
  }
}
