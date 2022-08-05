import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyRole, PartyRole } from '../party-role.model';
import { PartyRoleService } from '../service/party-role.service';

@Injectable({ providedIn: 'root' })
export class PartyRoleRoutingResolveService implements Resolve<IPartyRole> {
  constructor(protected service: PartyRoleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyRole: HttpResponse<PartyRole>) => {
          if (partyRole.body) {
            return of(partyRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyRole());
  }
}
