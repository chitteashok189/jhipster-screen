import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDisorder, Disorder } from '../disorder.model';
import { DisorderService } from '../service/disorder.service';

@Injectable({ providedIn: 'root' })
export class DisorderRoutingResolveService implements Resolve<IDisorder> {
  constructor(protected service: DisorderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisorder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((disorder: HttpResponse<Disorder>) => {
          if (disorder.body) {
            return of(disorder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Disorder());
  }
}
