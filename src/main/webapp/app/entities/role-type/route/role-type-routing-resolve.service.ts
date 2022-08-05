import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoleType, RoleType } from '../role-type.model';
import { RoleTypeService } from '../service/role-type.service';

@Injectable({ providedIn: 'root' })
export class RoleTypeRoutingResolveService implements Resolve<IRoleType> {
  constructor(protected service: RoleTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((roleType: HttpResponse<RoleType>) => {
          if (roleType.body) {
            return of(roleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RoleType());
  }
}
