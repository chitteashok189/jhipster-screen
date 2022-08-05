import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartyClassification, PartyClassification } from '../party-classification.model';
import { PartyClassificationService } from '../service/party-classification.service';

@Injectable({ providedIn: 'root' })
export class PartyClassificationRoutingResolveService implements Resolve<IPartyClassification> {
  constructor(protected service: PartyClassificationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyClassification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partyClassification: HttpResponse<PartyClassification>) => {
          if (partyClassification.body) {
            return of(partyClassification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyClassification());
  }
}
