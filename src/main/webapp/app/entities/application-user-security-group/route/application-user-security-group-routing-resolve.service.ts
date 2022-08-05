import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApplicationUserSecurityGroup, ApplicationUserSecurityGroup } from '../application-user-security-group.model';
import { ApplicationUserSecurityGroupService } from '../service/application-user-security-group.service';

@Injectable({ providedIn: 'root' })
export class ApplicationUserSecurityGroupRoutingResolveService implements Resolve<IApplicationUserSecurityGroup> {
  constructor(protected service: ApplicationUserSecurityGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicationUserSecurityGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((applicationUserSecurityGroup: HttpResponse<ApplicationUserSecurityGroup>) => {
          if (applicationUserSecurityGroup.body) {
            return of(applicationUserSecurityGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicationUserSecurityGroup());
  }
}
