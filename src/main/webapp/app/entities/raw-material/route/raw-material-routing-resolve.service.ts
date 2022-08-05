import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRawMaterial, RawMaterial } from '../raw-material.model';
import { RawMaterialService } from '../service/raw-material.service';

@Injectable({ providedIn: 'root' })
export class RawMaterialRoutingResolveService implements Resolve<IRawMaterial> {
  constructor(protected service: RawMaterialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRawMaterial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rawMaterial: HttpResponse<RawMaterial>) => {
          if (rawMaterial.body) {
            return of(rawMaterial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RawMaterial());
  }
}
