import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyRelationshipType, PartyRelationshipType } from '../party-relationship-type.model';
import { PartyRelationshipTypeService } from '../service/party-relationship-type.service';

@Injectable({ providedIn: 'root' })
export class PartyRelationshipTypeRoutingResolveService implements Resolve<IPartyRelationshipType> {
  constructor(protected service: PartyRelationshipTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyRelationshipType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyRelationshipType: HttpResponse<PartyRelationshipType>) => {
          if (partyRelationshipType.body) {
            return of(partyRelationshipType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyRelationshipType());
  }
}
