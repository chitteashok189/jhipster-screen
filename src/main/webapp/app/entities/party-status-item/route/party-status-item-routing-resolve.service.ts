import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyStatusItem, PartyStatusItem } from '../party-status-item.model';
import { PartyStatusItemService } from '../service/party-status-item.service';

@Injectable({ providedIn: 'root' })
export class PartyStatusItemRoutingResolveService implements Resolve<IPartyStatusItem> {
  constructor(protected service: PartyStatusItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyStatusItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyStatusItem: HttpResponse<PartyStatusItem>) => {
          if (partyStatusItem.body) {
            return of(partyStatusItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyStatusItem());
  }
}
