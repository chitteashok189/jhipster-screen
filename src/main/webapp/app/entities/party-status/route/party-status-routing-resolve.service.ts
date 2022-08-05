import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyStatus, PartyStatus } from '../party-status.model';
import { PartyStatusService } from '../service/party-status.service';

@Injectable({ providedIn: 'root' })
export class PartyStatusRoutingResolveService implements Resolve<IPartyStatus> {
  constructor(protected service: PartyStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyStatus: HttpResponse<PartyStatus>) => {
          if (partyStatus.body) {
            return of(partyStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyStatus());
  }
}
