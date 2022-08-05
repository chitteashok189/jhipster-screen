import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDosing, Dosing } from '../dosing.model';
import { DosingService } from '../service/dosing.service';

@Injectable({ providedIn: 'root' })
export class DosingRoutingResolveService implements Resolve<IDosing> {
  constructor(protected service: DosingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDosing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dosing: HttpResponse<Dosing>) => {
          if (dosing.body) {
            return of(dosing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dosing());
  }
}
