import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRoleTypeAttribute, RoleTypeAttribute } from '../role-type-attribute.model';
import { RoleTypeAttributeService } from '../service/role-type-attribute.service';

@Injectable({ providedIn: 'root' })
export class RoleTypeAttributeRoutingResolveService implements Resolve<IRoleTypeAttribute> {
  constructor(protected service: RoleTypeAttributeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRoleTypeAttribute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((roleTypeAttribute: HttpResponse<RoleTypeAttribute>) => {
          if (roleTypeAttribute.body) {
            return of(roleTypeAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RoleTypeAttribute());
  }
}
