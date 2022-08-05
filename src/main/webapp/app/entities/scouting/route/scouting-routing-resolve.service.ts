import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IScouting, Scouting } from '../scouting.model';
import { ScoutingService } from '../service/scouting.service';

@Injectable({ providedIn: 'root' })
export class ScoutingRoutingResolveService implements Resolve<IScouting> {
  constructor(protected service: ScoutingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScouting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((scouting: HttpResponse<Scouting>) => {
          if (scouting.body) {
            return of(scouting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Scouting());
  }
}
