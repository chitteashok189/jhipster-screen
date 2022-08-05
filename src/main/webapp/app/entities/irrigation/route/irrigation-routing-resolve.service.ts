import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIrrigation, Irrigation } from '../irrigation.model';
import { IrrigationService } from '../service/irrigation.service';

@Injectable({ providedIn: 'root' })
export class IrrigationRoutingResolveService implements Resolve<IIrrigation> {
  constructor(protected service: IrrigationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIrrigation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((irrigation: HttpResponse<Irrigation>) => {
          if (irrigation.body) {
            return of(irrigation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Irrigation());
  }
}
