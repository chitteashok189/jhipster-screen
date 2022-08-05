import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrowBed, GrowBed } from '../grow-bed.model';
import { GrowBedService } from '../service/grow-bed.service';

@Injectable({ providedIn: 'root' })
export class GrowBedRoutingResolveService implements Resolve<IGrowBed> {
  constructor(protected service: GrowBedService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrowBed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((growBed: HttpResponse<GrowBed>) => {
          if (growBed.body) {
            return of(growBed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GrowBed());
  }
}
