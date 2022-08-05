import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnnumerationType, EnnumerationType } from '../ennumeration-type.model';
import { EnnumerationTypeService } from '../service/ennumeration-type.service';

@Injectable({ providedIn: 'root' })
export class EnnumerationTypeRoutingResolveService implements Resolve<IEnnumerationType> {
  constructor(protected service: EnnumerationTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnnumerationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ennumerationType: HttpResponse<EnnumerationType>) => {
          if (ennumerationType.body) {
            return of(ennumerationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnnumerationType());
  }
}
