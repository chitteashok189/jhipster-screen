import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILight, Light } from '../light.model';
import { LightService } from '../service/light.service';

@Injectable({ providedIn: 'root' })
export class LightRoutingResolveService implements Resolve<ILight> {
  constructor(protected service: LightService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILight> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((light: HttpResponse<Light>) => {
          if (light.body) {
            return of(light.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Light());
  }
}
