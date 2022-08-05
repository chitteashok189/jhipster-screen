import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISecurityGroup, SecurityGroup } from '../security-group.model';
import { SecurityGroupService } from '../service/security-group.service';

@Injectable({ providedIn: 'root' })
export class SecurityGroupRoutingResolveService implements Resolve<ISecurityGroup> {
  constructor(protected service: SecurityGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecurityGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((securityGroup: HttpResponse<SecurityGroup>) => {
          if (securityGroup.body) {
            return of(securityGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecurityGroup());
  }
}
