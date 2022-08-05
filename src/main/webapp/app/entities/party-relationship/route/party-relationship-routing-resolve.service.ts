import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyRelationship, PartyRelationship } from '../party-relationship.model';
import { PartyRelationshipService } from '../service/party-relationship.service';

@Injectable({ providedIn: 'root' })
export class PartyRelationshipRoutingResolveService implements Resolve<IPartyRelationship> {
  constructor(protected service: PartyRelationshipService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyRelationship> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyRelationship: HttpResponse<PartyRelationship>) => {
          if (partyRelationship.body) {
            return of(partyRelationship.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyRelationship());
  }
}
