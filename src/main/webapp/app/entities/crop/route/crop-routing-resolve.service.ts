import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICrop, Crop } from '../crop.model';
import { CropService } from '../service/crop.service';

@Injectable({ providedIn: 'root' })
export class CropRoutingResolveService implements Resolve<ICrop> {
  constructor(protected service: CropService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICrop> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((crop: HttpResponse<Crop>) => {
          if (crop.body) {
            return of(crop.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Crop());
  }
}
