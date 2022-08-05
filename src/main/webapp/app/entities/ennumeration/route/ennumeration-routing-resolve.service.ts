import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnnumeration, Ennumeration } from '../ennumeration.model';
import { EnnumerationService } from '../service/ennumeration.service';

@Injectable({ providedIn: 'root' })
export class EnnumerationRoutingResolveService implements Resolve<IEnnumeration> {
  constructor(protected service: EnnumerationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnnumeration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ennumeration: HttpResponse<Ennumeration>) => {
          if (ennumeration.body) {
            return of(ennumeration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ennumeration());
  }
}
