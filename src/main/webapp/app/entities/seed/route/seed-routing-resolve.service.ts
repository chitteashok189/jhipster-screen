import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISeed, Seed } from '../seed.model';
import { SeedService } from '../service/seed.service';

@Injectable({ providedIn: 'root' })
export class SeedRoutingResolveService implements Resolve<ISeed> {
  constructor(protected service: SeedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((seed: HttpResponse<Seed>) => {
          if (seed.body) {
            return of(seed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Seed());
  }
}
