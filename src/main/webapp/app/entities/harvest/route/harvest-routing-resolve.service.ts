import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHarvest, Harvest } from '../harvest.model';
import { HarvestService } from '../service/harvest.service';

@Injectable({ providedIn: 'root' })
export class HarvestRoutingResolveService implements Resolve<IHarvest> {
  constructor(protected service: HarvestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHarvest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((harvest: HttpResponse<Harvest>) => {
          if (harvest.body) {
            return of(harvest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Harvest());
  }
}
