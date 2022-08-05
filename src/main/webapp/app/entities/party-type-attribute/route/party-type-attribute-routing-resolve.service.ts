import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyTypeAttribute, PartyTypeAttribute } from '../party-type-attribute.model';
import { PartyTypeAttributeService } from '../service/party-type-attribute.service';

@Injectable({ providedIn: 'root' })
export class PartyTypeAttributeRoutingResolveService implements Resolve<IPartyTypeAttribute> {
  constructor(protected service: PartyTypeAttributeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyTypeAttribute> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyTypeAttribute: HttpResponse<PartyTypeAttribute>) => {
          if (partyTypeAttribute.body) {
            return of(partyTypeAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyTypeAttribute());
  }
}
