import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBreeder, Breeder } from '../breeder.model';
import { BreederService } from '../service/breeder.service';

@Injectable({ providedIn: 'root' })
export class BreederRoutingResolveService implements Resolve<IBreeder> {
  constructor(protected service: BreederService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBreeder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((breeder: HttpResponse<Breeder>) => {
          if (breeder.body) {
            return of(breeder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Breeder());
  }
}
