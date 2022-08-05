import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInspectionRecord, InspectionRecord } from '../inspection-record.model';
import { InspectionRecordService } from '../service/inspection-record.service';

@Injectable({ providedIn: 'root' })
export class InspectionRecordRoutingResolveService implements Resolve<IInspectionRecord> {
  constructor(protected service: InspectionRecordService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInspectionRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inspectionRecord: HttpResponse<InspectionRecord>) => {
          if (inspectionRecord.body) {
            return of(inspectionRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InspectionRecord());
  }
}
