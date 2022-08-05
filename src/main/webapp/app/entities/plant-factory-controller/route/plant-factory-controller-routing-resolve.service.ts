import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlantFactoryController, PlantFactoryController } from '../plant-factory-controller.model';
import { PlantFactoryControllerService } from '../service/plant-factory-controller.service';

@Injectable({ providedIn: 'root' })
export class PlantFactoryControllerRoutingResolveService implements Resolve<IPlantFactoryController> {
  constructor(protected service: PlantFactoryControllerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlantFactoryController> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((plantFactoryController: HttpResponse<PlantFactoryController>) => {
          if (plantFactoryController.body) {
            return of(plantFactoryController.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlantFactoryController());
  }
}
