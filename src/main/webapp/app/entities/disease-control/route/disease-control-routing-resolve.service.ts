import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDiseaseControl, DiseaseControl } from '../disease-control.model';
import { DiseaseControlService } from '../service/disease-control.service';

@Injectable({ providedIn: 'root' })
export class DiseaseControlRoutingResolveService implements Resolve<IDiseaseControl> {
  constructor(protected service: DiseaseControlService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiseaseControl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((diseaseControl: HttpResponse<DiseaseControl>) => {
          if (diseaseControl.body) {
            return of(diseaseControl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DiseaseControl());
  }
}
