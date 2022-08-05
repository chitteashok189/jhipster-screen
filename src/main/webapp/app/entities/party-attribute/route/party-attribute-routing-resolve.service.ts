import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyAttribute, PartyAttribute } from '../party-attribute.model';
import { PartyAttributeService } from '../service/party-attribute.service';

@Injectable({ providedIn: 'root' })
export class PartyAttributeRoutingResolveService implements Resolve<IPartyAttribute> {
  constructor(protected service: PartyAttributeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyAttribute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyAttribute: HttpResponse<PartyAttribute>) => {
          if (partyAttribute.body) {
            return of(partyAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyAttribute());
  }
}
