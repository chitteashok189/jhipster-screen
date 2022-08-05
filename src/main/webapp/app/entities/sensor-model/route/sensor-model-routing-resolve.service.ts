import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISensorModel, SensorModel } from '../sensor-model.model';
import { SensorModelService } from '../service/sensor-model.service';

@Injectable({ providedIn: 'root' })
export class SensorModelRoutingResolveService implements Resolve<ISensorModel> {
  constructor(protected service: SensorModelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISensorModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sensorModel: HttpResponse<SensorModel>) => {
          if (sensorModel.body) {
            return of(sensorModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SensorModel());
  }
}
