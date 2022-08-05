import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlantFactory, PlantFactory } from '../plant-factory.model';
import { PlantFactoryService } from '../service/plant-factory.service';

@Injectable({ providedIn: 'root' })
export class PlantFactoryRoutingResolveService implements Resolve<IPlantFactory> {
  constructor(protected service: PlantFactoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlantFactory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((plantFactory: HttpResponse<PlantFactory>) => {
          if (plantFactory.body) {
            return of(plantFactory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlantFactory());
  }
}
