import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPestControl, PestControl } from '../pest-control.model';
import { PestControlService } from '../service/pest-control.service';

@Injectable({ providedIn: 'root' })
export class PestControlRoutingResolveService implements Resolve<IPestControl> {
  constructor(protected service: PestControlService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPestControl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pestControl: HttpResponse<PestControl>) => {
          if (pestControl.body) {
            return of(pestControl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PestControl());
  }
}
