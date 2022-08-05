import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClimate, Climate } from '../climate.model';
import { ClimateService } from '../service/climate.service';

@Injectable({ providedIn: 'root' })
export class ClimateRoutingResolveService implements Resolve<IClimate> {
  constructor(protected service: ClimateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClimate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((climate: HttpResponse<Climate>) => {
          if (climate.body) {
            return of(climate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Climate());
  }
}
