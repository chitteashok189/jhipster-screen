import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INutrient, Nutrient } from '../nutrient.model';
import { NutrientService } from '../service/nutrient.service';

@Injectable({ providedIn: 'root' })
export class NutrientRoutingResolveService implements Resolve<INutrient> {
  constructor(protected service: NutrientService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INutrient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nutrient: HttpResponse<Nutrient>) => {
          if (nutrient.body) {
            return of(nutrient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nutrient());
  }
}
