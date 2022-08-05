import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyStatusType, PartyStatusType } from '../party-status-type.model';
import { PartyStatusTypeService } from '../service/party-status-type.service';

@Injectable({ providedIn: 'root' })
export class PartyStatusTypeRoutingResolveService implements Resolve<IPartyStatusType> {
  constructor(protected service: PartyStatusTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyStatusType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyStatusType: HttpResponse<PartyStatusType>) => {
          if (partyStatusType.body) {
            return of(partyStatusType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyStatusType());
  }
}
