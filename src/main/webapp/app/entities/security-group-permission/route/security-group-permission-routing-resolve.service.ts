import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISecurityGroupPermission, SecurityGroupPermission } from '../security-group-permission.model';
import { SecurityGroupPermissionService } from '../service/security-group-permission.service';

@Injectable({ providedIn: 'root' })
export class SecurityGroupPermissionRoutingResolveService implements Resolve<ISecurityGroupPermission> {
  constructor(protected service: SecurityGroupPermissionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecurityGroupPermission> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((securityGroupPermission: HttpResponse<SecurityGroupPermission>) => {
          if (securityGroupPermission.body) {
            return of(securityGroupPermission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecurityGroupPermission());
  }
}
